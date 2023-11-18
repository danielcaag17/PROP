package src.domain.classes;
import java.util.*;


class BranchBound implements Strategy {
    private double[] AbsoluteFreqs;
    private double[][] Frequencies;
    private double[][] Distancies;
    private int n_size;

    private double cost(int c1, int p1, int c2, int p2) {
        // c1 i c2 són els IDs de caracter
        // p1 i p2 són les posicions (tecles) dels respectius caràcters
        // Computar cost de l'aresta entre (c1,p1) i (c2,p2)
        double freq_ab = this.Frequencies[c1][c2];
        double freq_ba = this.Frequencies[c2][c1];
        double dist_ab = this.Distancies[p1][p2];
        return ((freq_ab+freq_ba)/2)*dist_ab;
    }
    
    private double[][] matrixC1(ArrayList<Integer> partialSol, ArrayList<Integer> missingChars) {
        // Matriu C1:
        //      Files    [i] -> Caràcters no emplaçats
        //      Columnes [j] -> Tecles (posicions) no emplaçades
        //
        //      Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //                cada posició (i,j) de la matriu conté el valor corresponent al cost
        //                de posar el caràcter [i] a la posició [j] en relació amb tots els
        //                caràcters ja emplaçats en la solució parcial.
        //      
        //      Exemple:
        //          Solució parcial = [c1, c2, c3, ...]
        //          Per a qualsevol (i,j) de C1 suposem = Sol. [c1, c2, c3, ..., c(i), ...]
        //          C1[i][j] = Cost de les arestes entre c[i] en tecla[j] i totes les tecles
        //                     ja emplaçades en la solució parcial.

        // Tots els index des de partialSol.size() fins a n_size-1 són les tecles buides
        int c1_size = missingChars.size();
        double[][] C1 = new double[c1_size][c1_size];

        for (int i = 0; i < c1_size; i++) {
            for (int j = 0; j < c1_size; j++) {
                double value = 0;
                for (int idx = 0; idx < partialSol.size(); idx++) {
                    int id_char = partialSol.get(idx);
                    // i,j són 0...c1_size-1 => Els hem de retornar al seu valor corresponent
                    int t_char = missingChars.get(i);
                    int t_index = j + partialSol.size();
                    value += cost(t_char, t_index, id_char, idx);
                }
                C1[i][j] = value;
            }
        }
        return C1; 
    }

    private double[] vectorT(int char_id, ArrayList<Integer> missingChars) {
        // Vector T: Vector de freqüències entre caràcter [i] i la resta de caràcters no
        //        emplaçats. Ordre CREIXENT.
        int t_size = missingChars.size();
        double[] vecT = new double[t_size];
        
        for (int i = 0; i < t_size; i++) {
            int other_char = missingChars.get(i);
            double dist_ab = Frequencies[char_id][other_char];
            double dist_ba = Frequencies[other_char][char_id];
            vecT[i] = (dist_ab+dist_ba)/2;
        }
        Arrays.sort(vecT);
        return vecT;
    }
    
    private double[] vectorD(int tecla_id, ArrayList<Integer> missingChars) {
        // Vector D: Vector de distàncies entre tecla [j] i la resta de tecles no emplaçades.
        //        Ordre DECREIXENT.
        int d_size = missingChars.size();
        double[] vecD = new double[d_size];
        
        // Les tecles no omplertes (emplaçades) són les que van des de:
        //      -> missingChars.size() fins n_size
        for (int i = 0; i < d_size; i++) {
            vecD[i] = Distancies[tecla_id][Distancies.length - d_size + i];
        }
        Arrays.sort(vecD);
        double[] reversed_vecD = new double[d_size];
        for (int i = 0; i < d_size; i++) {
            reversed_vecD[i] = vecD[d_size - 1 - i];
        }
        return reversed_vecD;
    }

    private double scalarProduct(double[] vecA, double[] vecB) {
        int result = 0;
        for (int i = 0; i < vecA.length; i++) result += vecA[i]*vecB[i];
        return result;
    }

    private double[][] matrixC2(ArrayList<Integer> partialSol, ArrayList<Integer> missingChars) {
        // Matriu C2:
        //      Files    [i] -> Caràcters no emplaçats
        //      Columnes [j] -> Tecles (posicions) no emplaçades
        //
        //      Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //                cada posició (i,j) de la matriu conté el valor corresponent al producte
        //                escalar de dos vectors T i D.
        //
        //      Vector T: Vector de freqüències entre caràcter [i] i la resta de caràcters no
        //                emplaçats. Ordre CREIXENT.
        //      Vector D: Vector de distàncies entre tecla [j] i la resta de tecles no emplaçades.
        //                Ordre DECREIXENT.
        //
        //      Exemple: Tenim 5 caràcters i 5 posicions en total = (c1...c5) i (p1...p5)
        //               Solució parcial = [c1, c2, ...] -> (c1 en p1, c2 en p2)
        //               Suposem Punt[i][j] = (c3,p5)
        //               - Vector T = [freq(c3-c4), freq(c3-c5)] -> (Ordre creixent)
        //               - Vector D = [dist(p5-p3), dist(p5,p4)] -> (Ordre decreixent)

        // Tots els index des de partialSol.size() fins a n_size-1 són les tecles buides
        int c2_size = missingChars.size();
        double[][] C2 = new double[c2_size][c2_size];
        
        for (int i = 0; i < c2_size; i++) {
            for (int j = 0; j < c2_size; j++) {
                // i,j són 0...c1_size-1 => Els hem de retornar al seu valor corresponent
                // ex: i=0 pot correspondre al charID 3, o j=2 pot correspondre a teclaID 4
                int t_char = missingChars.get(i);
                int t_index = j + partialSol.size();
                double[] vecT = vectorT(t_char, missingChars);
                double[] vecD = vectorD(t_index, missingChars);
                C2[i][j] = scalarProduct(vecT, vecD);
            }
        }
        return C2;
    }

    private double[][] matrixSum(double[][] A, double[][] B) {
        int n = A.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                res[i][j] = A[i][j] + B[i][j];
        }
        return res;
    }

    private double calcF1(ArrayList<Integer> solParcial) {
        // F1 = Suma del cost de les arestes entre tots els caràcters ja emplaçats en una tecla.
        // Cost(a,b) = [(freq(a,b) + freq(a,b))/2] * dist(a,b)
        // 
        // Exemple:
        //      Solució parcial = [c1, c2, c3, c4]
        //      F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3)
        //           + Cost(c2,c4) + Cost(c3,c4)
        
        int n = solParcial.size();
        double res = 0;
        // Per cada element (fins al penúltim) de la solució parcial, obtenir el cost d'aquell
        // element en funció de tots els següents.
        for (int i = 0; i < (n-1); i++) {
            int charID_1 = solParcial.get(i);
            for (int k = i+1; k < n; k++) {
                int charID_2 = solParcial.get(k);
                res += cost(charID_1, i, charID_2, k);
            }
        }
        return res;
    }

    private double calcF2(ArrayList<Integer> solParcial, ArrayList<Integer> solHungarian, ArrayList<Integer> charsMissing) {
        // F2 = Sumatori del cost de cada node en la solució Hungarian en relació amb
        // cada node de la solució parcial.
        //      Exemple F2:
        //          - Solució parcial   : [c1, c2, c3, .., ..]
        //          - Solució Hungarian : [.., .., .., c4, c5]
        //          F2 = [Cost(c4-c1) + Cost(c4-c2) + Cost(c4-c3)]
        //               + [Cost(c5-c1) + Cost(c5-c2) + Cost(c5-c3)]
        
        double cost_f2 = 0;
        for (int i = 0; i < solParcial.size(); i++) {
            int tecla_id = i;
            int char_id  = solParcial.get(i);
            for (int j = 0; j < solHungarian.size(); j++) {
                int tecla_idH = solParcial.size() + j;
                int char_idH  = charsMissing.get(j);
                cost_f2 += cost(char_id, tecla_id, char_idH, tecla_idH);
            }
        }
        return cost_f2;
    }

    private double calcF3(ArrayList<Integer> solParcial, ArrayList<Integer> solHungarian, ArrayList<Integer> charsMissing) {
        // F3 = Aplicar F1 sobre la solució proporcionada per Hungarian.
        //      Exemple F3:
        //          - Solució Hungarian: [.., .., c3, c4, c5]
        //          F3 = Cost(c3-c4) + Cost(c3-c5) + Cost(c4-c5)
        
        double res = 0;
        int n = solHungarian.size();
        for (int i = 0; i < (n-1); i++) {
            int char_id1  = charsMissing.get( solHungarian.get(i) );
            int tecla_id1 = solParcial.size() + i;
            for (int k = i+1; k < n; k++) {
                int char_id2  = charsMissing.get( solHungarian.get(k) );
                int tecla_id2 = solParcial.size() + k;
                res += cost(char_id1, tecla_id1, char_id2, tecla_id2);
            }
        }
        return res;
    }

    private double cotaGilmore(ArrayList<Integer> solParcial, ArrayList<Integer> charsMissing) {
        double cotaGil = 0;

        cotaGil += calcF1(solParcial);

        double[][] matC1 = matrixC1(solParcial, charsMissing);
        double[][] matC2 = matrixC2(solParcial, charsMissing);
        double[][] matHung = matrixSum(matC1, matC2);

        // ##### System.out.println("~> Partial Solution: " + solParcial); // DEBUG

        // El ArrayList de la solució hungarian està al revés dels altres !!!
        //  - Index = Fila de la matriu (=> index fila indica CARACTER)
        //  - Value = Columna on hi ha el 0 de la solució (=> index columna indica TECLA)
        // S'ha de transformar els indexs i valors a IDs reals de caràcters i tecles !!!
        ArrayList<Integer> hungarianSolution = Hungarian.hungarianAlgorithm(matHung);

        // Transformar els valors per a que index sigui la tecla i value el caracter
        // Exemple: {3, 1, 0, 2} := (char 0, tecla 3) + (c1, t1) + (c2, t0) + (c3, t2)...
        //          Passa a ser => {2, 1, 3, 0}
        ArrayList<Integer> stdHungarianSol = new ArrayList<Integer>(hungarianSolution.size());
        for (int i = 0; i < hungarianSolution.size(); i++) stdHungarianSol.add(-1); // Relleno perquè sinó no puc fer el .set(i, x)
            for (int i = 0; i < hungarianSolution.size(); i++) {
            stdHungarianSol.set(hungarianSolution.get(i), i);
        }

        cotaGil += calcF2(solParcial, stdHungarianSol, charsMissing);
        
        cotaGil += calcF3(solParcial, stdHungarianSol, charsMissing);

        // ##### System.out.println("Cota Gilmore: " + cotaGil); // DEBUG
        
        return cotaGil;
    }

    private int greedyStart() {
        // Emplaça el caràcter més freqüent de tots en primera posició de la solució parcial
        double max = 0; // Frequencia més alta
        int index = 0; // CharID del caràcter més frequent
        for (int i = 0; i < AbsoluteFreqs.length; i++) {
            if (AbsoluteFreqs[i] > max) {
                max = AbsoluteFreqs[i];
                index = i;
            }
        }
        return index;
    }

    public ArrayList<Integer> algorithm() {
        ArrayList<Integer> bestSolution = new ArrayList<Integer>();
        ArrayList<Integer> charsMissing = new ArrayList<Integer>(); 
        double bestCota = 0;

        for (int i = 0; i < this.n_size; i++) charsMissing.add(i); // Omplir charMissing

        ArrayList<Integer> bestIterMissing = new ArrayList<Integer>();
        ArrayList<Integer> iterMissing = new ArrayList<Integer>();
        
        ArrayList<Integer> bestIterSolution = new ArrayList<Integer>();
        ArrayList<Integer> iterSolution = new ArrayList<Integer>();
        
        double bestIterCota = 0;
        double iterCota = 0;

        // Greedy Start to improve efficiency
        int most_frequent = greedyStart();
        bestSolution.add(most_frequent);
        charsMissing.remove((Integer)most_frequent);

        while (charsMissing.size() > 0) {
            bestIterSolution.clear();
            bestIterCota = 0;

            for (int char_id : charsMissing) {
                iterSolution.clear();
                iterSolution.addAll(bestSolution);
                iterSolution.add(char_id);
       
                iterMissing.clear();
                iterMissing.addAll(charsMissing);
                iterMissing.remove((Integer)char_id);
                
                iterCota = cotaGilmore(iterSolution, iterMissing);
                
                if (bestIterSolution.size() == 0 || iterCota < bestIterCota) {
                    bestIterSolution.clear();
                    bestIterSolution.addAll(iterSolution);

                    bestIterMissing.clear();
                    bestIterMissing.addAll(iterMissing);
                    
                    bestIterCota = iterCota;
                }
            }
            bestSolution.clear();
            bestSolution.addAll(bestIterSolution);
        
            charsMissing.clear();
            charsMissing.addAll(bestIterMissing);

            bestCota = bestIterCota;
        }
        System.out.println("Cota Gilmore: " + bestCota); // DEBUG
        return bestSolution;
    }

    public ArrayList<Integer> generarTeclat(double[][] freq_matrix, double[] abs_frequencies, double[][] dist_matrix) {
        // Totes les matrius són quadrades, de mida n_size
        this.AbsoluteFreqs = abs_frequencies;
        this.Frequencies = freq_matrix;
        this.Distancies = dist_matrix;
        this.n_size = freq_matrix.length;
        return algorithm();
    }
}

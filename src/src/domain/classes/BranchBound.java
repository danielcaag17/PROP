package src.domain.classes;

import java.util.*;


public class BranchBound implements Strategy {
    private double[] AbsoluteFreqs;  // Array de freqüències absolutes de cada caràcter
    private double[][] Frequencies;  // Matriu de freqüències per a cada parella de caràcters (i,j)
    private double[][] Distancies;   // Matriu de distàncies per a cada parella de tecles (i,j)
    private int n_size;              // Nombre de caràcters = Nombre de tecles (l'array de
                                     // freqüències és de mida n_size, i les matrius n_size*n_size)

    private void printProgressBar(int percentage) {
        // Pinta una barra de progrés que es va actualitzant en funció del percentatge
        // que rep com a paràmetre.
        int size = 30;
        int nbars = (int) ((double)percentage/100*size);
        int nspaces = size - nbars - 1;
        if (percentage == 100) System.out.println("\rProgress: [" + "=".repeat(size) + "] 100%");
        else {
            System.out.print("\rProgress: [" + "=".repeat(nbars) + ">" + " ".repeat(nspaces)
                             + "] " + percentage + "%");
        }
    }


    private double cost(int c1, int p1, int c2, int p2) {
        // Retorna el cost de l'aresta entre (c1,p1) i (c2,p2) on:
        //  - c1 i c2 són els IDs de caràcter
        //  - p1 i p2 són les posicions (tecles) dels respectius caràcters
        double freq_ab = this.Frequencies[c1][c2];
        double freq_ba = this.Frequencies[c2][c1];
        double dist_ab = this.Distancies[p1][p2];
        return ((freq_ab+freq_ba)/2)*dist_ab;
    }
    
    private double[][] matrixC1(ArrayList<Integer> partialSol, ArrayList<Integer> missingChars) {
        // Retorna la matriu C1, necessària per al càlcul del Hungarian Algorithm.
        //
        // Matriu C1:   Files    [i] -> Caràcters no emplaçats
        //              Columnes [j] -> Tecles (posicions) no emplaçades
        //
        // Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //           cada posició (i,j) de la matriu conté el valor corresponent al cost
        //           de posar el caràcter [i] a la posició [j] en relació amb tots els
        //           caràcters ja emplaçats en la solució parcial.
        //      
        // Exemple:
        //     Solució parcial = [c1, c2, c3, ...]
        //     Per a qualsevol (i,j) de C1 suposem = Sol. [c1, c2, c3, ..., c(i), ...]
        //     C1[i][j] = Cost de les arestes entre c[i] en tecla[j] i totes les tecles
        //                ja emplaçades en la solució parcial.

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
        // Retorna el vector T, necessari per al càlcul de la matriu C2.
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
        // Retorna el vector D, necessari per al càlcul de la matriu C2.
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
        // Retorna el producte escalar de dos vectors de mida igual.
        int result = 0;
        for (int i = 0; i < vecA.length; i++) result += vecA[i]*vecB[i];
        return result;
    }

    private double[][] matrixC2(ArrayList<Integer> partialSol, ArrayList<Integer> missingChars) {
        // Retorna la matriu C2, necessària per al càlcul del Hungarian Algorithm.
        //
        // Matriu C2:   Files    [i] -> Caràcters no emplaçats
        //              Columnes [j] -> Tecles (posicions) no emplaçades
        //
        // Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //           cada posició (i,j) de la matriu conté el valor corresponent al producte
        //           escalar de dos vectors T i D.
        //
        // Exemple: Tenim 5 caràcters i 5 posicions en total = (c1...c5) i (p1...p5)
        //          Solució parcial = [c1, c2, ...] -> (c1 en p1, c2 en p2)
        //          Suposem Punt[i][j] = (c3,p5)
        //          - Vector T = [freq(c3-c4), freq(c3-c5)] -> (Ordre creixent)
        //          - Vector D = [dist(p5-p3), dist(p5,p4)] -> (Ordre decreixent)

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
        // Retorna la suma de dues matrius de mida igual.
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
        // 
        // Exemple: Solució parcial = [c1, c2, c3, c4]
        //          F1 = Cost(c1,c2)+Cost(c1,c3)+Cost(c1,c4)+Cost(c2,c3)+Cost(c2,c4)+Cost(c3,c4)
        
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

    private double costHungarian(ArrayList<Integer> solHungarian, double[][] matHungarian) {
        // Retorna el cost de l'assignació òptima calculada per Hungarian Algorithm.
        //
        // El ArrayList de la solució hungarian és de la forma:
        //  - Index = Fila de la matriu (=> index fila indica CARACTER)
        //  - Value = Columna on hi ha el 0 de la solució (=> index columna indica TECLA)
        double res = 0;
        for (int i = 0; i < solHungarian.size(); i++) {
            int j = solHungarian.get(i);
            res += matHungarian[i][j];
        }
        return res;
    }

    private double cotaGilmore(ArrayList<Integer> solParcial, ArrayList<Integer> charsMissing) {
        // Retorna el valor de la cota Gilmore, calculada en funció d'una solució parcial
        // de la qual disposem. Es calcula com a la suma de tres termes, F1+F2+F3.
        //  - F1 ho calculem exacte
        //  - F2+F3 calcument aproximació mitjançant Hungarian Algorithm
        double cotaGil = 0;
        cotaGil += calcF1(solParcial);

        // Calculem les matrius necessàries per a executar el Hungarian Algorithm
        double[][] matC1 = matrixC1(solParcial, charsMissing);
        double[][] matC2 = matrixC2(solParcial, charsMissing);
        double[][] matHung = matrixSum(matC1, matC2);

        // Executem el Hungarian Algorithm i calculem el cost de l'assignació òptima
        ArrayList<Integer> hungarianSolution = Hungarian.hungarianAlgorithm(matHung);
        cotaGil += costHungarian(hungarianSolution, matHung);
        
        return cotaGil;
    }

    private int[] mostFrequentChars(int n_chars) {
        // Retorna una array que conté els indexos dels n_chars caràcters més frequents.
        // Serveix per a executar l'algoritme de branching de forma greedy, és a dir, que
        // comencem amb una solució parcial on ja tenim alguns elements emplaçats.
        // (Addicionalment, és escalable, per a poder determinar quin n_chars funciona millor)
        //
        // La funció ordena el vector de freqüències absolutes, per tal de determinar les N
        // freqüències més altes, les guarda i busca a quin índex pertanyen aquestes freqüències.
        // L'índex serà el ID del caràcter.
        ArrayList<Double> auxFreqsList = new ArrayList<>(n_size);
        for (int i = 0; i < n_size; i++) auxFreqsList.add(AbsoluteFreqs[i]);
        
        double[] sortedFreqs = AbsoluteFreqs.clone();
        Arrays.sort(sortedFreqs);
        
        double[] top_freqs = new double[n_chars];
        for (int i = 1; i <= n_chars; i++) top_freqs[i-1] = sortedFreqs[n_size-i];
        
        int[] res = new int[n_chars];
        for (int i = 0; i < n_chars; i++) {
            double auxFreq = top_freqs[i];
            int target_index = auxFreqsList.indexOf(auxFreq);
            // Un cop sabem l'index de la frequencia que busquem, sobreescrivim el valor de la
            // frequencia en la llista auxiliar de frequencies, d'aquesta forma si tenim més
            // d'un index amb la mateixa frequencia no estem afegint dos cops el mateix character ID
            // al resultat.
            auxFreqsList.set(target_index, -1.0);
            res[i] = target_index;
        }
        return res;
    }

    private ArrayList<Integer> algorithm() {
        // Retorna un ArrayList amb el millor emplaçament possible aproximat per al Quadratic
        // Assignment Problem, fent servir un algoritme de Branching similar al eager, amb un
        // enfocament greedy per a millorar el temps d'execució, i una funció de bounding coneguda
        // com a cota Gilmore, que es calcula amb l'ajuda del Hungarian Algorithm.
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

        // Greedy Start => Definim un greedy_lvl, que ens indica quin nombre d'elements volem
        // emplaçar a la solució parcial abans d'inicialitzar l'algoritme, i escull els N caràcters
        // més freqüents.
        int greedy_lvl = 4;
        int[] greedyStart = mostFrequentChars(greedy_lvl);
        for (int i = 0; i < greedy_lvl; i++) {
            bestSolution.add(greedyStart[i]);
            charsMissing.remove((Integer)greedyStart[i]);
        }
        
        // Progress Bar
        int n_iterations = 0;
        int max_iterations = 1;
        for (int i = n_size; i > 1; i--) max_iterations += i;
        int percent = 0;

        // Branching algorithm: Per a cada iteració es calcula la cota dels possibles caràcters
        // que poden precedir a la solució parcial, s'escull el caràcter amb la cota més baixa
        // i s'afegeix a la solució parcial abans de passar a la següent iteració.
        while (charsMissing.size() > 0) {
            bestIterSolution.clear();
            bestIterCota = 0;

            for (int char_id : charsMissing) {
                // Progress Bar
                percent = (int)((double) n_iterations / max_iterations * 100);
                printProgressBar(percent);

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
                n_iterations++;
            }
            bestSolution.clear();
            bestSolution.addAll(bestIterSolution);
        
            charsMissing.clear();
            charsMissing.addAll(bestIterMissing);

            bestCota = bestIterCota;
        }
        // Progress Bar
        printProgressBar(100);
        
        return bestSolution;
    }

    public ArrayList<Integer> generarTeclat(double[][] freq_matrix, double[] abs_frequencies, double[][] dist_matrix) {
        // Actualitza/estableix els valors dels atributs de la classe i retorna la solució
        // aproximada per al Quadratic Assignment Problem.
        this.AbsoluteFreqs = abs_frequencies;
        this.Frequencies = freq_matrix;
        this.Distancies = dist_matrix;
        this.n_size = freq_matrix.length;
        return algorithm();
    }
}

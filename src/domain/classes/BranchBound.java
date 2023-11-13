package src.domain.classes;

public class BranchBound implements Strategy {

    private double[][] Frequencies;
    private double[][] Distances;
    private int n_size;

    public BranchBound(double[][] freq_matrix, double[][] dist_matrix) {
        // Totes les matrius són quadrades, de mida n_size
        this.Frequencies = freq_matrix;
        this.Distances = dist_matrix;
        this.n_size = freq_matrix.length;
    }

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
            vecD[i] = Distancies[tecla_id][d_size+i];
        }
        Arrays.sort(vecD);
        double[] reversed_vecD = new double[d_size];
        for (int i = 0; i < d_size; i++) reversed_vecD[i] = vecD[d_size-i];
        return reversed_vecD;
    }

    private double scalarProduct(double[] vecA, double[] vecB) {
        int result = 0;
        for (int i = 0; i < vecA.length; i++) result += vecA[i]*vecB[i];
        return result;
    }

    private double[][] matrixC2(ArrayList<Integer> partialSol, ArrayList<Integer> missingChars) {
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

    private double cotaGilmore(ArrayList<Integer> partial_sol) {
        // 1r terme => Calculable
        //
        // F1 = Suma del cost de les arestes entre tots els caràcters ja emplaçats en una tecla.
        // Cost(a,b) = [(freq(a,b) + freq(a,b))/2] * dist(a,b)
        // 
        // Exemple:
        //      Solució parcial = [c1, c2, c3, c4] **
        //      F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3)
        //           + Cost(c2,c4) + Cost(c3,c4)
        //
        // ** Apunt: En el vector solució, l'index del vector indica la tecla/posició, i el
        //           contingut de cada índex és el caràcter que correspon a aquella posició.
        //           ex: Solució parcial = [c5, c2, ...]
        //               El caràcter 5 està assignat a la tecla 0
        //               El caràcter 2 està assignat a la tecla 1
        //
        //
        // 2n + 3r terme => Aproximació => Hungarian Algorithm
        // Hem de crear dues matrius: C1 i C2 => (C1+C2) és el punt de partida del Hungarian
        //
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
        //
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
        //
        //
        // Fem Hungarian Algorithm amb (C1+C2), i ens retorna una matriu quadrada (N-m)*(N-m)
        // amb una solució "òptima" per a els caràcters i tecles que encara no són emplaçats
        // en la solució parcial. La matriu la podem representar com un vector, tal com en la
        // solució parcial.
        //      # Explicació pas per pas de Hungarian Algorithm i Línies Mínimes en el PDF.
        //
        //
        // Càlcul 2n terme cota Gilmore:
        //      F2 = Sumatori del cost de cada node en la solució Hungarian en relació amb cada
        //           node de la solució parcial.
        //      
        //      Exemple F2:
        //          - Solució parcial   : [c1, c2, c3, .., ..]
        //          - Solució Hungarian : [.., .., .., c4, c5]
        //          F2 = [Cost(c4-c1) + Cost(c4-c2) + Cost(c4-c3)]
        //               + [Cost(c5-c1) + Cost(c5-c2) + Cost(c5-c3)]
        //
        //
        // Càlcul 3r terme cota Gilmore:
        //      F3 = Aplicar F1 sobre la solució proporcionada per Hungarian.
        //      
        //      Exemple F3:
        //          - Solució Hungarian: [.., .., c3, c4, c5]
        //          F3 = Cost(c3-c4) + Cost(c3-c5) + Cost(c4-c5)
        //
        //
        // Finalment, Cota Gilmore => F1 + F2 + F3
        //
        return 0.0;
    }

    private ArrayList<Integer> branchAlgorithm() {
        ArrayList<Integer> bestSolution = new ArrayList<Integer>();
        ArrayList<Integer> charsMissing = new ArrayList<Integer>(); 
        double bestCota = 0;

        for (int i = 0; i < this.n_size; i++) charsMissing.add(i); // Omplir charMissing

        ArrayList<Integer> bestIterSolution = new ArrayList<Integer>();
        ArrayList<Integer> iterSolution = new ArrayList<Integer>();
        double bestIterCota = 0;
        double iterCota = 0;
        int addedCharID = 0;
        
        while (charsMissing.size() > 0) {
            bestIterSolution.clear();
            bestIterCota = 0;

            for (int char_id : charsMissing) {
                iterSolution.clear();
                iterSolution.addAll(bestSolution);
                iterSolution.add(char_id);
                iterCota = cotaGilmore(iterSolution);
                
                if (bestIterSolution.size() == 0 || iterCota < bestIterCota) {
                    bestIterCota = iterCota;
                    bestIterSolution.addAll(iterSolution);
                    addedCharID = char_id;
                }
            }
            bestSolution.clear();
            bestSolution.addAll(bestIterSolution);
            bestCota = bestIterCota;
            charsMissing.remove((Integer)addedCharID);
        }
        return bestSolution;
    }
    
    public void generarTeclat() {
        // Ignorar de moment 
    }
}

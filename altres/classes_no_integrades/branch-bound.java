// Branching algorithm:
//      Tenim solució parcial. Per totes les lletres encara no emplaçades, fem
//      solucio_parcial+lletra i calculem la cota. Agafem la cota més baixa, i
//      solucio_parcial = solucio_parcial+lletra.
import java.util.*;

class Main {
    public static void main(String[] args) {
        // BranchBound bb = new BranchBound(5);
        // System.out.println("Solution: " + bb.algorithm());
    }
}

class BranchBound {
    private double[][] Frequencies;
    private double[][] Distancies;
    private int n_size;

    public BranchBound(double[][] freq_matrix, double[][] dist_matrix) {
        this.Frequencies = freq_matrix;
        this.Distancies = dist_matrix;
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
        int n = solParcial.size();
        double res = 0;
        // Per cada element (fins al penúltim) de la solució parcial, obtenir el cost d'aquell
        // element en funció de tots els següents.
        // Exemple:
        //   Solució parcial = [c1, c2, c3, c4]
        //   F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3) + Cost(c2,c4) + Cost(c3,c4)
        for (int i = 0; i < (n-1); i++) {
            for (int k = i+1; i < n; k++) {
                int charID_1 = solParcial.get(i);
                int charID_2 = solParcial.get(k);
                res += cost(charID_1, i, charID_2, k);
            }
        }
        return res;
    }

    private double cotaGilmore(ArrayList<Integer> solParcial) {
        double cotaGil = 0;
        
        // Implementacio equis de =========
        for (int i : solParcial) cotaGil += i;
        // Ignorar ========================
        
        return cotaGil;
    }

    public ArrayList<Integer> algorithm() {
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
}

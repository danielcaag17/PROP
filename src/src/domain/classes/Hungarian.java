package src.domain.classes;

import java.util.*;

public class Hungarian {
    
    private static void printMatrix(double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            System.out.print("[  ");
            for (int j = 0; j < mat.length; j++) {
                System.out.print(mat[i][j] + "  ");
            }
            System.out.print("]\n");
        }
        System.out.print("\n");
    }

    private static ArrayList<Integer> mostCompleteAssig(double[][] mat) {
        int n = mat.length;
        // selected_zeros => Index = Fila de la matriu
        //                   Value = Columna on hi ha 0 (-1 si no existeix)
        ArrayList<Integer> selected_zeros = new ArrayList<Integer>(n);
        
        CompleteAssignation ca = new CompleteAssignation(mat);
        int[] arr_assig = ca.mostCompleteAssig();

        for (int i = 0; i < n; i++) selected_zeros.add(arr_assig[i]);

        return selected_zeros;
    }

    private static boolean[][] calcMinimumLines(double[][] mat) {
        // 1. Obtenir assignació més completa possible, amb el número més gran possible de files
        //    amb un zero assignat. => backtracking (ho diu al pdf)
        // 2. Marcar les files sense assignació
        // 3. Marcar totes les columnes amb algun zero en una fila marcada. Marcar totes les files
        //    que tinguin la seva assignacio en una columna marcada. Repetir fins a acabar el bucle.
        // 4. Les línies mínimes són les que sorgeixen de recobrir les columnes marcades i les files
        //    no marcades
        int n = mat.length;
        double[][] auxMat = new double[n][n];
        // Hem de copiar les matrius així ...
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                auxMat[i][j] = mat[i][j];
            }
        }
        // Pas 1: Obtenim assignació més completa + files sense assignació marcades amb -1
        ArrayList<Integer> assig = mostCompleteAssig(auxMat);
        
        ArrayList<Boolean> markedRows = new ArrayList<Boolean>(n);
        ArrayList<Boolean> markedCols = new ArrayList<Boolean>(n);
        for (int i = 0; i < n; i++) {
            markedRows.add(false);
            markedCols.add(false);
        }

        // Pas 2: Marcar files sense assignació (valor -1) en el vector de files marcades
        for (int i = 0; i < assig.size(); i++) {
            if (assig.get(i) == -1) markedRows.set(i, true);
        }
        // Pas 3: Bucle:
        int changes = 1;
        while (changes > 0) {
            // Es una chapuza aquesta forma de controlar iteracions pero no tinc cap idea millor lol
            changes = 0;
            // -> Marcar totes les columnes amb algun zero en una fila marcada.
            for (int i = 0; i < n; i++) {
                if (markedRows.get(i)) { // fila (i) marcada
                    for (int j = 0; j < n; j++) {
                        if (auxMat[i][j] == 0 && !markedCols.get(j)) {
                            markedCols.set(j, true);
                            // System.out.println("markedCols " + i + " set to true");
                            changes++;
                            break;
            }   }   }   }    
            // -> Marcar totes les files que tinguin la seva assignacio en una columna marcada.
            for (int i = 0; i < n; i++) {
                if (assig.get(i) != -1) { // Assignació en fila (i)
                    int col_idx = assig.get(i);
                    if (markedCols.get(col_idx) && !markedRows.get(i)) { // Columna assignació marcada
                        markedRows.set(i, true);
                        // System.out.println("markedRows " + i + " set to true");
                        changes++;
                        break;
            }   }   }
            // System.out.println(changes);
        }
        // Pas 4: Recobrir columnes marcades i files no marcades
        boolean[][] result = new boolean[2][n];
        // result[0] = vector que indica files cobertes (files NO marcades)
        // result[1] = vector que indica columnes cobertes (columnes SI marcades)
        for (int i = 0; i < n; i++) {
            if (!markedRows.get(i)) result[0][i] = true;
            else result[0][i] = false;
            if (markedCols.get(i)) result[1][i] = true;
            else result[1][i] = false;
        }
        return result;
    }

    private static int numberOfLines(boolean[][] mat) {
        // La matriu d'entrada ha de ser la que ens retorna calcMinimumLines !!!
        int nlines = 0;
        // nlines ha de ser igual al nombre de true's en els dos vector que conté la matriu
        for (int i = 0; i < mat[0].length; i++) {
            if (mat[0][i]) nlines++;
            if (mat[1][i]) nlines++;
        }
        return nlines;
    }

    private static double minValRow(double[][] mat, int row) {
        int n = mat[row].length;
        double min = mat[row][0];
        for (int i = 1; i < n; i++) {
            if (mat[row][i] < min) min = mat[row][i];
        }
        return min;
    }

    private static double minValColumn(double[][] mat, int col) {
        int n = mat.length;
        double min = mat[0][col];
        for (int i = 1; i < n; i++) {
            if (mat[i][col] < min) min = mat[i][col];
        }
        return min;
    }

    private static double minNonCovered(double[][] mat, boolean[][] covLines) {
        double target = 999999;
        for (int i = 0; i < mat.length; i++) {
            if (!covLines[0][i]) { // fila no està coberta
                for (int j = 0; j < mat.length; j++) {
                    if (!covLines[1][j]) { // columna no està coberta
                        if (mat[i][j] < target) target = mat[i][j];
                    }
                }
            }
        }
        return target;
    }

    private static double minMatrix(double[][] mat) {
        double target = 999999;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j] < target) target = mat[i][j];
            }
        }
        return target;
    }

    public static ArrayList<Integer> hungarianAlgorithm(double[][] mat) {
        // Partim d'una matriu, en el nostre cas sempre quadrada.
        // Es resta de cada fila el valor mínim d'aquella fila
        // Es resta de cada columna el valor mínim d'aquella columna
        // 
        // While no tenim un zero en cada fila i cada columna de la matriu
        // - Recobrir cada zero de la matriu utilitzant el minim nombre possible de línies.
        //   Algoritme de línies mínimes. Si necessitem n línies en una matriu n*n significa
        //   que tenim un zero en cada columna i fila, i per tant hem acabat.
        // - Afegir a cada nombre cobert el mínim nombre no cobert. Si un nombre està cobert
        //   per dues línies, afegim el mínim dues vegades.
        // - Restar el mínim element de la matriu a cada element de la matriu, tornar al primer pas.
        // 
        // A l'acabar tenim un 0 per cada fila i columna de la matriu. El conjunt de n zeros
        // que ens dona una assignació factible ens dóna l'assignació òptima que buscàvem.
        
        // printMatrix(mat); // DEBUG
        
        int n = mat.length;
        double[][] auxMat = new double[n][n];
        // Hem de copiar les matrius així ...
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                auxMat[i][j] = mat[i][j];
            }
        }
        // Restar de cada fila el valor mínim d'aquella fila
        for (int i = 0; i < n; i++) {
            double min_row = minValRow(auxMat, i);
            for (int j = 0; j < n; j++) {
                auxMat[i][j] -= min_row;
            }
        }
        // Restar de cada columna el valor mínim d'aquella columna
        for (int j = 0; j < n; j++) {
            double min_col = minValColumn(auxMat, j);
            for (int i = 0; i < n; i++) {
                auxMat[i][j] -= min_col;
            }
        }

        // printMatrix(auxMat); // DEBUG

        // ##### System.out.println("calcMinimumLines in process...");
        // Obtenir línies mínimes
        boolean[][] minLines = calcMinimumLines(auxMat);
        int nMinLines = numberOfLines(minLines);
        /*
        System.out.println(nMinLines); // DEBUG
        for (int i = 0; i < n; i++) System.out.print(minLines[0][i] + " "); // DEBUG
        System.out.println(""); // DEBUG
        for (int i = 0; i < n; i++) System.out.print(minLines[1][i] + " "); // DEBUG
        System.out.println(""); // DEBUG
        */
        double valMinNonCovered;
        double valMinMatrix;

        while (nMinLines != n) {
            // Afegir a cada nombre cobert el mínim nombre no cobert. Si un nombre està cobert
            // per dues línies, afegim el mínim dues vegades.
            valMinNonCovered = minNonCovered(auxMat, minLines);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (minLines[0][i]) auxMat[i][j] += valMinNonCovered;
                    if (minLines[1][j]) auxMat[i][j] += valMinNonCovered;
                }
            }
            // Restar el mínim element de la matriu a cada element de la matriu, tornar al primer pas.
            valMinMatrix = minMatrix(auxMat);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    auxMat[i][j] -= valMinMatrix;
                }
            }
            // Recalcular
            minLines = calcMinimumLines(auxMat);
            nMinLines = numberOfLines(minLines);
        }

        // printMatrix(auxMat); // DEBUG

        // Index = Fila de la matriu (=> index fila indica CARACTER)
        // Value = Columna on hi ha el 0 de la solució (=> index columna indica TECLA)
        ArrayList<Integer> solutionHungarian = mostCompleteAssig(auxMat);

        // ##### System.out.println("Hungarian Solution: " + solutionHungarian); // DEBUG
        
        return solutionHungarian;
    }
}

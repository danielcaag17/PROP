package src.domain.classes;

import java.util.*;

public class CompleteAssignation {
    private double [][] matrix;
    private int n;

    private boolean[] columnLabel;
    private int[] currentAssig;
    private int[] bestAssig;
    private int bestLines;

    public CompleteAssignation(double[][] mat) {
        // Creadora de la classe, per a inicialitzar tots els atributs necessaris per al
        // càlcul de la millor assignació.
        this.n = mat.length;
        this.bestLines = 0;
        
        this.matrix = new double[n][n];
        this.columnLabel = new boolean[n];
        this.currentAssig = new int[n];
        this.bestAssig = new int[n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) matrix[i][j] = mat[i][j];
        }
        for (int i = 0; i < n; i++) columnLabel[i] = false;
        for (int i = 0; i < n; i++) currentAssig[i] = -1;
        for (int i = 0; i < n; i++) bestAssig[i] = -1;
    }

    private void backtrackingAssig(int row, int currLines) {
        // Algorisme de backtracking que calcula la millor assignació possible => És el coll
        // d'ampolla de totes les classes que l'implementen.
        if (row == n) {
            // En cas que arriba al final de l'algorisme, comprova si la solució és millor que 
            // la que tenim guardada fins al moment, i l'actualitza si és necessari.
            if (currLines > bestLines) {
                bestLines = currLines;
                for (int i = 0; i < n; i++) bestAssig[i] = currentAssig[i];
            }
            return;
        }
        // Optimització per a acabar abans una execució si es dona el cas que la millor solució
        // que puguéssim obtenir fos inferior a la millor assignació que ja tenim guardada.
        if (currLines + (n - row) < bestLines) return;
        // Donada una fila row, per a cada columna col comprovem si el valor que estem tractant
        // és un zero, si no n'existeix cap en la mateixa columna, i si és així executem la crida
        // recursiva tot indicant el valor aquest com a part de la assignació actual.
        for (int col = 0; col < n; col++) {
            if (matrix[row][col] == 0 && !columnLabel[col]) {
                currentAssig[row] = col;
                columnLabel[col] = true;
                currLines++;
                backtrackingAssig(row+1, currLines);
                currentAssig[row] = -1;
                columnLabel[col] = false;
                currLines--;
            }
        }
        backtrackingAssig(row+1, currLines);
    }

    public int[] mostCompleteAssig() {
        // Retorna la millor assignació possible, és a dir, la millor combinació de zeros en
        // files i columnes diferents, utilitzant un algorisme de backtracking.
        backtrackingAssig(0, 0);
        return this.bestAssig;
    }
}

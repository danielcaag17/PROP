import java.util.*;


class CompleteAssignation {
    private double [][] matrix;
    private int n;

    private boolean[] columnLabel;
    private int[] currentAssig;
    private int[] bestAssig;
    // private int currentLines;
    private int bestLines;

    public CompleteAssignation(double[][] mat) {
        this.n = mat.length;
        // this.currentLines = 0;
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

    private void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println("");
    }

    private void backtrackingAssig(int row, int currLines) {
        // System.out.println(n);
        // System.out.println(row);
        // printArray(currentAssig);
        if (row == n) {
            if (currLines > bestLines) {
                bestLines = currLines;
                for (int i = 0; i < n; i++) bestAssig[i] = currentAssig[i];
            }
            return;
        }
        if (currLines + (n - row) < bestLines) return;
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
            else backtrackingAssig(row+1, currLines);
        }
    }

    public int[] mostCompleteAssig() {
        backtrackingAssig(0, 0);
        // System.out.println("Final Best Assig: " + Arrays.toString(bestAssig));
        // System.out.println("Final Best Lines: " + bestLines);
        return this.bestAssig;
    }
}

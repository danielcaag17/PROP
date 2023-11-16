import java.util.*;

class Main {
    public static void main(String[] args) {
        double[][] matrix = {{0, 0, 0, 0},
                             {0, 2, 2, 2},
                             {0, 1, 3, 3},
                             {0, 0, 0, 0}};
        MostComplete mc = new MostComplete(matrix);
        int[] solution = mc.mostCompleteAssig();
    }
}

class MostComplete {
    private double [][] matrix;
    private int n;

    private boolean[] columnLabel;
    private int[] currentAssig;
    private int[] bestAssig;
    private int currentLines;
    private int bestLines;

    public MostComplete(double[][] mat) {
        this.n = mat.length;
        
        this.matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) matrix[i][j] = mat[i][j];
        }

        this.columnLabel = new boolean[n];
        for (int i = 0; i < n; i++) columnLabel[i] = false;
        
        this.currentAssig = new int[n];
        for (int i = 0; i < n; i++) currentAssig[i] = -1;
        
        this.bestAssig = new int[n];
        for (int i = 0; i < n; i++) bestAssig[i] = -1;
        
        this.currentLines = 0;
        this.bestLines = 0;
    }

    private void backtrackingAssig(int row) {
        if (row == n) {
            if (currentLines > bestLines) {
                bestLines = currentLines;
                for (int i = 0; i < n; i++) bestAssig[i] = currentAssig[i];
            }
            return;
        }
        else {
            for (int col = 0; col < n; col++) {
                if (matrix[row][col] == 0 && !columnLabel[col]) {
                    currentAssig[row] = col;
                    columnLabel[col] = true;
                    currentLines++;
                    backtrackingAssig(row+1);
                    currentAssig[row] = -1;
                    columnLabel[col] = false;
                    currentLines--;
                }
                else if (!columnLabel[col]) backtrackingAssig(row+1);
            }
        }
    }

//    public ArrayList<Integer> mostCompleteAssig() {
    public int[] mostCompleteAssig() {
        backtrackingAssig(0);
        // System.out.println("Final Best Assig: " + Arrays.toString(bestAssig));
        // System.out.println("Final Best Lines: " + bestLines);
        return this.bestAssig;
    }
}


import java.util.*;

class Main {
    public static void main(String[] args) {
        double[][] freq_m = {{0.5, 0.4, 0.1, 0.3},
                             {0.2, 0.7, 0.2, 0.4},
                             {0.6, 0.3, 0.3, 0.5},
                             {0.1, 0.4, 0.1, 0.3}};
        double[][] dist_m = {{21, 34, 31, 43},
                             {20, 35, 32, 44},
                             {20, 34, 33, 45},
                             {21, 34, 31, 43}};
        
        BranchBound bb = new BranchBound(freq_m, dist_m);
        System.out.println("Solution: " + bb.algorithm());
    }
}

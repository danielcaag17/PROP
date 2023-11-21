package src.drivers;

import org.junit.Test;
import static org.junit.Assert.*;

import src.domain.classes.CompleteAssignation;

public class DriverCompleteAssignation {
    // 4 tests diferents per Hungarian Algorithm
    private static double[][] test1; // falta assignar valors 
    private static double[][] test2;
    private static double[][] test3;
    private static double[][] test4;
    // Els 4 resultats dels tests previs
    private static int[] result1; // falta assignar valors 
    private static int[] result2;
    private static int[] result3;
    private static int[] result4;

    public static void main(String[] args) {
        // Execució dels diversos tests
        test1(); // potser no fan falta al main
        test2();
        test3();
        test4();
        // (...) se'n poden afegir més
    }

    @Test
    public static void test1() {
        CompleteAssignation ca = new CompleteAssignation(test1);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 1: Correcte\n", result, result1);
    }

    @Test
    public static void test2() {
        CompleteAssignation ca = new CompleteAssignation(test2);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 2: Correcte\n", result, result2);
    }

    @Test
    public static void test3() {
        CompleteAssignation ca = new CompleteAssignation(test3);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 3: Correcte\n", result, result3);
    }

    @Test
    public static void test4() {
        CompleteAssignation ca = new CompleteAssignation(test4);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 4: Correcte\n", result, result4);
    }
}

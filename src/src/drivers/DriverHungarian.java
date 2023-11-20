package src.drivers;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import src.domain.classes.Hungarian;

public class DriverHungarian {
    // 4 tests diferents per Hungarian Algorithm
    private static double[][] test1; // falta assignar valors 
    private static double[][] test2;
    private static double[][] test3;
    private static double[][] test4;
    // Els 4 resultats dels tests previs
    private static ArrayList<Integer> result1; // falta assignar valors 
    private static ArrayList<Integer> result2;
    private static ArrayList<Integer> result3;
    private static ArrayList<Integer> result4;
    
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
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test1);
        assertEquals("Test 1: Correcte\n", result, result1);
    }

    @Test
    public static void test2() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test2);
        assertEquals("Test 2: Correcte\n", result, result2);
    }

    @Test
    public static void test3() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test3);
        assertEquals("Test 3: Correcte\n", result, result3);
    }

    @Test
    public static void test4() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test4);
        assertEquals("Test 4: Correcte\n", result, result4);
    }

}

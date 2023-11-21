package src.drivers;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import src.domain.classes.Hungarian;

public class DriverHungarian {
    // 4 tests diferents per Hungarian Algorithm
    private double[][] test1 = {{21, 34, 31, 43},
                                {20, 35, 32, 44},
                                {20, 34, 33, 45},
                                {21, 34, 31, 43}};
/*
    private double[][] test2;
    private double[][] test3;
    private double[][] test4;
*/
    // Els 4 resultats dels tests previs
    private ArrayList<Integer> result1 = new ArrayList<>(Arrays.asList(2, 0, 1, 3)); 
/*
    private ArrayList<Integer> result2;
    private ArrayList<Integer> result3;
    private ArrayList<Integer> result4;
*/

    @Test
    public void test1() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test1);
        assertEquals("Test 1: Correcte\n", result, result1);
    }

/*
    @Test
    public void test2() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test2);
        assertEquals("Test 2: Correcte\n", result, result2);
    }

    @Test
    public void test3() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test3);
        assertEquals("Test 3: Correcte\n", result, result3);
    }

    @Test
    public void test4() {
        ArrayList<Integer> result = Hungarian.hungarianAlgorithm(test4);
        assertEquals("Test 4: Correcte\n", result, result4);
    }
*/
}

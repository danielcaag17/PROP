package src.drivers;

import org.junit.Test;
import static org.junit.Assert.*;

import src.domain.classes.CompleteAssignation;

public class DriverCompleteAssignation {
    // 4 tests diferents per Hungarian Algorithm
    private double[][] test1;
/*
    private double[][] test2;
    private double[][] test3;
    private double[][] test4;
*/
    // Els 4 resultats dels tests previs
    private int[] result1; 
/*
    private int[] result2;
    private int[] result3;
    private int[] result4;
*/

    @Test
    public void test1() {
        CompleteAssignation ca = new CompleteAssignation(test1);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 1: Correcte\n", result, result1);
    }

/*
    @Test
    public  void test2() {
        CompleteAssignation ca = new CompleteAssignation(test2);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 2: Correcte\n", result, result2);
    }

    @Test
    public  void test3() {
        CompleteAssignation ca = new CompleteAssignation(test3);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 3: Correcte\n", result, result3);
    }

    @Test
    public  void test4() {
        CompleteAssignation ca = new CompleteAssignation(test4);
        int[] result = ca.mostCompleteAssig();
        assertEquals("Test 4: Correcte\n", result, result4);
    }
*/
}

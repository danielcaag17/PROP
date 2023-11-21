package src.drivers;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import src.domain.classes.Hungarian;

public class DriverHungarian {

    public static void main(String[] args) {
        // 4 tests diferents per Hungarian Algorithm
        double[][] test1 = {{21, 34, 31, 43},
                        {20, 35, 32, 44},
                        {20, 34, 33, 45},
                        {21, 34, 31, 43}};

        double[][] test2 = {{82, 83, 69, 92},
                        {77, 37, 49, 92},
                        {11, 69,  5, 86},
                        { 8,  9, 98, 23}};

        double[][] test3 = {{123, 90, 912,  12, 123, 22, 12},
                        {54, 315, 732,  14,  35, 41, 3122},
                        {19, 327,  35,  21,	 75, 32, 12},
                        {13,  87, 321,  73, 237, 32, 31},
                        {35,  32,  78, 654,	 57, 31, 78},
                        {25,  45, 786,  69,  35, 41, 78}};

        double[][] test4 = {{123, 90, 912,  12, 123, 22,  12, 87,  32,	523},
                        {54, 315, 732,  14,  35, 41, 312, 45,  12,	 32},
                        {19, 327,  35,  21,	 75, 32,  12, 34,  87,	 83},
                        {13,  87, 321,  73, 237, 32,  31, 10,  17,	 24},
                        {35,  32,  78, 654,	 57, 31,  78, 96, 354,	 32},
                        {25,  45, 786,  69,  35, 41,  78, 35,  99,	 54},
                        {44, 224,  78, 315,	 75, 23,  78, 56,  56,	 327},
                        {78,  45,  89,	56,	 32, 12,  45, 78,  98,	 32},
                        {64,  79,  65,	32,	 98, 45,  19, 73,  48,	 67},
                        {30,  90,  76,	52,	 15, 32,  78, 65,  12,   77}};                                                    
                        
        // Els 4 resultats dels tests previs
        ArrayList<Integer> result1 = new ArrayList<>(Arrays.asList(2, 0, 1, 3));
        ArrayList<Integer> result2 = new ArrayList<>(Arrays.asList(2, 1, 0, 3)); 
        ArrayList<Integer> result3 = new ArrayList<>(Arrays.asList(5, 3, 2, 0, 1, 4)); 
        ArrayList<Integer> result4 = new ArrayList<>(Arrays.asList(3, 8, 2, 7, 1, 0, 5, 9, 6, 4)); 
        
        // TEST 1
        ArrayList<Integer> res_test1 = Hungarian.hungarianAlgorithm(test1);
        if (result1.equals(res_test1)) System.out.println("[+] test1 -> PASSED!");
        else System.out.println("[!] test1 -> FAILED!");

        // TEST 2
        ArrayList<Integer> res_test2 = Hungarian.hungarianAlgorithm(test2);
        if (result2.equals(res_test2)) System.print.out("[+] test2 -> PASSED!");
        else System.out.println("[!] test2 -> FAILED!");

        // TEST 3
        ArrayList<Integer> res_test3 = Hungarian.hungarianAlgorithm(test3);
        if (result3.equals(res_test3)) System.print.out("[+] test3 -> PASSED!");
        else System.out.println("[!] test3 -> FAILED!");

        // TEST 4
        ArrayList<Integer> res_test4 = Hungarian.hungarianAlgorithm(test4);
        if (result4.equals(res_test4)) System.print.out("[+] test4 -> PASSED!");
        else System.out.println("[!] test4 -> FAILED!");
    }
}

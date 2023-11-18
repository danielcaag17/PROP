package src.domain.classes;

import java.util.*;

public class Generador { // segons el patró estrategia, la classe no ha de ser abstracta
    private Strategy S;

    public Generador () {
        // res
    }

    public Map<Character, Integer> generarTeclat(double[][] freqMatrix, double[][] distMatrix, char[] abcdari, Map<Character, Double> freqAbs) {
        double[] freqAbsArray = getFreqAbsolute(abcdari, freqAbs);
        ArrayList<Integer> resultAlgorithm = S.generarTeclat();   // a definir quina estructura retorna
        Map<Character, Integer> result = 
    }

    private double[] getFreqAbsolute(char[] abcdari, Map<Character, Double> freqAbs) {

    }

    private Map<Character, Integer> beautifyResult(ArrayList<Integer> idsOf, char[] abcdari) {

    }
}

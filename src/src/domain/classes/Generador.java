package src.domain.classes;

import java.util.*;

public class Generador { // segons el patró estrategia, la classe no ha de ser abstracta
    private Strategy S;

    // Veure si sha de fer un new Strategy
    public Generador () {
        // res
    }

    public Map<Character, Integer> generarTeclat(double[][] freqMatrix, double[][] distMatrix, Map<Character, Double> freqAbs) {
        double[] freqAbsArray = getFreqAbsolute(freqAbs);
        ArrayList<Integer> resultAlgorithm = S.generarTeclat(freqMatrix, freqAbsArray, distMatrix);   // a definir quina estructura retorna
        char[] abcdari = getAbcdari(freqAbs);
        return beautifyResult(resultAlgorithm, abcdari);
    }

    private double[] getFreqAbsolute(Map<Character, Double> freqAbs) {
        double[] res = new double[freqAbs.size()];
        int i = 0;
        for (Character c : freqAbs.keySet()) {
            res[i] = freqAbs.get(c);
        }
        return res;
    }

    private char[] getAbcdari (Map<Character, Double> freqAbs) {
        char[] res = new char[freqAbs.size()];
        int i = 0;
        for (Character c : freqAbs.keySet()) {
            res[i] = c;
        }
        return res;
    }

    // IMPORTANT: assig[i] conté el index d'abcdari que conté la lletra final en la posició i, tal que i és la id d'una tecla del layout.
    private Map<Character, Integer> beautifyResult(ArrayList<Integer> assig, char[] abcdari) {
        Map<Character, Integer> posicioCaracters = new HashMap<Character, Integer>();
        for (int i = 0; i < assig.size(); i++) {
            posicioCaracters.put(abcdari[assig.get(i)], i); // afegim l'entrada on extraiem un caràcter i el seu index (id de posició al layout)
        }

        return posicioCaracters;
    }
}

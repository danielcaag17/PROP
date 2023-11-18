package src.domain.classes;

import java.util.*;

public class Generador { // segons el patró estrategia, la classe no ha de ser abstracta
    private Strategy S;

    public Generador () {
        // res
    }

    public Map<Character, Integer> generarTeclat(double[][] freqMatrix, double[][] distMatrix, char[] abcdari, Map<Character, Double> freqAbs) {
        double[] freqAbsArray = getFreqAbsolute(abcdari, freqAbs);
        ArrayList<Integer> resultAlgorithm = S.generarTeclat(freqMatrix, freqAbsArray, distMatrix);   // a definir quina estructura retorna
        return beautifyResult(resultAlgorithm, abcdari);
    }

    private double[] getFreqAbsolute(char[] abcdari, Map<Character, Double> freqAbs) {
        double[] res = new double[abcdari.length];
        for (int i = 0; i<abcdari.length; i++) {
            res[i] = freqAbs.get(abcdari[i]);
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

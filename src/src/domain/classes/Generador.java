package src.domain.classes;

import java.util.*;

import src.exceptions.GeneradorNoValid;

public class Generador {
    private Strategy S;

    /**
     * Pre: 
     * Post: s'ha instanciat la classe Strategy pertinent
     * 
     * @param strategy string per identificar quina estrategia utilitzar.
     * 
     * @throws GeneradorNoValid si no hi ha un tipus d'estrategia demanat per strategy.
     */
    public Generador (String strategy) throws GeneradorNoValid {
        switch(strategy) {
            case "Branch&bound":
                S = new BranchBound();
                break;
            default:
                throw new GeneradorNoValid();
        }
    }

    /**
     * Pre: freqMatrix és una matriu no buida,
     *      distMatrix és una matriu no buida,
     *      freqAbs és un Map no buit,
     *      tots els paràmetres tenen la mateixa mida
     * Post: es retorna les associacions generades entre les lletres de l'Alfabet i les ids del Layout
     * 
     * @param freqMatrix matriu de freqüències que donada una lletra aparegui la següent.
     * @param distMatrix matriu amb les distàncies entre les ids del Layout.
     * @param freqAbs estructura de dades que conté la freqüència de cada lletra de l'Alfabet.
     * 
     * @return el càlcul fet pel Generador que assigna una lletra d'Alfabet a un id del Layout
     */
    public Map<Character, Integer> generarTeclat(double[][] freqMatrix, double[][] distMatrix, Map<Character, Double> freqAbs) {
        double[] freqAbsArray = getFreqAbsolute(freqAbs);                                               // Obtenir en un Array les freqüencies de les lletres
        ArrayList<Integer> resultAlgorithm = S.generarTeclat(freqMatrix, freqAbsArray, distMatrix);     // Crida a Strategy per generar el teclat
        char[] abcdari = getAbcdari(freqAbs);                                                           // Obtenir en un Array les lletres de l'Alfabet
        return beautifyResult(resultAlgorithm, abcdari);                                                // Associar les lletres amb les ids
    }

    /**
     * Pre: freqAbs és un Map no buit
     * Post: es retorna un Array amb les freqüències de les lletres
     * 
     * @param freqAbs estructura de dades que conté la freqüència de cada lletra de l'Alfabet.
     * 
     * @return l'Array amb les freqüències de les lletres
     */
    private double[] getFreqAbsolute(Map<Character, Double> freqAbs) {
        double[] res = new double[freqAbs.size()];
        int i = 0;
        for (Character c : freqAbs.keySet()) {
            res[i] = freqAbs.get(c);
            i++;
        }
        return res;
    }

    /**
     * Pre: freqAbs és un Map no buit
     * Post: es retorna un Array amb les lletres de l'Alfabet
     * 
     * @param freqAbs estructura de dades que conté la freqüència de cada lletra de l'Alfabet.
     * 
     * @return l'Array amb les lletres de l'Alfabet
     */
    private char[] getAbcdari (Map<Character, Double> freqAbs) {
        char[] res = new char[freqAbs.size()];
        int i = 0;
        for (Character c : freqAbs.keySet()) {
            res[i] = c;
            i++;
        }
        return res;
    }

    /**
     * Pre: assig és un ArrayList no buit,
     *      abcdari és un Array no buid,
     *      assig i abcdari tenen la mateixa mida
     * Post: es retorna un Map amb les associacions entre les lletres i les ids
     * 
     * @param assig ArrayList on assig[i] conté l'índex d'abcdari que conté la lletra en la posició i, tal que i és la id d'una tecla del layout.
     * @param abcdari matriu amb les distàncies entre les ids del Layout.
     * 
     * @return el càlcul fet pel Generador que assigna una lletra d'Alfabet a un id del Layout
     */
    private Map<Character, Integer> beautifyResult(ArrayList<Integer> assig, char[] abcdari) {
        Map<Character, Integer> posicioCaracters = new HashMap<Character, Integer>();
        for (int i = 0; i < assig.size(); i++) {
            posicioCaracters.put(abcdari[assig.get(i)], i);             // Afegir l'entrada on extraiem un caràcter i el seu index (id de posició al layout)
        }
        return posicioCaracters;
    }
}

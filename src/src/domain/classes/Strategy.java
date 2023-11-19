package src.domain.classes;

import java.util.*;

public interface Strategy {

    /**
     * Pre:
     * Post: s'han generat les ids per cada lletra
     * 
     * @param frequencyMat es la matriu de freqüencies entre lletres del Teclat.
     * @param frequencyAbsolute es un arry amb les freqüències de cada lletra del Teclat
     * @param distanceMat es la matriu de les distàncies del Layout
     * 
     * @return la id de cada lletra ordenades
     */
    public ArrayList<Integer> generarTeclat(double[][] frequencyMat, double[] frequencyAbsolute, double[][] distanceMat);
}

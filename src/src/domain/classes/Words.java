package src.domain.classes;

import java.io.*;
import java.util.*;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public class Words implements StrategyAlfabet {
    
    /**
     * Pre:
     * Post: es retorna l'Alfabet caculat amb l'entrada doanada
     * 
     * @param path string que indica on està l'entrada que defineix l'Alfabet
     * 
     * @return l'Alfabet resultat de l'entrada donada
     */
    @Override
    public Alfabet read(String path) throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        HashMap<String, Double> words = getWords(path);                             // Llegir l'entrada
        Alfabet a = new Alfabet();
        int totalLenght = 0;
        Map<Character, Double> map = new HashMap<>();
        for (String s : words.keySet()) {
            int lenght = s.length();
            map = a.processCharacters(s, lenght, map);                              // Contar el nombre d'aparicions de totes les lletres de l'Alfabet
            totalLenght += lenght;                                                  // Saber quina és la mida total
        }
        a.setCharacters(map);                                                       // Inicialitzar characters, abecedari i size

        double[][] matrix = new double[map.size()][map.size()];                     // Matrix NxN inicialitzada amb 0.0
        a.setFrequencies(matrix);
        for (String s : words.keySet()) {
            int lenght = s.length();
            a.processFrequencies(s, lenght);                                        // Contar el nombre d'aparicions que donada una lletre es doni la següent
        }

        a.calculateFrequencies();                                                   // Passar el nombre d'aparicions a probabilitat per 1
        a.calculateCharacters(totalLenght);
        return a;
    }

    /**
     * Pre: 
     * Post: es retorna les paraules amb la seva probabilitats
     * 
     * @param path string que indica on està l'entrada que defineix l'Alfabet
     * 
     * @return un map amb les paraules i les seves freqüències
     * 
     * @throws FormatDadesNoValid si l'entrada no té el format esperat
     */
    private HashMap<String, Double> getWords (String path) throws FormatDadesNoValid, FileNotFoundException {
        HashMap<String, Double> words = new HashMap<>();
        File file = new File(path);
        try (Scanner myReader = new Scanner(file, "utf-8")) {
            while (myReader.hasNextLine()) {
                String word = myReader.next();
                String probabilitatString = myReader.next();
                double probabilitat = 0.0;
                try {
                    probabilitat = Float.parseFloat(probabilitatString);
                }
                catch(NumberFormatException e) {                            // String no es un Float
                    throw new FormatDadesNoValid();
                }
                if (! words.containsKey(word)) {                            // Comprovar que la lletra no s'ha vist encara
                    words.put(word, probabilitat);;                         // Crear una nova entrada amb la paraula i la seva probabilitat
                }
            }
            myReader.close();
        }
        return words;
    }
    
}

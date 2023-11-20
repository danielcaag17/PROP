package src.domain.classes;

import java.io.*;
import java.util.*;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public class Words implements StrategyAlfabet {

    @Override
    public Alfabet read(String path) throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        HashMap<String, Double> words = getWords(path);
        Alfabet a = new Alfabet();
        int totalLenght = 0;
        Map<Character, Double> map = new HashMap<>();
        for (String s : words.keySet()) {
            int lenght = s.length();
            map = a.processCharacters(s, lenght, map);
            totalLenght += lenght;
        }
        a.setSize(map.size());
        a.setCharacters(map);

        double[][] matrix = new double[map.size()][map.size()];
        for (String s : words.keySet()) {
            int lenght = s.length();
            matrix = a.processFrequencies(s, lenght, matrix);
        }
        matrix = a.calculateFrequencies(matrix);
        map = a.calculateCharacters(totalLenght, map);

        a.setSize(map.size());
        a.setCharacters(map);
        a.setFrequencies(matrix);
        return a;
    }

    
    private HashMap<String, Double> getWords (String path) throws FormatDadesNoValid, FileNotFoundException {
        HashMap<String, Double> words = new HashMap<>();
        File file = new File(path); // Tractar excepció que pugui donar File() !!!
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) { // Tractar excepció que pugui donar hasNextLine !!!
                String word = myReader.next(); // Tractar excepció que pugui donar nextLine !!!
                String probabilitatString = myReader.next();
                double probabilitat = 0.0;
                try {
                    probabilitat = Float.parseFloat(probabilitatString); // Tractar excepció que pugui donar parseFloat !!!
                }
                catch(NumberFormatException e) {    // String no es un Float
                    throw new FormatDadesNoValid();
                }
                if (! words.containsKey(word)) {                       // comprovar que la lletra no s'ha vist encara
                    words.put(word, probabilitat);;                  // crear una nova entrada amb la lletra c i 0 aparicions
                }
            }
            myReader.close();
        }
        return words;
    }
    
}

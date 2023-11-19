package src.domain.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import src.exceptions.FormatDadesNoValid;

public class Words implements StrategyAlfabet {

    @Override
    public Alfabet read(String path) {
        HashMap<String, Double> words = getWords(path);
        int totalLenght = 0;
        for (String s : words.keySet()) {
            int lenght = s.length();
            processCharacters(s, lenght);
            totalLenght += lenght;
        }
        calculateProbabilities(totalLenght);
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

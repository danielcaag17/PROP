package src.domain.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import src.exceptions.FormatDadesNoValid;

public class Text implements StrategyAlfabet {

    @Override
    public Alfabet read(String path) {
        String text = getText(path);
        int lenght = text.length();
        processCharacters(text, lenght);
        calculateProbabilities(lenght);
    }

    private String getText (String path) throws FormatDadesNoValid, FileNotFoundException {
        String text = "";
        File file = new File(path);
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNext()) {
                String input = myReader.next();
                try {
                    Float.parseFloat(input); // Tractar excepció que pugui donar parseFloat !!!
                    throw new FormatDadesNoValid();
                }
                catch(NumberFormatException e) {    // String no es un Float
                    // do nothing, significa que el format és correcte
                    // fer un contador perque no sempre entri a fer la conversió
                    // si han passat 20 strings i no hi ha error deixar de convertir a Float
                }
                text += input;
            }
            myReader.close();
        }
        return text;
    }
    
}

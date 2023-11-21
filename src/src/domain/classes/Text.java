package src.domain.classes;

import java.io.*;
import java.util.*;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public class Text implements StrategyAlfabet {

    /**
     * Pre:
     * Post: es retorna l'Alfabet caculat amb l'entrada doanada
     * 
     * @param path string que indica on està l'entrada que defineix l'Alfabet
     * 
     * @return l'Alfabet resultat de l'entrada donada
     */
    @Override
    public Alfabet read (String path) throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        String text = getText(path);
        int lenght = text.length();

        Alfabet a = new Alfabet();
        Map<Character, Double> map = new HashMap<>();
        map = a.processCharacters(text, lenght, map);

        a.setSize(map.size());
        a.setCharacters(map);

        double[][] matrix = new double[map.size()][map.size()];                 // Matrix NxN inicialitzada amb 0.0

        matrix = a.processFrequencies(text, lenght, matrix);
        matrix = a.calculateFrequencies(matrix);
        map = a.calculateCharacters(lenght, map);

        a.setCharacters(map);
        a.setFrequencies(matrix);
        return a;
    }

    /**
     * Pre: 
     * Post: es retorna el text llegit del fitxer
     * 
     * @param path String que indica l'entrada per calcular l'Alfabet
     * 
     * @return el text indicat pel path
     * 
     * @throws FormatDadesNoValid si l'entrada no té el format esperat
     */
    private String getText (String path) throws FormatDadesNoValid, FileNotFoundException {
        String text = "";
        File file = new File(path);
        int criteriAcceptació = 0;                              // Variable que indica fins a quin punt es comproba que el format és vàlid
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNext()) {
                String input = myReader.next();
                if (criteriAcceptació < 100) {
                    try {
                        Float.parseFloat(input);
                        throw new FormatDadesNoValid();
                    }
                    catch(NumberFormatException e) {            // String input no es un Float
                        // No fer res, significa que el format és correcte
                        // fer un contador perque no sempre entri a fer la conversió
                        // si han passat 20 strings i no hi ha error deixar de convertir a Float
                    }
                    criteriAcceptació++;
                }
                text += input.toLowerCase();                    // Passar a lower case tota l'entrada
            }
            myReader.close();
        }
        return text;
    }
    
}

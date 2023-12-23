package src.domain.classes;

import java.io.*;
import java.util.*;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public class Text extends Alfabet {

    // Pre:
    // Post: s'ha creat una instància de Text amb size = 0
    public Text () {super();}


    // Pre: no existeix cap Alfabet amb aquest nom
    // Post: s'ha creat una instància de Text amb nom = nom i size = 0
    public Text (String nom) {
        super(nom);
    }

    public Text(String nom, Map<Character, Double> characters, double[][] frequencies) {
        setNom(nom);
        setCharacters(characters);
        setFrequencies(frequencies);
    }

    /**
     * Pre:
     * Post: es retorna l'Alfabet caculat amb l'entrada doanada
     * 
     * @param path string que indica on està l'entrada que defineix l'Alfabet
     * 
     * @return l'Alfabet resultat de l'entrada donada
     */
    @Override
    public void read (String path) throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        String text = getText(path);                                            // Llegir l'entrada
        int lenght = text.length();

        characters = new HashMap<>();
        characters = processCharacters(text, lenght, characters);                           // Contar el nombre d'aparicions de totes les lletres de l'Alfabet
        setCharacters(characters);                                                   // Inicialitzar characters, abecedari i size
        
        frequencies = new double[characters.size()][characters.size()];                 // Matrix NxN inicialitzada amb 0.0
        setFrequencies(frequencies);
        processFrequencies(text, lenght);                                     // Contar el nombre d'aparicions que donada una lletre es doni la següent

        calculateFrequencies();                                               // Passar el nombre d'aparicions a probabilitat per 1
        calculateCharacters(lenght);
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
        int criteriAcceptacio = 0;                              // Variable que indica fins a quin punt es comproba que el format és vàlid
        try (Scanner myReader = new Scanner(file, "utf-8")) {
            if (!myReader.hasNextLine()) throw new FormatDadesNoValid(path, "està buit."); // L'entrada és buida
            while (myReader.hasNext()) {
                String input = myReader.next();
                if (criteriAcceptacio < 100) {
                    try {
                        Float.parseFloat(input);
                        throw new FormatDadesNoValid();
                    }
                    catch(NumberFormatException e) {            // String input no es un Float
                        // No fer res, significa que el format és correcte
                        // fer un contador perque no sempre entri a fer la conversió
                        // si han passat 20 strings i no hi ha error deixar de convertir a Float
                    }
                    criteriAcceptacio++;
                }
                text += input.toLowerCase();                    // Passar a lower case tota l'entrada
            }
            myReader.close();
        }
        return text;
    }
    
}

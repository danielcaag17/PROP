package src.domain.classes;

import java.io.*;
import java.util.*;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public class Words extends Alfabet {

    // Pre:
    // Post: s'ha creat una instància de Text amb size = 0
    public Words() {super();}

    // Pre: no existeix cap Alfabet amb aquest nom
    // Post: s'ha creat una instància de Words amb nom = nom i size = 0
    public Words (String nom) {
        super(nom);
    }

    // Pre: no existeix cap Alfabet amb aquest nom
    // Post: s'ha creat una instància de Text amb nom = nom, characters = characters, frequencies = frequencies i size = characters.size
    public Words(String nom, Map<Character, Double> characters, double[][] frequencies) {
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
    public void read(String path) throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        HashMap<String, Double> words = getWords(path);                             // Llegir l'entrada
        int totalLenght = 0;
        characters = new HashMap<>();
        for (String s : words.keySet()) {
            int lenght = s.length();
            characters = processCharacters(s, lenght, characters);                              // Contar el nombre d'aparicions de totes les lletres de l'Alfabet
            totalLenght += lenght;                                                  // Saber quina és la mida total
        }
        setCharacters(characters);                                                       // Inicialitzar characters, abecedari i size

        frequencies = new double[characters.size()][characters.size()];                     // Matrix NxN inicialitzada amb 0.0
        setFrequencies(frequencies);
        for (String s : words.keySet()) {
            int lenght = s.length();
            processFrequencies(s, lenght);                                        // Contar el nombre d'aparicions que donada una lletre es doni la següent
        }

        calculateFrequencies();                                                   // Passar el nombre d'aparicions a probabilitat per 1
        calculateCharacters(totalLenght);
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
            if (!myReader.hasNextLine()) throw new FormatDadesNoValid(path, "està buit."); // L'entrada és buida
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

package src.domain.classes;
import src.exceptions.*;

import java.io.*;
import java.util.*;

public class Alfabet {
private String nom;                             // clau primària
    private Map<Character, Double> characters;   // cada caràcter de l'alfabet amb la seva corresponent probabilitat
    private double[][] frequencies;                        // a veure quina Estructura de Dades utilitzar + quin nom
    int size;

    public Alfabet (String nom) {
        this.nom = nom;
        size = 0;
    }


    public void readInput (String ta, String path) throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException {
        if (ta == "text") readText(path);
        else if (ta == "llista-paraules") readWords(path);
        else {  // String ta no conicideix amb cap de les opcions
            throw new TipusDadesNoValid();
        }
    }

    // Gestionar un text d'entrada
    private void readText (String path) throws FormatDadesNoValid, FileNotFoundException {
        String text = getText(path);
        int lenght = text.length();
        processCharacters(text, lenght);
        calculateProbabilities(lenght);
    }

     // Gestionar llistes de paraules
    private void readWords(String path) throws FormatDadesNoValid, FileNotFoundException { //conjunt de paraules amb la seva probabilitat
        HashMap<String, Double> words = getWords(path);
        int totalLenght = 0;
        for (String s : words.keySet()) {
            int lenght = s.length();
            processCharacters(s, lenght);
            totalLenght += lenght;
        }
        calculateProbabilities(totalLenght);
    }

    private void processCharacters(String text, int length) { //return lenght (?) per calculateProbabilities
        setCharacters(text, length);
        setX(text, length);
    }

    private void setCharacters (String text, int length) {
        for (int i = 0; i < length; i++) {
            Character c = text.charAt(i);
            if (c != ' ') {
                if (! characters.containsKey(c)) {                         // comprovar que la lletra no s'ha vist encara
                    characters.put(c, 0.0);;                  // crear una nova entrada amb la lletra c i 0 aparicions
                }
                double a = characters.get(c);   // obtenir el nombre d'aparicions de la lletra
                characters.replace(c, a+1);    // sumar un a les aparicions de la lletra c
            }
        }
        frequencies = new double[characters.size()][characters.size()];
        setSize(characters.size());
    }

    private void setX (String text, int length) {
        for (int i = 0; i < length - 1; i++) {
                if (text.charAt(i) != ' ') {                       // veure la lletra seguent
                char next = Character.toLowerCase(text.charAt(i+1));
                int j = Character.toLowerCase(text.charAt(i)) - 'a';
                int k = next - 'a';
                frequencies[j][k]++;
            }
        }
    }

    private void calculateProbabilities (int length) {     //ull amb totes les divisions, EXC si es entre 0 !!
        for (Character c : characters.keySet()) {
            double a = characters.get(c);   // obtenir el nombre d'aparicions de la lletra
            double probabilitat = a / length;            // obtenir la probabilitat
            double nAparicions = characters.get(c);
            characters.replace(c, probabilitat);      // associar la lletra amb la seva probabilitat

            for (int i = 0; i < size; i++) { //size ha de ser de la  mateixa mida que la matriu
                frequencies[c][i] /= nAparicions;     //dividir la matrix duna fila pel valor que conte el vector que conta el nombre
                                            //de vegades que apareix la lletra 
                                            // Ull divisions entre 0 --> tractar EXC
            }
        }
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

    private HashMap<String, Double> getWords (String path) throws FormatDadesNoValid, FileNotFoundException {
        HashMap<String, Double> words = new HashMap<>();
        File file = new File(path); // Tractar excepció que pugui donar File() !!!
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) { // Tractar excepció que pugui donar hasNextLine !!!
                String word = myReader.next(); // Tractar excepció que pugui donar nextLine !!!
                double probabilitat = 0.0;
                try {
                    probabilitat = myReader.nextDouble(); // Tractar excepció que pugui donar parseFloat !!!
                }
                catch(InputMismatchException e) {    // String no es un Float
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

    private void setSize (int size) {
        this.size = size;
    }

    public int getSize () {
        return size;
    }

    public String getNom () {
        return nom;
    }

    public void setNom (String nom) {
        this.nom = nom;
    }

    // Retorna les lletres de l'alfabet
    public char[] getAbecedari () {
        char[] abecedari = new char[this.size];
        int i = 0;
        for (Character c : characters.keySet()) {
            abecedari[i] = c;
            i++;
        }
        return abecedari;
    }

    public double[][] getFrequencies () {
        return frequencies;
    }

    public Map<Character, Double> getCharacter () {
        return characters;
    }
}

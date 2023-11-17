package src.domain.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;       // mirar quin es import que sha de fer servir
import java.util.Scanner;
import java.util.Set;

import src.exceptions.AlfabetJaExisteix;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TeclatNoExisteix;
import src.exceptions.TipusDadesNoValid;

public class Alfabet {
private String nom;                             // clau primària
    private Map<Character, Float> characters;   // cada caràcter de l'alfabet amb la seva corresponent probabilitat
    private float[][] x;                        // a veure quina Estructura de Dades utilitzar + quin nom
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
        HashMap<String, Float> words = getWords(path);
        int totalLenght = 0;
        for (String s : words.keySet()) {
            int lenght = s.length();
            processCharacters(s, lenght);
            totalLenght += lenght;
        }
        calculateProbabilities(totalLenght);
    }

    private void processCharacters(String text, int length) { //return lenght (?) per calculateProbabilities
        for (int i = 0; i < length; i++) {
            Character c = text.charAt(i);                    // obtenir la lletra del text
            if (! included(c)) {                         // comprovar que la lletra no s'ha vist encara
                size++;
                characters.put(c, 0.f);;                  // crear una nova entrada amb la lletra c i 0 aparicions
            }
            float a = characters.get(text.charAt(i));   // obtenir el nombre d'aparicions de la lletra
            characters.replace(text.charAt(i), a+1);    // sumar un a les aparicions de la lletra c
            if (i < length - 1) {                       // veure la lletra seguent
                char next = text.charAt(i+1);
                int j = c - 'a';
                int k = next - 'a';
                x[j][k]++;
            }
        }
        setSize(characters.size());
    }

    private void calculateProbabilities (int length) {     //ull amb totes les divisions, EXC si es entre 0 !!
        for (Character c : characters.keySet()) {
            float a = characters.get(c);   // obtenir el nombre d'aparicions de la lletra
            float probabilitat = a / length;            // obtenir la probabilitat
            float nAparicions = characters.get(c);
            characters.replace(c, probabilitat);      // associar la lletra amb la seva probabilitat

            for (int i = 0; i < size; i++) { //size ha de ser de la  mateixa mida que la matriu
                x[c][i] /= nAparicions;     //dividir la matrix duna fila pel valor que conte el vector que conta el nombre
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
                }
                catch(NumberFormatException e) {    // String no es un Float
                    throw new FormatDadesNoValid();
                }
                text += input;
            }
            myReader.close();
        }
        return text;
    }

    private HashMap<String, Float> getWords (String path) throws FormatDadesNoValid, FileNotFoundException {
        HashMap<String, Float> words = new HashMap<>();
        File file = new File(path); // Tractar excepció que pugui donar File() !!!
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) { // Tractar excepció que pugui donar hasNextLine !!!
                String word = myReader.next(); // Tractar excepció que pugui donar nextLine !!!
                Float probabilitat = 0.0f;
                try {
                    probabilitat = myReader.nextFloat(); // Tractar excepció que pugui donar parseFloat !!!
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

    // aquesta lletra existeix en el vector
    private boolean included (char c) {
        return characters.containsKey(c);               // comprovar si el map te la clau c
    }

    public int getSize () {
        return size;
    }

    public String getNom () {
        return nom;
    }

    // Retorna les lletres de l'alfabet
    public String getAbecedari () {
        Set<Character> keys = characters.keySet();
        return keys.toString();
    }
}

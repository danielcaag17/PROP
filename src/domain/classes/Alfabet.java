package src.domain.classes;
import src.exceptions.*;

import java.io.*;
import java.util.*;

public class Alfabet {
    private String nom;                             // clau primària
    private Map<Character, Double> characters;   // cada caràcter de l'alfabet amb la seva corresponent probabilitat
    private double[][] frequencies;                        // a veure quina Estructura de Dades utilitzar + quin nom
    int size;
    private StrategyAlfabet strategy;

    public Alfabet () {
        setSize(0);
    }

    public Alfabet (String nom) {
        setNom(nom);
        setSize(0);
        characters = new HashMap<>();
    }


    public void readInput (String ta, String path) throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException {
        setStrategy(ta);
        Alfabet a = strategy.read(path);
        this.characters = a.characters;
        this.frequencies = a.frequencies;
        setSize(a.size);
    }

    public Map<Character, Double> processCharacters (String text, int length) {
        Map<Character, Double> chars = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Character c = text.charAt(i);
            if (c != ' ') {
                if (! chars.containsKey(c)) {                         // comprovar que la lletra no s'ha vist encara
                    chars.put(c, 0.0);;                  // crear una nova entrada amb la lletra c i 0 aparicions
                }
                double a = chars.get(c);   // obtenir el nombre d'aparicions de la lletra
                chars.replace(c, a+1);    // sumar un a les aparicions de la lletra c
            }
        }
        return chars;
    }

    public double[][] processFrequencies (String text, int length, int size) {
        double[][] freqs = new double[size][size];
        for (int i = 0; i < length - 1; i++) {
            if (text.charAt(i) != ' ') {                       // veure la lletra seguent
                char next = Character.toLowerCase(text.charAt(i+1));
                int j = Character.toLowerCase(text.charAt(i)) - 'a';
                int k = next - 'a';
                freqs[j][k]++;
            }
        }
        return freqs;
    }

    public double[][] calculateFrecuencies (Map<Character, Double> map, double[][] matrix) {     //ull amb totes les divisions, EXC si es entre 0 !!
        for (Character c : map.keySet()) {
            double nAparicions = map.get(c);         // obtenir el nombre d'aparicions de la lletra
            for (int i = 0; i < map.size(); i++) { //size ha de ser de la  mateixa mida que la matriu
                int j = c - 'a';
                matrix[j][i] /= nAparicions;     //dividir la matrix duna fila pel valor que conte el vector que conta el nombre
                                            //de vegades que apareix la lletra 
                                            // Ull divisions entre 0 --> tractar EXC
            }
        }
        return matrix;
    }

    public Map<Character, Double> calculateCharacters (int length, Map<Character, Double> map) {     //ull amb totes les divisions, EXC si es entre 0 !!
        for (Character c : map.keySet()) {
            double nAparicions = map.get(c);
            double probabilitat = nAparicions / length;            // obtenir la probabilitat
            map.replace(c, probabilitat);      // associar la lletra amb la seva probabilitat
        }
        return map;
    }

    private void setStrategy (String strategy) throws TipusDadesNoValid {
        switch (strategy) {
            case "text":
                this.strategy = (StrategyAlfabet) new Text();
                break;
            case "llista-paraules":
                this.strategy = (StrategyAlfabet) new Words();
                break;
            default:
                throw new TipusDadesNoValid();
        }
    }

    public void setCharacters (Map<Character, Double> map) {
        this.characters = map;
    }

    public void setFrequencies (double[][] matrix) {
        this.frequencies = matrix;
    }

    public void setSize (int size) {
        this.size = size;
    }

    public void setNom (String nom) {
        this.nom = nom;
    }

    public int getSize () {
        return size;
    }

    public String getNom () {
        return nom;
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

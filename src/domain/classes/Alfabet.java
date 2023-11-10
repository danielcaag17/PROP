package src.domain.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;       // mirar quin es import que sha de fer servir
import java.util.Scanner;
import java.util.Map;       // mirar quin es import que sha de fer servir
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Alfabet {
    private String nom;                                             // clau primària
    private Map<Character, Float> characters;   // cada caràcter de l'alfabet amb la seva corresponent probabilitat
    private float[][] x;                    // a veure quina Estructura de Dades utilitzar + quin nom
    int size;       // potser estaria be saber quants caracters diferents té labecedari per aixi poder inicialitzar les dades amb l'espai que necessiten

    //vector[]<char> = new vector[mida alfabet]
    //vector[]<float> = new vector[mida alfabet]
    //matrix[][]<float> = new matrix[mida alfabet] ----> probabilitat dondada una lletra sigui la seguent

    public Alfabet (String nom) {
        this.nom = nom;
    }

    // Gestionar un text d'entrada
    public void readText (String path) {
        String text = getText(path);
        int lenght = text.length();
        processCharacters(text, lenght);
        calculateProbabilities(lenght);
    }

     // Gestionar llistes de paraules
    public void readWords(String path) { //conjunt de paraules amb la seva probabilitat
        Iterator it = words.entrySet().iterator();      //veure si aixo esta ben fet
        while (it.hasNext()) {
            processCharacters(it);
            it.next();
        }
        calculateProbabilities();       // falta veure de quina manera es calculen les probabilitats de la propia paraula
    }

    private void processCharacters(String text, int length) { //return lenght (?) per calculateProbabilities
        for (int i = 0; i < length; i++) {
            Character c = text.charAt(i);                    // obtenir la lletra del text
            if (! included(c))                          // comprovar que la lletra no s'ha vist encara
                characters.put(c, 0.f);;                  // crear una nova entrada amb la lletra c i 0 aparicions
            float a = characters.get(text.charAt(i));   // obtenir el nombre d'aparicions de la lletra
            characters.replace(text.charAt(i), a+1);    // sumar un a les aparicions de la lletra c
            if (i < length - 1) {                       // veure la lletra seguent
                char next = text.charAt(i+1);
                int j = c - 'a';
                int k = next - 'a';
                x[j][k]++;
            }
        }
    }

    private void calculateProbabilities (int length) {     //ull amb totes les divisions, EXC si es entre 0 !!
        for (Character c : characters.keySet()) {
            float a = characters.get(c);   // obtenir el nombre d'aparicions de la lletra
            float probabilitat = a / length;            // obtenir la probabilitat
            float nAparicions = characters.get(c);
            characters.replace(c, a);      // associar la lletra amb la seva probabilitat

            for (int i = 0; i < size; i++) {
                x[c][i] /= nAparicions;     //dividir la matrix duna fila pel valor que conte el vector que conta el nombre
                                            //de vegades que apareix la lletra 
            }
        }
    }

    private String getText (String path) {
        String text = "";
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
            text += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // No passarà mai perque el path ja es comprova a ctrlDomini
            System.err.println("PathIncorrecte");
            e.printStackTrace();
        }
        return text;
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
}

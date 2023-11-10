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
        processCharacters(text);
        calculateProbabilities();
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

    private void processCharacters(String text) { //return lenght (?) per calculateProbabilities
        int length = text.length();
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

    private void calculateProbabilities () {     //ull amb totes les divisions, EXC si es entre 0 !!
        Iterator it = characters.entrySet().iterator();
        while (it.hasNext()) {
            float a = characters.get(text.charAt(i));   // obtenir el nombre d'aparicions de la lletra
            float probabilitat = a / length;            // obtenir la probabilitat
            characters.replace(text.charAt(i), a);      // associar la lletra amb la seva probabilitat

            //for (i = 0; i < mida de alfabet; i++) {
                //x[it.key][i] /= a;

                //dividir la matrix duna fila pel valor que conte el vector que conta el nombre
                //de vegades que apareix la lletra 
            //}
            it.next();
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

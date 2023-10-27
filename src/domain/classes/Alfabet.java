package src.domain.classes;

import java.util.Map;       // mirar quin es import que sha de fer servir

public class Alfabet {
    private String nom;                                             // clau primària
    private Map<char, float> characters = new Map<char, float>();   // cada caràcter de l'alfabet amb la seva corresponent probabilitat
    private float[][] x = new float[][];                    // a veure quina Estructura de Dades utilitzar + quin nom
    int midaAlfabet;       // potser estaria be saber quants caracters diferents té labecedari per aixi poder inicialitzar les dades amb l'espai que necessiten

    //vector[]<char> = new vector[mida alfabet]
    //vector[]<float> = new vector[mida alfabet]
    //matrix[][]<float> = new matrix[mida alfabet] ----> probabilitat dondada una lletra sigui la seguent


    // Gestionar alfabet
    public void readAlfabet() { //a definir quin tipus de dades es vol rebre com a parametre

    }

    // Gestionar un text d'entrada
    public void readText (String text) {
        processCharacters(text);
        calculateProbabilities();
    }

     // Gestionar llistes de paraules
    public void readWords(Set<string, float> words) { //conjunt de paraules amb la seva probabilitat
        Iterator it = words.entrySet().iterator();      //veure si aixo esta ben fet
        while (it.hasNext()) {
            processCharacters(it);
            it.next();
        }
        calculateProbabilities();       // falta veure de quina manera es calculen les probabilitats de la propia paraula
    }

    private void processCharacters(String text) {
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);                    // obtenir la lletra del text
            if (! included(c))                          // comprovar que la lletra no s'ha vist encara
                characters.put(c, 0);                   // crear una nova entrada amb la lletra c i 0 aparicions
            float a = characters.get(text.charAt(i));   // obtenir el nombre d'aparicions de la lletra
            characters.replace(text.charAt(i), a+1);    // sumar un a les aparicions de la lletra c
            if (i < length - 1) {                       // veure la lletra seguent
                char next = text.charAt(i+1);
                x[c][next]++;                      // molt molt pseudocodi
            }
        }
    }

    private void calculateProbabilities() {
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

    // aquesta lletra existeix en el vector
    private boolean included(char c) {
        return characters.containsKey(c);               // comprovar si el map te la clau c
    }
}

package src.domain.classes;

public class Alfabet {
    private String nom;
    //vector[]<char> = new vector[mida alfabet]
    //vector[]<float> = new vector[mida alfabet]
    //matrix[][]<float> = new matrix[mida alfabet] ----> probabilitat dondada una lletra sigui la seguent


    // Gestionar alfabet
    public void readAlfabet() { //a definir quin tipus de dades es vol rebre com a parametre

    }

    // Gestionar text d'entrada
    public void readText(String text) {
        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (!included(text.charAt(i))) {
                //vector de lletras push
            }
            //sumar una aparicio de la lletra
            if (i < length - 1) {
                //veure la lletra seguent i sumar a la aprobabilitat
            }
        }

        /*for (i in vector length)
         * dividir la matrix duna fila pel valor que conte el vector que conta el nombre
         * de vegades que apareix la lletra
         * 
         * dividr tot el vector de les aparicions de la lletra per length
         */
    }

    // Gestionar llistes de paraules
    public void readWords() { //conjunt de paraules amb la seva probabilitat

    }

    // aquesta lletra existeix en el vector
    private boolean included(char c) {
        return true;
    }
}

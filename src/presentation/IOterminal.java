package src.presentation;

import java.io.*;
import java.util.*;

public class IOterminal {
    private CtrlPresentacio ctrlPresentacio;
    private Scanner s;

    public IOterminal(CtrlPresentacio cp) {
        ctrlPresentacio = cp;
        // s = new Scanner(System.in);
    }

    public void inicialitza() {
        // Codi inicialització terminal (welcome msg)
    }

    /* 
     * IMPORTANT PENSAR COMANDES D'INPUT
     * - tipus: nou_alfabet nou_teclat borrar_teclat etc.
     * 
     * Interacció pensada per a la creació de nou alfabet
     * - Preguntar pel nom que es vol donar al alfabet
     * - Preguntar pel tipus de dades que s'introdueixen (llista de freqüències o text)
     * - Demanar el path al fitxer on estan guardades
     * - L'IOterminal comprova que el fitxer existeix i si no torna a demanar el path.
     */
}

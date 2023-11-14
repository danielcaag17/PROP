package src.presentation;

import java.io.*;
import java.util.*;

import src.exceptions.*;

public class IOterminal {
    private CtrlPresentacio ctrlPresentacio;
    private Scanner s;

    public IOterminal(CtrlPresentacio cp) {
        ctrlPresentacio = cp;
        s = new Scanner(System.in);
    }

    public void inicialitzaTerminal() {
        // Codi inicialització terminal (welcome msg)
        System.out.print(
            "\n" + //
            "       _  _  _  _                                 \n" + //
            "      |_)|_)/ \\|_)                               \n" + //
            "---   |  | \\\\_/|    ---                         \n" + //
            "                                                  \n" + //
            " _           _                                    \n" + //
            "|_) _       |_) _ __ |_  |  _                     \n" + //
            "|  (_||_|   | \\(_|||||_) | (_|                   \n" + //
            " _                    __                          \n" + //
            "| \\ _ __  o  _  |    /   _ __  o  _  _  __ _  _  \n" + //
            "|_/(_|| | | (/_ |    \\__(_|| | |  /_(_| | (/__>  \n" + //
            "                   _                              \n" + //
            "  | _  __ _| o    / \\_|_ _  |                    \n" + //
            "\\_|(_) | (_| |    \\_/ |_(_| |                   \n" + //
            "\n\n"
        );
        System.out.println("TREBALL DE PROP: GENERADOR DE TECLATS");
        System.out.println("Daniel  Cañizares  Aguilar");
        System.out.println("Jordi   Otal       Salvans");
        System.out.println("Pau     Rambla     Albet");

        mostraMenu();
        escollir();
    }

    public void mostraMenu() {
        System.out.print(
            "\n" +
            "> Les comandes necessàries per a fer ús del programa són les següent: \n" +
            "Format --> comanda : abreviatura \n" +
            "   nou_teclat : nt \n" +
            "   modifica_teclat : modt \n" +
            "   esborra_teclat : et \n" +
            "   mostra_teclat : mt \n" +
            "   nou_alfabet : na \n" +
            "   esborra_alfabet : ea \n" +
            "   mostra_alfabet : ma \n" +
            "   nou_layout : nl \n" +
            "   mostra_layout : ml \n" +
            "   esborra_layout : el \n" +
            "\n" +
            "   llista_teclats : lt \n" +
            "   llista_alfabets : la \n" +
            "   llista_layouts : ll \n" +
            "\n" +
            "   more_info : mi \n" +
            "   finalitzar : f \n" +
            ""
        );
    }

    public void escollir() {
        System.out.print("> ");
        String command = s.nextLine();
        if (     command.equals("nou_teclat")      || command.equals("nt")) nouTeclat();
        else if (command.equals("modifica_teclat") || command.equals("modt")) modificaTeclat();
        else if (command.equals("esborra_teclat")  || command.equals("et")) esborraTeclat();
        else if (command.equals("mostra_teclat")   || command.equals("mt")) mostraTeclat();
        else if (command.equals("nou_alfabet")     || command.equals("na")) nouAlfabet();
        else if (command.equals("esborra_alfabet") || command.equals("ea")) esborraAlfabet();
        else if (command.equals("mostra_alfabet")  || command.equals("ma")) mostraAlfabet();
        else if (command.equals("nou_layout")      || command.equals("nl")) nouLayout();
        else if (command.equals("mostra_layout")   || command.equals("ml")) mostraLayout();
        else if (command.equals("esborra_layout")  || command.equals("el")) esborraLayout();
        else if (command.equals("llista_teclats")  || command.equals("lt")) llistaTeclats();
        else if (command.equals("llista_alfabets") || command.equals("la")) llistaAlfabets();
        else if (command.equals("llista_layouts")  || command.equals("ll")) llistaLayouts();
        else if (command.equals("more_info")       || command.equals("mi")) moreInfo();
        else if (command.equals("finalitzar")      || command.equals("f")) finalitzar();
        else {
            System.out.println("(!)> ERROR: '" + command + "' no és una comanda vàlida."); 
            System.out.println("> Si us plau, introdueix una comanda vàlida.");
            escollir();
        }
    }

    private void writeList(String[] list) {
        int i = 1;
        for(String element : list) {
            System.out.println(i + ". " + element);
            i++;
        }
    }

    public void llistaLayouts() {
        System.out.println("> Llista de Layouts:");
        writeList(ctrlPresentacio.getListLayouts());
        escollir();
    }

    public void llistaAlfabets() {
        System.out.println("> Llista d'Alfabets:");
        writeList(ctrlPresentacio.getListAlfabets());
        escollir();
    }

    public void llistaTeclats() {
        System.out.println("> Llista de Teclats:");
        writeList(ctrlPresentacio.getListTeclats());
        escollir();
    }

    private Integer answerInteger() {
        System.out.print("> ");
        return s.nextInt();
    }

    private Character answerCharacter() {
        System.out.print("> ");
        String input = s.next();
        if (input.length() == 1) {
            return input.charAt(0);
        }
        else {
            System.out.println("(!)> Aquest valor no és un sol caràcter. Introdueix un caràcter:");
            return answerCharacter();
        }
    }

    private String answerString() {
        System.out.print("> ");
        return s.nextLine();
    }

    public void esborraLayout() {
        System.out.println("> Quin layout, dels no creats inicialment, vols que s'esborri? Indica'n la mida:");
        Integer idL = answerInteger();
        try {
            ctrlPresentacio.esborrarLayout(idL);
            System.out.println("> Layout de mida "+ idL +" esborrat");
        } 
        catch(LayoutNoExisteix e) {
            System.out.println("(!)> ERROR: Aquest Layout no existeix.");
        }
        catch(LayoutNoBorrable e) {
            System.out.println("(!)> ERROR: Aquest Layout no es pot esborrar.");
        }
        escollir();
    }

    public void mostraLayout() {
        System.out.println("> Quin Layout vols que es mostri? Indica'n la mida:");
        Integer idL = answerInteger();
        try {
            String out = ctrlPresentacio.visualitzarLayout(idL);
            System.out.println("> Layout:");
            System.out.println(out);
        } 
        catch(LayoutNoExisteix e) {
            System.out.println("(!)> ERROR: Aquest Layout no existeix.");
        }
        escollir();
    }

    public void nouLayout() {
        System.out.println("> Per a afegir un nou layout indica'n la mida:");
        Integer idL = answerInteger();
        try {
            ctrlPresentacio.afegirLayout(idL);
            System.out.println("> Layout de mida "+ idL +" creat");
        } 
        catch(LayoutJaExisteix e) {
            System.out.println("(!)> ERROR: Aquest Layout ja existeix. Prova amb una mida diferent.");
        }
        escollir();
    }

    public void mostraAlfabet() {
        System.out.println("> Quin alfabet vols mostrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            String out = ctrlPresentacio.visualitzarAlfabet(nom);
            System.out.println("> Alfabet:");
            System.out.println(out);
        }
        catch(AlfabetNoExisteix e) {
            System.out.println("(!)> ERROR: Aquest Alfabet no existeix.");
        }
        escollir();
    }

    public void esborraAlfabet() {
        System.out.println("> Quin alfabet vols esborrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            ctrlPresentacio.esborrarAlfabet(nom);
            System.out.println("> Alfabet anomenat "+nom+" esborrat.");
        }
        catch(AlfabetNoExisteix e) {
            System.out.println("(!)> ERROR: Aquest Alfabet no existeix.");
        }
        escollir();
    }

    public void nouAlfabet() {
        /*
        FileReader fr = null;
        try {
            fr = new FileReader(path);       
        }
        catch(FileNotFoundException e1){
            System.out.println("El fitxer no existeix.");
        }
        */
        System.out.println("> Per a afegir un nou alfabet indica'n el seu nom:");
        String nom = answerString();
        System.out.println("> Indica el tipus de dades que s'entraran per a crear l'alfabet, {'text', 'llista-paraules'}:");
        String tipus = answerString();
        System.out.println("> Finalment, indica el path al fitxer on es troben aquestes dades (veure 'more_info' per a exemples):");
        String path = answerString();
        try {
            ctrlPresentacio.afegirAlfabet(nom, tipus, path);
            System.out.println("> Alfabet creat amb nom:"+nom+" i tipus:"+tipus);
        }
        catch(AlfabetJaExisteix e) {
            System.out.println("(!)> ERROR: Aquest Alfabet ja existeix. Prova amb un altre nom.");
        }
        // FALTARÀ INCORPORRAR ELS CATCH DE LES EXCEPCIONS QUE COMPROVEN EL TIPUS I EL PATH DINS D'alfabet.java
        escollir();
    }

    public void mostraTeclat() {
        System.out.println("> Quin teclat vols mostrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            String out = ctrlPresentacio.visualitzarTeclat(nom);
            System.out.println("> Teclat:");
            System.out.println(out);
        }
        catch(TeclatNoExisteix e) {
            System.out.println("(!)> ERROR: Aquest Teclat no existeix.");
        }
        escollir();
    }

    public void esborraTeclat() {
        // Pregunta nom del teclat a esborrar
        System.out.println("> Teclat esborrat");

        escollir();
    }

    public void modificaTeclat() {
        // Pregunta nom del teclat a modificar i canvis a fer.
        System.out.println("> Teclat modificat");

        escollir();
    }

    public void nouTeclat() {
        // Pregunta dades del teclat a crear {nom teclat, nom alfabet, id layout}
        System.out.println("> Teclat creat");

        escollir();
    }

    public void moreInfo() {
        /* 
         * Més informació sobre cada comanda.
         * 
         * - per a alfabet posar exemple de path per a diferents tipus de dades.
         */
        escollir();
    }

    public void finalitzar() {
        System.out.println("Que vagi bé! Et trobarem a faltar!");
        System.exit(0);
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

    /*
    nou_teclat
    modifica_teclat
    esborra_teclat
    mostra_teclat
    // llista teclats - per cada teclat (nom teclat, nom alfabet, mida layout)
    // llista alfabets - per cada alfabet (nom alfabet, abecedari)
    // llista layouts - per cada layout (mida layout, matriu distribucio?)
    nou_alfabet
    esborra_alfabet
    mostra_alfabet
    // nou_layout
    // esborra_layout
    mostra_layout
    */
}

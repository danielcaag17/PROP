package src.presentation;

import java.io.*;
import java.util.*;

import src.exceptions.*;

public class IOterminal {
    private CtrlPresentacio ctrlPresentacio;
    private Scanner s;

    /** Missatge previ al output del sistema */
    private String output = "> ";
    /** Missatge previ als input introduïts per l'usuari */
    private String inputFromUser = "? > "; 
    /** Missatge previ als inputs de comandes posades per l'usuari */
    private String inputCommand = "# ";
    /** Missatge previ al output d'errors */
    private String errorOutput = "[!] > ";

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

        mostraMenu(); // crida a escollir també
    }

    public void mostraMenu() {
        System.out.print(
            "\n" +
            output + "Les comandes necessàries per a fer ús del programa són les següent: \n" +
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
            "   print_commands : pc \n" +
            "   finalitzar : f \n" +
            ""
        );
        escollir();
    }

    public void escollir() {
        System.out.print(inputCommand);
        String command = s.next();
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
        else if (command.equals("print_commands")  || command.equals("pc")) mostraMenu();
        else if (command.equals("finalitzar")      || command.equals("f")) finalitzar();
        else {
            System.out.println(errorOutput + "ERROR: '" + command + "' no és una comanda vàlida."); 
            System.out.println(output + "Si us plau, introdueix una comanda vàlida.");
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
        System.out.println(output + "Llista de Layouts:");
        writeList(ctrlPresentacio.getListLayouts());
        escollir();
    }

    public void llistaAlfabets() {
        System.out.println(output + "Llista d'Alfabets:");
        writeList(ctrlPresentacio.getListAlfabets());
        escollir();
    }

    public void llistaTeclats() {
        System.out.println(output + "Llista de Teclats:");
        writeList(ctrlPresentacio.getListTeclats());
        escollir();
    }

    private Integer answerInteger() {
        System.out.print(inputFromUser);
        return s.nextInt();
    }

    private Character answerCharacter() {
        System.out.print(inputFromUser);
        String input = s.next();
        if (input.length() == 1) {
            return input.charAt(0);
        }
        else {
            System.out.println(errorOutput + "Aquest valor no és un sol caràcter. Introdueix un caràcter:");
            return answerCharacter();
        }
    }

    private String answerString() {
        System.out.print(inputFromUser);
        return s.next();
    }

    public void esborraLayout() {
        System.out.println(output + "Quin layout, dels no creats inicialment, vols que s'esborri? Indica'n la mida:");
        Integer idL = answerInteger();
        try {
            ctrlPresentacio.esborrarLayout(idL);
            System.out.println(output + "Layout de mida "+ idL +" esborrat");
        } 
        catch(LayoutNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Layout no existeix.");
        }
        catch(LayoutNoBorrable e) {
            System.out.println(errorOutput + "ERROR: Aquest Layout no es pot esborrar.");
        }
        escollir();
    }

    public void mostraLayout() {
        System.out.println(output + "Quin Layout vols que es mostri? Indica'n la mida:");
        Integer idL = answerInteger();
        try {
            String out = ctrlPresentacio.visualitzarLayout(idL);
            System.out.println(output + "Layout:");
            System.out.println(out);
        } 
        catch(LayoutNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Layout no existeix.");
        }
        escollir();
    }

    public void nouLayout() {
        System.out.println(output + "Per a afegir un nou layout indica'n la mida:");
        Integer idL = answerInteger();
        try {
            ctrlPresentacio.afegirLayout(idL);
            System.out.println(output + "Layout de mida "+ idL +" creat");
        } 
        catch(LayoutJaExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Layout ja existeix. Prova amb una mida diferent.");
        }
        escollir();
    }

    public void mostraAlfabet() {
        System.out.println(output + "Quin alfabet vols mostrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            String out = ctrlPresentacio.visualitzarAlfabet(nom);
            System.out.println(output + "Alfabet:");
            System.out.println(out);
        }
        catch(AlfabetNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Alfabet no existeix.");
        }
        escollir();
    }

    public void esborraAlfabet() {
        System.out.println(output + "Quin alfabet vols esborrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            ctrlPresentacio.esborrarAlfabet(nom);
            System.out.println(output + "Alfabet "+nom+" esborrat.");
        }
        catch(AlfabetNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Alfabet no existeix.");
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
        System.out.println(output + "Per a afegir un nou alfabet indica com vols anomenar-lo:");
        String nom = answerString();
        System.out.println(output + "Indica el tipus de dades que s'entraran per a crear l'alfabet, {'text', 'llista-paraules'}:");
        String tipus = answerString();
        System.out.println(output + "Finalment, indica el path al fitxer on es troben aquestes dades (veure 'more_info' per a exemples):");
        String path = answerString();
        try {
            ctrlPresentacio.afegirAlfabet(nom, tipus, path);
            System.out.println(output + "Alfabet creat amb nom: '"+nom+"' i tipus: '"+tipus+"'.");
        }
        catch(Excepcions e) {
            switch (e.getTipus()) {
                case "AlfabetJaExisteix":
                    System.out.println(errorOutput + "ERROR: Aquest Alfabet ja existeix. Prova amb un altre nom.");
                    break;
                case "TipusDadesNoValid":
                    System.out.println(errorOutput + "ERROR: El tipus de dades "+tipus+" no és un tipus vàlid. Prova amb {'text', 'llista-paraules'}.");
                    break;
                case "FormatDadesNoValid":
                    System.out.println(errorOutput + "ERROR: El format de les dades del fitxer "+path+" no és vàlid. (Veure more_info)");
                    break;
                case "EntradaLlegidaMalament":
                    System.out.println(errorOutput + "ERROR: Hi ha hagut un error en el processament de les dades.");
                    break;
                default:
                    System.out.println(errorOutput + "ERROR: " + e.getMessage());
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(errorOutput + "ERROR: El fitxer "+path+" no s'ha trobat.");
        }
        // FALTARÀ INCORPORRAR ELS CATCH DE LES EXCEPCIONS QUE COMPROVEN EL TIPUS I EL PATH DINS D'alfabet.java
        escollir();
    }

    public void mostraTeclat() {
        System.out.println(output + "Quin teclat vols mostrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            String out = ctrlPresentacio.visualitzarTeclat(nom);
            System.out.println(output + "Teclat:");
            System.out.println(out);
        }
        catch(TeclatNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Teclat no existeix.");
        }
        escollir();
    }

    public void esborraTeclat() {
        System.out.println(output + "Quin teclat vols esborrar? Indica'n el seu nom:");
        String nom = answerString();
        try {
            ctrlPresentacio.esborrarTeclat(nom);
            System.out.println(output + "Teclat "+nom+" esborrat.");
        }
        catch(TeclatNoExisteix e) {
            System.out.println(errorOutput + "ERROR: Aquest Teclat no existeix.");
        }
        escollir();
    }

    public void modificaTeclat() {
        System.out.println(output + "Quin teclat vols modificar? Indica'n el seu nom:");
        String nom = answerString();
        Map<Character, Character> canvis = new HashMap<>();
        System.out.println(output + "Quants canvis vols efectuar? Per cada canvi hauràs d'incloure dues lletres a intercanviar.");
        Integer n = answerInteger();
        if (n >= 1) {
            for (int i = 0; i < n; i++) {
                // lletra1 es canvia per lletra 2.
                System.out.println(output + "Introdueix la primera lletra del canvi número " + (i+1) + "." );
                Character lletra1 = answerCharacter();
                System.out.println(output + "Introdueix la segona lletra del canvi número " + (i+1) + "." );
                Character lletra2 = answerCharacter();
                System.out.println(output + "Canvi "+ i+1 + ": " + lletra1 + " -> " + lletra2);
                canvis.put(lletra1, lletra2); // S'introdueix el canvi
            }
            try {
                String out = ctrlPresentacio.modificarTeclat(nom, canvis);
                System.out.println(output + "Teclat "+nom+" esborrat.");
                System.out.println(out);
            }
            catch(TeclatNoExisteix e) {
                System.out.println(errorOutput + "ERROR: Aquest Teclat no existeix.");
            }
            catch(LletraNoTeclat e) {
                System.out.println(errorOutput + "ERROR: " + e.getMessage());
            }
        }
        else {
            System.out.println(errorOutput + "ERROR: El nombre de canvis ha de ser positiu.");
        }
        escollir();
    }

    public void nouTeclat() {
        System.out.println(output + "Per a afegir un nou teclat indica com vols anomenar-lo:");
        String nom = answerString();
        System.out.println(output + "Indica el nom del Alfabet amb que es generarà el Teclat:");
        String nomAlfabet = answerString();
        System.out.println(output + "Finalment, indica el Layout que vols fer servir, amb la seva mida (veure 'more_info'):");
        Integer idLayout = answerInteger();
        try {
            System.out.println("Generant teclat...");
            ctrlPresentacio.crearNouTeclat(nom, nomAlfabet, idLayout);
            System.out.println(output + "Teclat "+nom+" creat.");
        }
        catch (Excepcions e) {
            switch (e.getTipus()) {
                case "TeclatJaExisteix": 
                    System.out.println(errorOutput + "Ja existeix un Teclat amb el nom: " + nom + ". Prova amb un altre nom.");
                    break;
                case "AlfabetNoExisteix":
                    System.out.println(errorOutput + "No existeix un Alfabet amb el nom: " + nomAlfabet + ".");
                    break;
                case "LayoutNoExisteix":
                    System.out.println(errorOutput + "No existeix un Layout amb la mida: " + idLayout + ".");
                    break;
                case "MidesDiferents":
                    System.out.println(errorOutput + "Les mides de l'Alfabet seleccionat i del Layout han de ser iguals.");
                    break;
            }
        }
        escollir();
    }

    public void moreInfo() {
        System.out.print(
            " - nou_teclat      : Comanda per a crear un nou teclat, a partir d'un alfabet i un layout existent. \n" +
            "                     [!] Important que les mides del layout i alfabet siguin igual. \n" +
            " - modifica_teclat : Comanda per modificar un teclat existent, s'introdueixen parelles de lletres de les que es vol que \n" + 
            "                     s'intercanviin les posicions. Al acabar es mostra la distribució del teclat posterior als canvis. \n" +
            " - esborra_teclat  : Comanda per a esborrar un teclat existent. El teclat deixarà d'existir al esborrar-se. \n" +
            " - mostra_teclat   : Comanda per a visualitzar tota la informació rellevant d'un teclat existent. \n" +
            " - nou_alfabet     : Comanda per a crear un nou alfabet, a partir d'un tipus de dades {'text', 'llista-paraules'} \n" + 
            "                     i un fitxer (es passa a través del path) que contingui les dades. \n" +
            "                     Al directori test/exemples_input_alfabet/ es poden trobar exemples de fitxers amb dades per a crear alfabets. \n" +
            "                     El format de les dades que es poden introduïr és el següent: \n" +
            "                       - Per 'text': un text amb diverses paraules que siguin representatives de l'ús del Alfabet. \n" +
            "                       - Per 'llista-paraules': una llista de paraules i les seves freqüències. \n" + 
            "                         Un bon exemple: https://corpus.rae.es/lfrecuencias.html \n" +
            " - esborra_alfabet : Comanda per a esborrar un alfabet existent. L'alfabet esborrat deixarà d'existir. \n" +
            " - mostra_alfabet  : Comanda per a visualitzar tota la informació rellevant d'un alfabet existent. \n" +
            " - nou_layout      : Comanda per a crear un nou layout, a partir d'una mida. \n"+
            "                     Es creen diferents paràmetres automàticament que generen un layout.\n" +
            "                     El sistema està optimitzat per a mides entre 16 i 48. A més el sistema genera, inicialment, \n" +
            "                     4 layouts de mides entre 24 i 27. \n" +
            " - mostra_layout   : Comanda per a visualitzar tota la informació rellevant d'un layout existent. \n" +
            " - esborra_layout  : Comanda per a esborrar un layout existent i no generat inicialment. \n" + 
            "                     Per mides entre 24 i 27 no es podrà esborrar. \n" +
            "\n" +
            " - llista_teclats  : Comanda per a llistar informació reduïda de tots els teclats creats. \n" +
            " - llista_alfabets : Comanda per a llistar informació reduïda de tots els alfabets creats. \n" +
            " - llista_layouts  : Comanda per a llistar informació reduïda de tots els layouts existens. \n" +
            "\n" +
            "   finalitzar : Comanda per a finalitzar l'execució del programa. \n"
        );
        
        escollir();
    }

    public void finalitzar() {
        System.out.println("Que vagi bé! Et trobarem a faltar!");
        System.exit(0);
    }
}

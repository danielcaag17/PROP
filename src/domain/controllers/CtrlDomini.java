package src.domain.controllers;

import java.util.*;

import src.data.*;
import src.domain.classes.*;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    private CtrlAlfabetFile ctrlAlfabetFile;
    private Generador Generador;
    private int[] midesInicials = new int[] {24, 25, 26, 27};
    // private String strategy;
    private HashMap<String, Teclat> Teclats; // Cjt de Teclats, on String és el nom del Teclat
    private HashMap<String, Alfabet> Alfabets; // Cjt d'Alfabets, on String és el nom de l'Alfabet
    private HashMap<String, Layout> Layouts; // Cjt de Layouts, on String és l'id del Layout

    private static CtrlDomini singletonDomini;

    // Pre:
    // Post: s'ha creat una instància de controlador de domini
    public CtrlDomini() {
        init();
    }

    public static CtrlDomini getInstance() {
        if(singletonDomini == null) singletonDomini = new CtrlDomini();
        return singletonDomini;
    }

    // Pre:
    // Post: s'han inicialitzat les instàncies del model i variables del CtrlDomini
    public void init() {
//      ctrlAlfabetFile = CtrlAlfabetFile.getInstance();
        Teclats = new HashMap<String, Teclat>();
        Alfabets = new HashMap<String, Alfabet>();
        Layouts = new HashMap<String, Layout>();
        inicialitzarLayoutsBase();
//      strategy = "QuadraticAssignmentProblem"
    }

    /* Funcions de Transacció i de Domini */
    /*
     * - getters
     * - inicialitzarLayoutsBase()
     * - crearNouTeclat(nt, na, idL)
     * - modificarTeclat(nt)
     * - visualitzarTeclat(nt)
     * - esborrarTeclat(nt)
     * - afegirAlfabet(na, ta, pf)
     * - visualitzarAlfabet(na)
     * - visualitzarLayout(idL)
     */

    // Crea les instàncies de Layout de les mides predefinides.
    private void inicialitzarLayoutsBase() {
        for (int i = 0; i<midesInicials.length; i++) {
            Layout l = new Layout(i+1, midesInicials[i]);
            String key = String.valueOf(midesInicials[i]);
            Layouts.put(key, l);
        }
    }
    
    // Retorna el teclat amb nom nt
    public Teclat getTeclat(String nt) {
        return Teclats.get(nt);
    }

    // Retorna l'alfabet amb nom na
    public Alfabet getAlfabet(String na) {
        return Alfabets.get(na);
    }

    // Retorna el Layout amb id=idL
    public Layout getLayout(String idL) {
        return Layouts.get(idL);
    }

    /*
     * nt: nom del teclat 
     * na: nom del alfabet del teclat
     * idL: id del Layout del teclat
     * Pre: el teclat amb nom nt no existeix
     * Post: el teclat amb nom nt s'ha creat i associat amb l'alfabet amb nom na i el Layout amb id idL
     * Excepcions: 
     * - El teclat nt existeix
     * - La mida del alfabet na i el layout idL és diferent
     */
    public void crearNouTeclat(String nt, String na, String idL) throws TeclatJaExisteix, MidesDiferents {
        Alfabet a = Alfabets.get(na);
        Layout l = Layouts.get(idL);
        if (a.getMida() != l.getMida()) throw new MidesDiferents(a.getMida(), l.getMida());
        if (Teclats.get(nt) != null) throw new TeclatJaExisteix(nt);
        Teclat t = new Teclat(nt, l, a, Generador); // IMPORTANT QUE CREADORA TECLAT SIGUI PUBLIC
        t.generarTeclat(); // potser passar strategy?
        Teclats.put(nt, t);
    }
}

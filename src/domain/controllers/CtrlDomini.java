package src.domain.controllers;

import java.util.*;

import src.data.*;
import src.domain.classes.*;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    // private CtrlAlfabetFile ctrlAlfabetFile;
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

    // Crea les instàncies de Layout de les mides predefinides.
    private void inicialitzarLayoutsBase() {
        for (int i = 0; i<midesInicials.length; i++) {
            Layout l = new Layout(i+1, midesInicials[i]);
            String key = String.valueOf(midesInicials[i]);
            Layouts.put(key, l);
        }
    }
    
    /**
     *  @return Retorna el teclat amb nom nt.
     */ 
    public Teclat getTeclat(String nt) {
        return Teclats.get(nt);
    }

    /** 
     * @return Retorna l'alfabet amb nom na.
     */
    public Alfabet getAlfabet(String na) {
        return Alfabets.get(na);
    }

    /**
     * @return Retorna el Layout amb id=idL.
     */
    public Layout getLayout(String idL) {
        return Layouts.get(idL);
    }

    /**
     * Pre: el teclat amb nom nt no existeix. -
     * Post: el teclat amb nom nt s'ha creat i associat amb l'alfabet amb nom na i el Layout amb id idL.
     * 
     * @param nt: nom del teclat.
     * @param na: nom del alfabet del teclat.
     * @param idL: id del Layout del teclat.
     * 
     * @throws TeclatJaExisteix si existeix una instància Teclat amb nom nt.
     * @throws MidesDiferents si la mida del Alfabet amb nom na i el Layout amb id idL són diferents. 
     */
    public void crearNouTeclat(String nt, String na, String idL) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix, LayoutNoExisteix {
        if (Teclats.get(nt) != null) throw new TeclatJaExisteix(nt);
        if (Alfabets.get(na) == null) throw new AlfabetNoExisteix(na);
        if (Layouts.get(idL) == null) throw new LayoutNoExisteix(idL);
        Alfabet a = Alfabets.get(na);
        Layout l = Layouts.get(idL);
        if (a.getSize() != l.getSize()) throw new MidesDiferents(a.getSize(), l.getSize());
        Teclat t = new Teclat(nt, l, a, Generador); // IMPORTANT QUE CREADORA TECLAT SIGUI PUBLIC
        t.crearTeclat(); // potser passar strategy?
        Teclats.put(nt, t);
    }

    /**
     * Pre: el teclat amb nom nt existeix, els canvis són vàlids. -
     * Post: el teclat amb nom nt s'ha modificat amb els canvis aplicats.
     * 
     * @param nt: nom del teclat 
     * @param canvis: canvis que es volen aplicar al teclat
     * @return Teclat que ha sigut modificat. 
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public Teclat modificarTeclat(String nt, Map<Character, Character> canvis) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        Teclat t = getTeclat(nt);
        for (Character c : canvis.keySet()) {
            t.modificarTeclat(c, canvis.get(c)); 
        }
        Teclats.replace(nt, t);
        return t;
    }

    /**
     * Pre: el teclat amb nom nt existeix.
     * 
     * @param nt: nom del teclat.
     * @return Teclat amb nom nt.
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public Teclat visualitzarTeclat(String nt) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        return getTeclat(nt);
    }

    /**
     * Pre: el teclat amb nom nt existeix. -
     * Post: el teclat amb nom nt s'ha esborrat.
     * 
     * @param nt: nom del teclat.
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public void esborrarTeclat(String nt) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        Teclats.remove(nt);
    }

    /**
     * Pre: l'alfabet amb nom na no existeix. -
     * Post: s'ha creat un alfabet amb nom na i dades extretes de pf.
     * 
     * @param na nom del alfabet.
     * @param ta tipus (vàlid*) de les dades per crear l'alfabet.
     * @param pf path (vàlid*) al fitxer on hi ha guardades les dades.
     * @see (*)que s'ha comprovat previàment.
     * @throws AlfabetJaExisteix si existeix una instància Alfabet amb nom na.
     */
    public void afegirAlfabet(String na, String ta, String pf) throws AlfabetJaExisteix {
        if (Alfabets.get(na) != null) throw new AlfabetJaExisteix(na);
        Alfabet a = new Alfabet(na);
        if (ta == "text") { a.readText(pf); }
        else if (ta == "llista-paraules") { a.readWords(pf); }
        Alfabets.put(na, a);
    }

    /**
     * Pre: l'alfabet amb nom na existeix.
     * 
     * @param na nom del alfabet.
     * @return Retorna l'alfabet amb nom na.
     * @throws AlfabetNoExisteix si no existeix una instància d'alfabet amb nom na.
     */
    public Alfabet visualitzarAlfabet(String na) throws AlfabetNoExisteix {
        if(Alfabets.get(na) ==  null) throw new AlfabetNoExisteix(na);
        return getAlfabet(na);
    }
    
    /**
     * Pre: l'alfabet amb nom na existeix. -
     * Post: l'alfabet amb nom na s'ha esborrat.
     * 
     * @param na nom del alfabet.
     * @throws AlfabetNoExisteix si no existeix una instància d'alfabet amb nom na.
     */
    public void esborrarAlfabet(String na) throws AlfabetNoExisteix {
        if(Alfabets.get(na) ==  null) throw new AlfabetNoExisteix(na);
        Alfabets.remove(na);
    }

    /**
     * Pre: el layout amb id idL existeix.
     * 
     * @param idL id del Layout.
     * @return Retorna el layout amb id idL.
     * @throws LayoutNoExisteix si no existeix una instància de layout amb id idL.
     */
    public Layout visualitzarLayout(String idL) throws LayoutNoExisteix {
        if(Layouts.get(idL)==null) throw new LayoutNoExisteix(idL);
        return getLayout(idL);
    }

    /*
     * - getters
     * - inicialitzarLayoutsBase()
     * - crearNouTeclat(nt, na, idL)
     * - modificarTeclat(nt)
     * - visualitzarTeclat(nt)
     * - esborrarTeclat(nt)
     * - afegirAlfabet(na, ta, pf)
     * - visualitzarAlfabet(na)
     * - esborraAlfabet(na)
     * - visualitzarLayout(idL)
     */

}

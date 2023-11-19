package src.domain.controllers;

import java.io.FileNotFoundException;
import java.util.*;

//import src.data.*;
import src.domain.classes.*;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    // private CtrlAlfabetFile ctrlAlfabetFile;
    private Generador Generador;
    private int[] midesInicials = new int[] {24, 25, 26, 27}; // mides inicials dels Layouts disponibles
    // private String strategy;
    private HashMap<String, Teclat> Teclats; // Cjt de Teclats, on String és el nom del Teclat
    private HashMap<String, Alfabet> Alfabets; // Cjt d'Alfabets, on String és el nom de l'Alfabet
    private HashMap<Integer, Layout> Layouts; // Cjt de Layouts, on String és l'id del Layout
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
        Layouts = new HashMap<Integer, Layout>();
        inicialitzarLayoutsBase();
//      strategy = "QuadraticAssignmentProblem"
    }

    /* Funcions de Transacció i de Domini */

    // Crea les instàncies de Layout de les mides predefinides.
    private void inicialitzarLayoutsBase() {
        for (int i = 0; i<midesInicials.length; i++) {
            Layout l = new Layout(midesInicials[i]);
            Layouts.put(midesInicials[i], l); // la clau de Layout és la seva mida
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
    public Layout getLayout(Integer idL) {
        return Layouts.get(idL);
    }

    /**
     * @return Retorna un array de Strings amb el nom, el nom del alfabet i el nom del layout de cada teclat del sistema.
     */
    public String[] getListTeclats() {
        String[] s = new String[Teclats.size()];
        int i=0;
        for (String key : Teclats.keySet()) {
            Teclat t = Teclats.get(key);
            s[i] = "Nom: " + t.getNom() + 
                   " - Nom alfabet: " + t.getAlfabet().getNom() +
                   " - Nom Layout: " + t.getLayout().getId();
            i++;
        }
        return s;
    }

    /**
     * @return Retorna un array de Strings amb el nom, la mida i l'abecedari de cada alfabet del sistema.
     */
    public String[] getListAlfabets() {
        String[] s = new String[Alfabets.size()];
        int i=0;
        for (String key : Alfabets.keySet()) {
            Alfabet a = Alfabets.get(key);
            s[i] = "Nom: " + a.getNom() + 
                   " - Mida: " + a.getSize() +
                   " - Abecedari: " + a.getAbecedari().toString();
            i++;
        }
        return s;
    }
    
    private String getString(int[][] mat) {
        String s = "";
        for(int i = 0; i < mat.length; i++) {
            s+="\n";
            for(int j = 0; j < mat[i].length; j++) {
                s += mat[i][j] + " ";
            }
        }
        return s;
    }

    /**
     * @return Retorna un array de String amb la mida i la matriu de distribució d'ids de cada layout del sistema
     */
    public String[] getListLayouts() {
        String[] s = new String[Layouts.size()];
        int i=0;
        for (Integer key : Layouts.keySet()) {
            Layout l = Layouts.get(key);
            s[i] = "Mida: " + l.getSize() + 
                   "\n - Matriu distribució d'ids: " + getString(l.getDistribucioFilled()); // Aquest toString() s'hauria de veure que fa.
            i++;
        }
        return s;
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
    public void crearNouTeclat(String nt, String na, Integer idL) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix, LayoutNoExisteix {
        if (Teclats.get(nt) != null) throw new TeclatJaExisteix(nt);
        if (Alfabets.get(na) == null) throw new AlfabetNoExisteix(na);
        if (Layouts.get(idL) == null) throw new LayoutNoExisteix(idL.toString());
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
    public String modificarTeclat(String nt, Map<Character, Character> canvis) throws TeclatNoExisteix, LletraNoTeclat {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        Teclat t = getTeclat(nt);
        for (Character c : canvis.keySet()) {
            t.modificarTeclat(c, canvis.get(c)); 
        }
        Teclats.replace(nt, t);
        return t.toString(); // Potser no fa falta
    }

    /**
     * Pre: el teclat amb nom nt existeix.
     * 
     * @param nt: nom del teclat.
     * @return Retorna el string que representa el teclat amb nom nt.
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public String visualitzarTeclat(String nt) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        return getTeclat(nt).toString();
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
     * @param ta tipus de les dades per crear l'alfabet.
     * @param pf path (vàlid*) al fitxer on hi ha guardades les dades.
     * @see (*)que s'ha comprovat previàment.
     * @throws AlfabetJaExisteix si existeix una instància Alfabet amb nom na.
     */
    public void afegirAlfabet(String na, String ta, String pf) throws AlfabetJaExisteix, FileNotFoundException, FormatDadesNoValid, TipusDadesNoValid {
        if (Alfabets.get(na) != null) throw new AlfabetJaExisteix(na);
        Alfabet a = new Alfabet(na);
        a.readInput(ta, pf);
        Alfabets.put(na, a);
    }

    /**
     * Pre: l'alfabet amb nom na existeix.
     * 
     * @param na nom del alfabet.
     * @return Retorna el string que representa l'alfabet amb nom na.
     * @throws AlfabetNoExisteix si no existeix una instància d'alfabet amb nom na.
     */
    public String visualitzarAlfabet(String na) throws AlfabetNoExisteix {
        if(Alfabets.get(na) ==  null) throw new AlfabetNoExisteix(na);
        return getAlfabet(na).toString();
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
     * Pre: l'alfabet amb id idL no existeix. -
     * Post: s'ha creat un Layout amb mida idL.
     * 
     * @param idL mida del Layout a crear. Funciona com a id.
     * @throws LayoutJaExisteix si existeix una instància Layout amb id idl.
     */
    public void afegirLayout(Integer idL) throws LayoutJaExisteix {
        if (Layouts.get(idL) != null) throw new LayoutJaExisteix(idL.toString());
        Layout l = new Layout(idL);
        Layouts.put(idL, l);
    }

    /**
     * Pre: el layout amb id idL existeix.
     * 
     * @param idL id del Layout.
     * @return Retorna el string que representa el layout amb id idL.
     * @throws LayoutNoExisteix si no existeix una instància de layout amb id idL.
     */
    public String visualitzarLayout(Integer idL) throws LayoutNoExisteix {
        if(Layouts.get(idL)==null) throw new LayoutNoExisteix(idL.toString());
        return getLayout(idL).toString();
    }

    /**
     * Pre: el layout amb id idL existeix. - 
     * Post: el layout amb id idL s'ha esborrat.
     * 
     * @param idL id del Layout
     * @throws LayoutNoExisteix si no existeix una instància de layout amb id idl.
     * @throws LayoutNoBorrable si id del layout que es vol esborrar pertany als layouts generats per defecte.
     */
    public void esborrarLayout(Integer idL) throws LayoutNoExisteix, LayoutNoBorrable {
        // no es poden borrar els 4 layouts inicials
        if (Layouts.get(idL) == null) throw new LayoutNoExisteix(idL.toString());
        if (Arrays.binarySearch(midesInicials, idL) >= 0) throw new LayoutNoBorrable(idL.toString());
        Layouts.remove(idL);
    }

}

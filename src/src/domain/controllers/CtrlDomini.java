package src.domain.controllers;

import java.io.FileNotFoundException;
import org.json.*;
import java.util.*;

//import src.data.*;
import src.domain.classes.*;
import src.data.CtrlPersistenciaFile;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    private CtrlPersistenciaFile ctrlPersistFile;
    private Generador Generador;
    private int[] midesInicials = new int[] {24, 25, 26, 27}; // mides inicials dels Layouts disponibles
    private String strategy;
    private HashMap<String, Teclat> Teclats; // Cjt de Teclats, on String és el nom del Teclat
    private HashMap<String, Alfabet> Alfabets; // Cjt d'Alfabets, on String és el nom de l'Alfabet
    private HashMap<Integer, Layout> Layouts; // Cjt de Layouts, on String és l'id del Layout
    private static CtrlDomini singletonDomini;
    private Factoria factoria;

    // Pre:
    // Post: s'ha creat una instància de controlador de domini
    private CtrlDomini() {
        init();
    }

    public static CtrlDomini getInstance() {
        if(singletonDomini == null) singletonDomini = new CtrlDomini();
        return singletonDomini;
    }

    // Pre:
    // Post: s'han inicialitzat les instàncies del model i variables del CtrlDomini
    public void init() {
        factoria = Factoria.getInstance();

        ctrlPersistFile = factoria.getCtrlPersistenciaFile();
        Teclats = new HashMap<String, Teclat>();
        Alfabets = new HashMap<String, Alfabet>();
        Layouts = new HashMap<Integer, Layout>();

        // Si hi ha un estat previ s'haurien de omplir els valors del estat

        strategy = "Genetic"; // strategy pot ser {"BranchBound", "Genetic"} // Buscar una altre forma de fer-ho
        Generador = new Generador(strategy);
        
        // restoreState(); // Restaura l'estat previ de l'execució si n'hi havia de guardat.
        if (Layouts.isEmpty()) inicialitzarLayoutsBase(); // només si és execució incial
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
     * <li>Pre:</li>
     * <li>Post: la estratègia de generador ha sigut canviada.</li>
     */
    public String toggleStrategy() {
        if (this.strategy == "BranchBound") {
            this.strategy = "Genetic";
            Generador.setStrategy(this.strategy);
        }
        else {
            this.strategy = "BranchBound";
            Generador.setStrategy(this.strategy);
        }
        return strategy;
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
                   " - Abecedari: " + Arrays.toString(a.getAbecedari());
            i++;
        }
        return s;
    }

    public String[] getListGeneradors() {
        String[] s = new String[]{"Genetic", "QAP"};
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
                   "\n - Matriu distribució d'ids: \n" + l.getDistribucioFilledString(); // Aquest toString() s'hauria de veure que fa.
            i++;
        }
        return s;
    }

    /**
     * <li>Pre: el teclat amb nom nt no existeix.</li>
     * <li>Post: el teclat amb nom nt s'ha creat i associat amb l'alfabet amb nom na i amb l'estratègia generadora ge.</li>
     * 
     * @param nt nom del teclat.
     * @param na nom del alfabet del teclat.
     * @param ge nom de l'estratègia generadora del teclat.
     * 
     * @throws TeclatJaExisteix si existeix una instància Teclat amb nom nt.
     * @throws AlfabetNoExisteix si no existeix una instància Alfabet amb nom na.
     * @throws MidesDiferents si la mida del Alfabet amb nom na i el Layout amb id idL són diferents. 
     */
    public void crearNouTeclat(String nt, String na, String ge) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix {
        if (Teclats.get(nt) != null) throw new TeclatJaExisteix(nt);
        if (Alfabets.get(na) == null) throw new AlfabetNoExisteix(na);
        Alfabet a = Alfabets.get(na);
        Integer idL = a.getSize();
        Layout l;
        // Comprovem que existeix un layout amb mida idL i si no el creem.
        if (!Layouts.containsKey(idL)) {
            try { this.afegirLayout(idL); }
            catch(Excepcions e) {}
        }
        l = Layouts.get(idL);
        if (a.getSize() != l.getSize()) throw new MidesDiferents(a.getSize(), l.getSize());
        Teclat t = new Teclat(nt, l, a, Generador); // IMPORTANT QUE CREADORA TECLAT SIGUI PUBLIC
        Generador.setStrategy(ge);
        t.crearTeclat(); // potser passar strategy?
        Teclats.put(nt, t);
    }

    /**
     * <li>Pre: el teclat amb nom nt existeix, els canvis són vàlids.</li>
     * <li>Post: el teclat amb nom nt s'ha modificat amb els canvis aplicats.</li>
     * 
     * @param nt nom del teclat 
     * @param canvis canvis que es volen aplicar al teclat
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
     * <li>Pre: el teclat amb nom nt existeix.</li>
     * 
     * @param nt nom del teclat.
     * @return Retorna el string que representa el teclat amb nom nt.
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public String visualitzarTeclat(String nt) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        return getTeclat(nt).toString();
    }

    /**
     * <li>Pre: el teclat amb nom nt existeix.</li>
     * <li>Post: el teclat amb nom nt s'ha esborrat.</li>
     * 
     * @param nt nom del teclat.
     * 
     * @throws TeclatNoExisteix si no existeix una insància Teclat amb nom nt.
     */
    public void esborrarTeclat(String nt) throws TeclatNoExisteix {
        if (Teclats.get(nt) == null) throw new TeclatNoExisteix(nt);
        Teclats.remove(nt);
    }

    /**
     * <li>Pre: l'alfabet amb nom na no existeix.</li>
     * <li>Post: s'ha creat un alfabet amb nom na i dades extretes de pf.</li>
     * 
     * @param na nom del alfabet.
     * @param ta tipus de les dades per crear l'alfabet.
     * @param pf path al fitxer on hi ha guardades les dades.
     * @throws AlfabetJaExisteix si existeix una instància Alfabet amb nom na.
     */
    public void afegirAlfabet(String na, String ta, String pf) throws AlfabetJaExisteix, FileNotFoundException, FormatDadesNoValid, TipusDadesNoValid, EntradaLlegidaMalament {
        if (Alfabets.get(na) != null) throw new AlfabetJaExisteix(na);
        Alfabet a;
        switch (ta) {
            case "text":
                a = new Text(na);
                break;
            case "llista-paraules":
                a = new Words(na);
                break;        
            default:
                throw new TipusDadesNoValid();
        }
        a.readInput(pf);
        Alfabets.put(na, a);
    }

    /**
     * <li>Pre: l'alfabet amb nom na existeix.</li>
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
     * <li>Pre: l'alfabet amb nom na existeix.</li>
     * <li>Post: l'alfabet amb nom na s'ha esborrat.</li>
     * 
     * @param na nom del alfabet.
     * @throws AlfabetNoExisteix si no existeix una instància d'alfabet amb nom na.
     */
    public void esborrarAlfabet(String na) throws AlfabetNoExisteix {
        if(Alfabets.get(na) ==  null) throw new AlfabetNoExisteix(na);
        Alfabets.remove(na);
    }

    /**
     * <li>Pre: l'alfabet amb id idL no existeix.</li>
     * <li>Post: s'ha creat un Layout amb mida idL.</li>
     * 
     * @param idL mida del Layout a crear. Funciona com a id.
     * @throws LayoutJaExisteix si existeix una instància Layout amb id idl.
     */
    public void afegirLayout(Integer idL) throws LayoutJaExisteix, MidaMassaPetita {
        if (Layouts.get(idL) != null) throw new LayoutJaExisteix(idL.toString());
        if (idL < 1) throw new MidaMassaPetita(idL.toString());
        Layout l = new Layout(idL);
        Layouts.put(idL, l);
    }

    /**
     * <li>Pre: el layout amb id idL existeix.</li>
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
     * <li>Pre: el layout amb id idL existeix.</li>
     * <li>Post: el layout amb id idL s'ha esborrat.</li>
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

    /**
     * <li>Pre: </li>
     * <li>Post: el estat de totes les instàncies, d'alfabet, de teclat i de layout està desat a fitxers del directori: <code>data/</code> </li>
     */
    public void saveState() {
        for (String key : Teclats.keySet()) {
            Teclat t = Teclats.get(key);
            String tRaw = t.saveData(); 
            ctrlPersistFile.saveState("teclats", t.getNom(), tRaw);
        }
        for (String key : Alfabets.keySet()) {
            Alfabet a = Alfabets.get(key);
            String aRaw = a.saveData();
            ctrlPersistFile.saveState("alfabets", a.getNom(), aRaw);
        }
        for (Integer key : Layouts.keySet()) {
            Layout l = Layouts.get(key);
            String lRaw = l.saveData();
            ctrlPersistFile.saveState("layouts", Integer.toString(l.getSize()), lRaw);
        }
    }
    
    /**
     * <li>Pre: l'aplicació s'acaba d'iniciar </li>
     * <li>Post: l'estat de l'aplicació s'ha resturat de la última vegada desada. </li>
     */
    private void restoreState() {
        String[] filesTeclats = ctrlPersistFile.getAll("teclats");
        String[] filesAlfabets = ctrlPersistFile.getAll("alfabets");
        String[] filesLayouts = ctrlPersistFile.getAll("layouts");
        for (String file : filesTeclats) {
            String tRaw = ctrlPersistFile.getObject(file);
            // TODO: extreure dades de tRaw del JSON i crear instància de Teclat
            String nom = ""; 
            Teclat t = new Teclat(nom, null, null, null);
            Teclats.put(nom, t);
        }
        for (String file : filesAlfabets) {
            String aRaw = ctrlPersistFile.getObject(file);
            // TODO: extreure dades de aRaw del JSON i crear instància de Alfabet
            String nom = "";
            Alfabet a;
            a = new Text(nom); 
            Alfabets.put(nom, a);
        }
        for (String file : filesLayouts) {
            String lRaw = ctrlPersistFile.getObject(file);
            // TODO: extreure dades de lRaw del JSON i crear instància de Layout
            Integer idL = 1;
            Layout l = new Layout(idL);
            Layouts.put(idL, l);
        }
    }
}

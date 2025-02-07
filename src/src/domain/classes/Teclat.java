package src.domain.classes;

import java.util.Map;

import org.json.*;

import src.exceptions.LletraNoTeclat;

public class Teclat {
    private String nom;                         // Clau primaria de la classe Teclat
    private Layout L;                           // Layout que el Teclat té associat
    private Alfabet A;                          // Alfabet que el Teclat té associat
    private Generador G;                        // Generador amb el qual es genera el Teclat
    private Map<Character, Integer> teclat;     // Estructura per guardar la relació entre les lletres de l'Alfabet i les seves ids del Layout
    private char[][] distribucioCharacters;     // Matriu que representa el Teclat

    // Pre: no existeix cap Teclat amb aquest nom
    // Post: s'ha creat una instància de Teclat amb nom = nom
    public Teclat(String nom) {
        setNom(nom);
    }

    // Pre: no existeix cap Teclat amb aquest nom
    // Post: s'ha creat una instància de Teclat amb nom = nom, distribucioCharacters = distribucioCharacters, G = ge, L = layout de la mida del teclat, A = alfabet buit
    public Teclat(String nom, Map<Character, Integer> teclat, char[][] distribucioCharacters, Generador ge) {
        setNom(nom);
        setLayout(new Layout(teclat.size()));
        setAlfabet(new Text("empty"));
        setGenerador(ge);
        setTeclat(teclat);
        setDistribucioCharacters(distribucioCharacters);
    }

    /**
     * Pre: no existeix cap Teclat amb aquest nom,
     *      Layout existeix,
     *      Alfabet existeix,
     *      Generador existeix
     * Post: s'ha creat una instància de Teclat amb nom = nom, Layout = L,
     *       Alfabet = A, Generador = G
     */
    public Teclat(String nom, Layout L, Alfabet A, Generador G) {
        setNom(nom);
        setLayout(L);
        setAlfabet(A);
        setGenerador(G);
    }
    
    /**
     * Pre: A existeix, L existeix, G exiteix
     * Post: s'ha creat la matriu que representa el Teclat
     */
    public void crearTeclat () {
        // Passar el paràmetres necessaris per a que el Generador pugui generar el teclat   
        teclat = G.generarTeclat(A.getFrequencies(), L.getDistancies(), A.getCharacter());
        omplirDistribucio();                        // Omplir la matriu del Teclat amb la lletra que toca a cada id
    }

    /**
     * Pre:
     * Post: s'ha omplert la matriu segons la relació entre les lletres i les seves posicions
     */
    private void omplirDistribucio() {
        distribucioCharacters = L.getDistribucio();
        for (Character c : teclat.keySet()) {
            int id = teclat.get(c);                                     // Obtenir la id de cada lletra
            Pair<Integer, Integer> p = L.getCoordenadaFromId(id);       // Traduir la id a la posició de la matriu
            distribucioCharacters[p.getFirst()][p.getSecond()] = c;     // Posar la lletra a la posició corresponent de la matriu
        }
    }

    /**
     * Pre: 
     * Post: s'ha intercanviat la lletra a per la lletra b
     * 
     * @param a es un caràcter.
     * @param b es un caràcter.
     * 
     * @throws LletraNoTeclat si alguna de les lletres a o b no pertanyen al Teclat
     */
    public void modificarTeclat(Character a, Character b) throws LletraNoTeclat {     // Posicions a intercanviar
        if (! teclat.containsKey(a))                                    // Comprobar si alguna de les dues lletres no són del Teclat
            throw new LletraNoTeclat(Character.toString(a));                                 // Llençar excepció
        if (! teclat.containsKey(b))
            throw new LletraNoTeclat(Character.toString(b));
        
        swapLetters(a, b);                                              // Intercanviar la posició de les lletres
    }

    /**
     * Pre: les lletres pertanyen al Teclat
     * Post: s'ha intercanviat la lletra a per la lletra b
     * 
     * @param a es un caràcter del Teclat.
     * @param b es un caràcter del Teclat.
     */
    private void swapLetters(Character a, Character b) {
        Integer aux = teclat.get(a);
        teclat.put(a, teclat.get(b));
        teclat.put(b, aux);

        Pair<Integer, Integer> aPos = L.getCoordenadaFromId(teclat.get(a));
        Pair<Integer, Integer> bPos = L.getCoordenadaFromId(teclat.get(b));
        distribucioCharacters[aPos.first][aPos.second] = a;
        distribucioCharacters[bPos.first][bPos.second] = b;
    }

    /**
     * Pre: 
     * Post: associa el nom que identifica el Teclat amb nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Pre: 
     * Post: associa el Layout del Teclat amb L
     */
    private void setLayout(Layout L) {
        this.L = L;
    }

    /**
     * Pre: 
     * Post: associa l'Alfabet del Teclat amb A
     */
    private void setAlfabet(Alfabet A) {
        this.A = A;
    }

    /**
     * Pre: 
     * Post: associa el Generador del Teclat amb G
     */
    private void setGenerador(Generador G) {
        this.G = G;
    }

    /**
     * Pre: 
     * Post: associa el teclat (la relació caràcter id) del Teclat amb teclat
     */
    private void setTeclat(Map<Character, Integer> teclat) {
        this.teclat = teclat;
    }

    /**
     * Pre: 
     * Post: associa la distribucioCharacters del Teclat amb distribucioCharacters passat com a paràmetre
     */
    private void setDistribucioCharacters(char[][] distribucioCharacters) {
        this.distribucioCharacters = distribucioCharacters;
    }

    /**
     * Pre: 
     * Post: es retorna el nom que identifica el Teclat
     * 
     * @return el nom que identifica el Teclat
     */
    public String getNom () {
        return nom;
    }

    /**
     * Pre: 
     * Post: es retorna la relació entre les lletres del Teclat i les seves ids
     * 
     * @return la relació entre les lletres del Teclat i les seves ids
     */
    public Map<Character, Integer> getTeclat () {
        return teclat;
    }

    /**
     * Pre: 
     * Post: es retorna la matriu amb les lletres del Teclat
     * 
     * @return la matriu amb les lletres del Teclat
     */
    public char[][] getDistribucioCharacters () {
        return distribucioCharacters;
    }

    /**
     * Pre: 
     * Post: es retorna la id de la lletra a
     * 
     * @return la id de la lletra a
     * 
     * @throws LletraNoTeclat si la lletra a no pertany al Teclat
     */
    public Integer getPosLletra (Character a) throws LletraNoTeclat {
        if (! teclat.containsKey(a))
            throw new LletraNoTeclat(Character.toString(a));
        return teclat.get(a);
    }

    /**
     * Pre: 
     * Post: es retorna l'Alfabet del Teclat
     * 
     * @return l'Alfabet del Teclat
     */
    public Alfabet getAlfabet () {
        return A;
    }

    /**
     * Pre: 
     * Post: es retorna el Layout del Teclat
     * 
     * @return el Layout del Teclat
     */
    public Layout getLayout () {
        return L;
    }

    /**
     * Pre: 
     * Post: es transforma el Teclat en un String
     * 
     * @return el Teclat en format de String
     */
    @Override
    public String toString() {
        String result = "Nom: " + nom + "\n";                                // Guarda el nom del Teclat a result
        result += "Nom Alfabet: " + A.getNom() + "\n";                      // Guardar el nom de l'Alfabet a result
        result += "Teclat: \n";
        for (int i = 0; i < distribucioCharacters.length; i++) {
            for (int j = 0; j < distribucioCharacters[i].length; j++) {
                result += distribucioCharacters[i][j] + " ";                // Guardar tota la matriu que representa el Teclat
            }
            result += "\n";   
        }
        return result;
    }

    private static JSONObject mapToJson(Map<Character, Integer> map) {
        JSONObject json_obj = new JSONObject(map);
        return json_obj;
    }

    private static JSONArray matToJson(char[][] matrix) {
        JSONArray mat_json = new JSONArray();
        for (int i = 0; i < matrix.length; i++) {
            JSONArray row = new JSONArray();
            for (int j = 0; j < matrix[0].length; j++) {
                //row.put(matrix[i][j]);
                row.put(Character.toString(matrix[i][j]));
            }
            mat_json.put(row);
        }
        return mat_json;
    }
    
    public String saveData() {
        JSONObject ret = new JSONObject();
        
        ret.put("nom", this.nom);
        ret.put("teclat", mapToJson(this.teclat));
        ret.put("distribucioCharacters", matToJson(this.distribucioCharacters));

        return ret.toString(4);
    }
 }

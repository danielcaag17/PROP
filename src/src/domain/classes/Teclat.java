package src.domain.classes;

import java.util.Map;

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
        this.nom = nom;
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
        this.nom = nom;
        this.L = L;
        this.A = A;
        this.G = G;
    }
    
    /**
     * Pre: A existeix, L existeix, G exiteix
     * Post: s'ha creat la matriu que representa el Teclat
     */
    public void crearTeclat () {                // si es vol fer Genetica s'haura de passar algun paràmetre mes !!!
        // teclat = G.generarTeclat(A.getFrequencies(), L.getDistancies(), A.getAbecedari(), A.getCharacter());

        // Passar el paràmetres necessaris per a que el Generador pugui generar el teclat   
        teclat = G.generarTeclat(A.getFrequencies(), L.getDistancies(), A.getCharacter());
        omplirDistribucio();                        // Omplir la matriu del Teclat amb la lletra que toca a cada id
    }

    /**
     * Pre:
     * Post: s'ha omplert la matriu segons la relació entre les lletres i les seves posicions
     */
    private void omplirDistribucio() {
        distribucioCharacters = new char[L.getRows()][L.getColumns()];
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
            throw new LletraNoTeclat();                                 // Llençar excepció
        if (! teclat.containsKey(b))
            throw new LletraNoTeclat();
        
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
    }

    // ????
    public void guardarTeclat () {

    }

    // UI
    public void provarTeclat () {

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
            throw new LletraNoTeclat();
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
        String result = "Nom:" + nom + "\n";                                // Guarda el nom del Teclat a result
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
 }

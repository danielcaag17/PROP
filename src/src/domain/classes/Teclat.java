package src.domain.classes;

import java.util.Map;

public class Teclat {
    private String nom;     // Clau primaria
    private Layout L;
    private Alfabet A;
    private Generador G;
    private Map<Character, Integer> teclat;     // Estructura per guardar el teclat, no definitiu
    private char[][] distribucioCharacters;     //Matriu de les lletres


    public Teclat(String nom) {    // Creadora
        this.nom = nom;
    }

    public Teclat(String nom, Layout L, Alfabet A, Generador G) {  // Creadora amb tots els parámetres
        this.nom = nom;
        this.L = L;
        this.A = A;
        this.G = G;
    }
    
    public void crearTeclat () {
        // layout --> distancies
        // alfabet --> freq
        teclat = G.generarTeclat(A.getFrequencies(), L.getDistancies(), A.getAbecedari(), A.getCharacter());
        omplirDistribució();
    }

    private void omplirDistribució () {
        for (Character c : teclat.keySet()) {
            int id = teclat.get(c);
            Pair<Integer, Integer> p = L.getCoordenadaFromId(id);
            distribucioCharacters[p.getFirst()][p.getSecond()] = c;
        }
    }

    //Entenc que el Layout no es pot modificar i el que es modifica es la pos. de les lletres
    //Veure si es vol void o que retorni teclat
    public void modificarTeclat(Character a, Character b) {     // Posicions a intercanviar
        // if les posicions a intercanviar es troben dins del teclat --> excepcio
        swapLetters(a, b);
    }

    //Veure una millor manera, malament perque swap pot fer swap amb altres elements i no nomes teclat
    private void swapLetters(Character a, Character b) {
        Integer aux = teclat.get(a);
        teclat.put(a, teclat.get(b));
        teclat.put(b, aux);
    }

    public void guardarTeclat () {

    }

    /*
    Quan es decideixi descartar teclat no es fa res
    public void descartarTeclat () {

    }
    */

    // Printar d'una manera més visual
    // Aquesta fucnió implementa una altre
    public void visualitzarTeclat () {
        for (Character c : teclat.keySet()) {
            System.out.println(c.toString());
        }
    }

    // UI
    public void provarTeclat () {

    }

    public void esborrarTeclat () {

    }

    public String getNom () {
        return nom;
    }

    public Map<Character, Integer> getTeclat () {
        return teclat;
    }

    public char[][] getDistribucioCharacters () {
        return distribucioCharacters;
    }

    // Retorna la posició de la lletra en el teclat
    public Integer getPosLletra (Character a) {
        return teclat.get(a);
    }

    // Retorna Alfabet
    public Alfabet getAlfabet () {
        return A;
    }

    // Retorna Layout
    public Layout getLayout () {
        return L;
    }

    @Override
    public String toString() {
        String result = nom + "\n";
        result += A.getNom() + "\n";
        for (int i = 0; i < distribucioCharacters.length; i++) {
            for (int j = 0; j < distribucioCharacters[i].length; j++) {
                result += distribucioCharacters[i][j] + " ";
            }
            result += "\n";   
        }
        return result;
    }
 }

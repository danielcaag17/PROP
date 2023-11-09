package src.domain.classes;

import java.util.Map;

public class Teclat {
    private String nom;     // Clau primaria
    private Layout L;
    private Alfabet A;
    private Generador G;
    private Map<Character, Integer> teclat;    // Estructura per guardar el teclat, no definitiu


    Teclat(String nom) {    // Creadora
        this.nom = nom;
    }

    Teclat(String nom, Layout L, Alfabet A, Generador G) {  // Creadora amb tots els parámetres
        this.nom = nom;
        this.L = L;
        this.A = A;
        this.G = G;
    }
    
    public void crearTeclat () {
        teclat = G.generarTeclat(L, A);
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

    public void visualitzarTeclat () {

    }

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

    // Retorna la posició de la lletra en el teclat
    public Integer getPosLletra (Character a) {
        return teclat.get(a);
    }
 }

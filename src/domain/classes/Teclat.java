package src.domain.classes;

public class Teclat {
    private String nom;     // Clau primaria
    private Layout L;
    private Alfabet A;
    private Generador G;
    private char[][] teclat;    // Estructura per guardar el teclat, no definitiu


    Teclat(String nom) {    // Creadora
        this.nom = nom;
    }

    Teclat(String nom, Layout L, Alfabet A, Generador G) {  // Creadora amb tots els parámetres
        this.nom = nom;
        this.L = L;
        this.A = A;
        this.G = G;
    }
    
    public void crearTeclat() {
        teclat = G.generarTeclat(L, A);
    }

    public void modificarTeclat() {

    }

    public void guardarTeclat() {

    }

    /*
    Quan es decideixi descartar teclat no es fa res
    public void descartarTeclat () {

    }
    */

    public void visualitzarTeclat() {

    }

    public void provarTeclat() {

    }

    public void esborrarTeclat() {

    }

    public String getNom() {
        return nom;
    }
 }

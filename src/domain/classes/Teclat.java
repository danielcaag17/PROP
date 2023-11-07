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

    //Entenc que el Layout no es pot modificar i el que es modifica es la pos. de les lletres
    //Veure si es vol void o que retorni teclat
    public void modificarTeclat(int i, int j, int ii, int jj) {     // Posicions a intercanviar
        swap(i, j, ii, jj);
    }

    //Veure una millor manera
    private void swap(int i, int j, int ii, int jj) {
        char aux = teclat[i][j];
        teclat[i][j] = teclat[ii][jj];
        teclat[ii][jj] = aux;
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

    public char[][] getTeclat() {
        return teclat;
    }
 }

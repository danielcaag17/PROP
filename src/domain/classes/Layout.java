package src.domain.classes;

public class Layout {
    /** Atributs */ 
    /** id de la instància */
    private int id;
    /** Indica la mida del layout, número de posicions disponibles */
    private int size; 
    /** Indica la distància entre les "mida" posicions */
    private double[][] distancies;
    /** Indica per cada id les seves coordenades al layout */
    private Pair<Integer, Integer>[] coordenades;
    /** Indica la distribució de les tecles, a cada parell de coordenades (i,j) se li assigna un id */
    private int[][] distribucio; // he pensat a cada posició guardar quina és la id que li pertoca per poder trobar-la a partir de les pos

    /* Exemple d'ús: 
    Volem les coordenades per id=3 --> (coordenades[id].first, coordenades[id].second)
    Si volem accedir a la tecla amb id=3 --> distribucio[coordenades[id].first][coordenades[id].second]
    Si volem saber la distància entre dues tecles amb id=3 i id=5 --> distancies[3][5]
    --- RECOMANACIÓ: treballar amb ids menys quan s'han d'omplir tecles.
    */

    /** Constructora */
    public Layout(int i, int m) {
        id = i;
        size = m;
        inicialitzarDistribucio();
        omplirCoordenades();
        calcularDistancies();
    }

    /** Metòdes */

    /** Inicialitzar el nombre de columnes i files de l'atribut distribucio */
    private void inicialitzarDistribucio() {

    }

    /** Relacionar id de tecla (a menor id més prioritat al QAP) amb les seves coordenades */
    private void omplirCoordenades() {

    }

    /** Càlcul de distàncies Manhattan */
    private void calcularDistancies() {

    }

    /**
     * Get instance id
     * @return Retorna el id de la instància Layout
     */
    public int getId() {
        return id;
    }

    /**
     * Get instance size
     * @return Retorna el nombre de tecles que té la instància Layout
     */
    public int getSize() {
        return size;
    }

    /**
     * Get instance distancies
     * @return Retorna les distàncies entre posicions
     */
    public double[][] getDistancies() {
        return distancies;
    }

    /**
     * Get instance coordenades
     * @return Retorna les coordenades de cada id de tecla/posició
     */
    public Pair<Integer, Integer>[] getCoordenades() {
        return coordenades;
    }

    /**
     * Get instance distribucio
     * @return Retorna la distribució de les tecles/posicions
     */
    public int[][] getDistribucio() {
        return distribucio;
    }
}

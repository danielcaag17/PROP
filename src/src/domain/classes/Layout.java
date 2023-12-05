package src.domain.classes;

import java.util.*;
import src.exceptions.CoordenadaIncorrecta;

public class Layout {
    /** Atributs */
    /** Indica la mida del layout, número de posicions disponibles. Aquest paràmetre és la clau primària. */
    private int mida; 
    private int[] mov_x = new int[] {0, 1, 1, 1, 0,-1,-1,-1};
    private int[] mov_y = new int[] {1, 1, 0,-1,-1,-1, 0, 1};
    /** Indica la distància entre les "mida" posicions */
    private double[][] distancies;
    /** Indica per cada id les seves coordenades al layout */
    private List<Pair<Integer, Integer>> coordenades;
    /** Indica la distribució de les tecles, a cada parell de coordenades (i,j) se li assigna un id */
    private int[][] distribucio; // he pensat a cada posició guardar quina és la id que li pertoca per poder trobar-la a partir de les pos

    private int ncol;
    private int nfil = 3; // 3 files a la matriu distribució ens dóna una relació 3:1 per a totes les mides entre 12 i 48.


    /* Exemple d'ús: 
    Volem les coordenades per id=3 --> (coordenades[id].first, coordenades[id].second)
    Si volem accedir a la tecla amb id=3 --> distribucio[coordenades[id].first][coordenades[id].second]
    Si volem saber la distància entre dues tecles amb id=3 i id=5 --> distancies[3][5]
    --- RECOMANACIÓ: treballar amb ids menys quan s'han d'omplir tecles.
    */

    /** Constructora */
    public Layout(int m) {
        mida = m;
        distancies = new double[mida][mida];
        coordenades = new ArrayList<>(mida);
        inicialitzarDistribucio();
        omplirCoordenades();
        calcularDistancies();
    }

    /** Metòdes */

    /** Calcula la part entera inferior quan b és cert i la part entera superior quan és fals */
    private int round(double d, boolean b) {
        if (b) {
            return (int)d;
        }
        else {
            return 1+(int)d;
        }
    }

    /** Inicialitzar el nombre de columnes i files de l'atribut distribucio */
    private void inicialitzarDistribucio() {
        double x = (double)mida/3;
        if (mida%3 != 0) { ncol = round(x, false); }
        else { ncol = round(x, true); }
        if (ncol < 3) ncol = 3; // Per evitar segmentation fault al omplir el cercle inicial

        distribucio = new int[nfil][ncol]; // creem una distribució de les mesures buscades
        for (int i = 0; i<nfil; i++) {
            Arrays.fill(distribucio[i], -1);
        }
    }

    /** Relacionar id de tecla (a menor id més prioritat al QAP) amb les seves coordenades */
    private void omplirCoordenades() {
        Pair<Integer, Integer> mig = new Pair<Integer,Integer>(nfil/2, ncol/2);
        if (ncol % 2 == 0) mig.second -= 1;
        int dir = 1;
        int offset = -1;
        int id = 1;
        distribucio[mig.first][mig.second] = 0;
        coordenades.add(0, mig);
        for (int k = 1; k<ncol*nfil; k++) {
            Pair<Integer, Integer> tecla = new Pair<Integer,Integer>();
            if (k<=8) {
                tecla.first = mig.first+mov_x[k-1];
                tecla.second = mig.second+mov_y[k-1];
                if (id == mida) {break;} // Surt si el cercle inicial ha de ser menor a 8, serveix per mides inferiors a 9
            }
            else {
                int p = k % nfil; // canviar nom a variable p
                
                if (p == 0) {
                    // Correccions dels offsets i la direcció
                    offset -= 2*offset; // intercanvia el signe del valor del offset
                    if (offset>0) offset++; // si offset és positiu, increm.
                    dir *= -1; // intercanviem la direcció de la coordenada first
                }
                // Sembla que es repeteixi codi però així s'evita que el codi s'executi més vegades de les que hauria en certs casos.
                if (p == 1 && mida%nfil == 1 && k > (nfil*ncol - 6)) {
                    if (offset > 0) tecla.first = mig.first-1;
                    else tecla.first = mig.first+1;
                    k++;
                }
                else if (p == 1 && mida%nfil == 2 && k > (nfil*ncol - 3)) {
                    if (offset > 0) tecla.first = mig.first-1;
                    else tecla.first = mig.first+1;
                    k++;
                }
                else {
                    tecla.first = mig.first+dir*(p-1);  
                    // Estem recorrent columnes posteriors al cercle que envolta el centre
                    // ho volem fer equitativament per ambdues bandes i en sentits contraris
                    // dir ens ajuda al sentit contrari, 
                    // p-1 corregeix p per a que faci d'offset per al eix first
                }
                tecla.second = mig.second+offset;

                // Surt si la primera columna (que no formi part del cercle central) no fa falta que s'ompli més
                // Serveix per mides entre 9 i 12 (excloses)
                if (id == mida && ncol == 4) {break;}
            }

            coordenades.add(id, tecla);
            distribucio[tecla.first][tecla.second] = id;
            id++;
        }
    }

    /** Càlcul de la distància Manhattan entre dos punts */
    private int distanciaManhattan(Pair<Integer,Integer> c1, Pair<Integer, Integer> c2) {
        return Math.abs(c1.first-c2.first) + Math.abs(c1.second - c2.second);
    }

    /** Càlcul de la matriu distàncies, mitjançant la distància Manhattan */
    private void calcularDistancies() {
        for (int i = 0; i < mida; i++) {
            for (int j = i+1; j < mida; j++) {
                double dist = (double)distanciaManhattan(coordenades.get(i), coordenades.get(j));
                distancies[i][j] = dist;
                distancies[j][i] = dist;
            }
        }
    }

    /**
     * Get instance id. The Layout id is the same value as its size.
     * @return Retorna la id del Layout
     */
    public int getId() {
        return mida;
    }

    /**
     * Get instance size
     * @return Retorna el nombre de tecles que té la instància Layout
     */
    public int getSize() {
        return mida;
    }

    /**
     * Check if a coordinate of the matrix distribution has some valid id stored.
     * @param coordenada Indica una coordenada.
     * @return cert si la coordeanada introduïda és vàlida.
     */
    public boolean isCoordenadaValida(Pair<Integer,Integer> coordenada) {
        if (coordenada.first < 0 || coordenada.first > nfil) return false;
        if (coordenada.second < 0 || coordenada.second > ncol) return false;
        return distribucio[coordenada.first][coordenada.second] != -1;
    }

    /**
     * Get Id of a Pair of coordinates
     * @param coordenada Indica una coordenada vàlida de les que es volen la id
     * @return Retorna la id de les coordenades coordenada
     */
    public int getIdCoordenades(Pair<Integer,Integer> coordenada) throws CoordenadaIncorrecta {
        if (!isCoordenadaValida(coordenada)) throw new CoordenadaIncorrecta(coordenada);
        return distribucio[coordenada.first][coordenada.second];
    }

    /**
     * Get Pair of coordinates from Id
     * @param id Indica la id de la qual es volen saber les coordenades
     * @return Retorna el Pair de coordenades assignades a la id
     */
    public Pair<Integer,Integer> getCoordenadaFromId(int id) {
        return coordenades.get(id);
    }

    /**
     * Get instance coordenades
     * @return Retorna les coordenades de cada id de tecla/posició
     */
    public List<Pair<Integer, Integer>> getCoordenades() {
        return coordenades;
    }

    /**
     * Get instance distancies
     * @return Retorna les distàncies entre posicions
     */
    public double[][] getDistancies() {
        return distancies;
    }

    /**
     * Get instance distribucio filled with the id of each position, -1 if it's a empty position
     * @return Retorna la distribució de les ids en les tecles/posicions 
     */
    public int[][] getDistribucioFilled() {
        return distribucio;
    }

    /**
     * Get instance distribucio filled with the id of each position
     * @return Retorna la distribució de les ids en les tecles/posicions en format String
     */
    public String getDistribucioFilledString() {
        return distribucioFilledToString();
    }

    /**
     * Get a matrix with number of rows = nfil and number of columns = ncol
     * @return Retorna la distribució de les tecles/posicions inicialitzada amb '-'
     */
    public char[][] getDistribucio() {
        char[][] c = new char[nfil][ncol];
        for (int i = 0; i < nfil; i++) {
            Arrays.fill(c[i], '-');
        }
        return c;
    }

    // Documentar
    public int getRows() {
        return nfil;
    }

    // Documentar
    public int getColumns() {
        return ncol;
    }

    private String distribucioFilledToString() {
        String s = "";
        for(int i = 0; i < nfil; i++) {
            for(int j = 0; j < ncol; j++) {
                if (distribucio[i][j] == -1) s+="--"; // COMPROVAR QUE AIXO FUNCIONA -- VEURE ON MÉS ES POT APLICAR
                else {
                    if (distribucio[i][j] < 10) s+='0';
                    s += distribucio[i][j];
                }
                s += " ";
            }
            s+="\n";
        }
        return s;
    }

    private String distanciesToString() {
        String s = "";
        for (int i = -1; i < distancies[0].length; i++) {
            if(i < 0) s+="id   ";
            else {
                if (i<10) s+="0";
                s+=i + " ";
            }
        }
        s+="\n";
        for (int i = -1; i < distancies[0].length; i++) {
            if(i < 0) s+="     ";
            else {
                s+="-- ";
            }
        }
        s+="\n";
        for(int i = 0; i < distancies.length; i++) {
            for(int j = -1; j < distancies[i].length; j++) {
                if (j < 0) {
                    if (i<10) s+="0";
                    s+=i + " | ";
                }
                else {
                    if (distancies[i][j] < 10) s+='0';
                    s += (int)distancies[i][j] + " ";
                }
            }
            s+="\n";
        }
        return s;
    }

    @Override
    public String toString() {
        String result = "Layout de mida: " + mida + "\n";
        result += "- Distribució de Ids: \n" + distribucioFilledToString();
        result += "- Distàncies entre Ids: \n" + distanciesToString();
        return result;
    }
}

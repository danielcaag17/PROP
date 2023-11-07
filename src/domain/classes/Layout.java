package src.domain.classes;

public class Layout {
    private int id;
    private int mida; // Indica la mida del layout, número de posicions disponibles
    private int[][] distanciaPosicions; // Indica la distància entre les "mida" posicions

    // Creadora
    public Layout(int i, int m) {
        id = i;
        mida = m;
    }

    // atributs necessaris

    // metodes necessaris
    public int getId() {
        return id;
    }

    public int getMida() {
        return mida;
    }

    public int[][] getDistancies() {
        return distanciaPosicions;
    }
}

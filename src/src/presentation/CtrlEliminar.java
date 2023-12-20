package src.presentation;

import javax.swing.JPopupMenu;

import src.exceptions.*;

public class CtrlEliminar {
    String tipus = "";
    String clau = "";
    CtrlPresentacio cp;
    JPopupMenu popupEliminarMenu;

    /**
     * Constructora per defecte
     *
     * @param tipus indica el tipus d'instància que es vol eliminar
     * @param clau indica la clau de l'instància que es vol eliminar
     */
    public CtrlEliminar (String tipus, String clau, String pantalla) {
        this.tipus = tipus;
        this.clau = clau;
        cp = CtrlPresentacio.getInstance();
        initPopup();
    }

    /**
     * Inicialitza el popup per a eliminar
     */
    public void initPopup() {
        popupEliminarMenu = new JPopupMenu();
        popupEliminarMenu.add("Eliminar");
        popupEliminarMenu.add("Cancelar");
    }

    /**
     * Elimina l'instància seleccionada
     */
    public void eliminar () {
        switch (tipus) {
            case "Teclat":
                try {
                    cp.esborrarTeclat(clau);
                }
                catch (Excepcions e) {
                    cp.Excepcio(e.getTipus(), e.getMessage());;
                }
                break;
            case "Alfabet":
                try {
                    cp.esborrarAlfabet(clau);
                }
                catch (Excepcions e) {
                    cp.Excepcio(e.getTipus(), e.getMessage());;
                }
                break;
            case "Layout":
                try {
                    cp.esborrarLayout(Integer.valueOf(clau));
                }
                catch (Excepcions e) {
                    cp.Excepcio(e.getTipus(), e.getMessage());;
                }
                break;
        }
    }    
}

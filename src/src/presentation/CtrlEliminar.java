package src.presentation;

import javax.swing.*;

import java.awt.*;

import src.exceptions.*;

public class CtrlEliminar {
    String tipus = "";
    String clau = "";
    JFrame pantalla;
    String pantallaRetorn;
    CtrlPresentacio cp;
    JDialog popupEliminarMenu;
    JButton okButton;
    JButton cancelButton;

    /**
     * Constructora per defecte
     *
     * @param tipus indica el tipus d'instància que es vol eliminar
     * @param clau indica la clau de l'instància que es vol eliminar
     */
    public CtrlEliminar (String tipus, String clau, JFrame pantalla, String pantallaRetorn) {
        this.tipus = tipus;
        this.clau = clau;
        this.pantalla = pantalla;
        this.pantallaRetorn = pantallaRetorn;
        cp = CtrlPresentacio.getInstance();
        initPopup();
    }

    /**
     * Inicialitza el popup per a eliminar
     */
    public void initPopup() {
        popupEliminarMenu = new JDialog(pantalla, "Eliminar", true);
        popupEliminarMenu.setLayout(new BorderLayout());
        okButton = Utils.Button("Confirmar", null);
        cancelButton = Utils.Button("Cancel·lar", null);
        
        JPanel buttons = new JPanel();
        popupEliminarMenu.add(new JLabel("Estàs segur que vols eliminar aquesta instància?"), BorderLayout.CENTER);
        buttons.add(okButton);
        buttons.add(cancelButton);
        buttonsListener();
        popupEliminarMenu.add(buttons, BorderLayout.SOUTH);

        popupEliminarMenu.setSize(new Dimension(420,200));
        popupEliminarMenu.setLocationRelativeTo(pantalla);
        popupEliminarMenu.setVisible(true);
        popupEliminarMenu.setResizable(false);
    }

    public void buttonsListener() {
        okButton.addActionListener(e -> {
            eliminar();
            apagaPantalla();
        });
        cancelButton.addActionListener(e -> {
            apagaPantalla();
        });
    }

    public void apagaPantalla() {
        pantalla.dispose();
        Utils.canviPantalla(popupEliminarMenu, pantallaRetorn);
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
                    cp.Excepcio(pantalla, e.getTipus(), e.getMessage());;
                }
                catch(Exception e) {
                    cp.Excepcio(pantalla, "Error: ", e.getMessage());;
                }
                break;
            case "Alfabet":
                try {
                    cp.esborrarAlfabet(clau);
                }
                catch (Excepcions e) {
                    cp.Excepcio(pantalla, e.getTipus(), e.getMessage());;
                }
                catch(Exception e) {
                    cp.Excepcio(pantalla, "Error: ", e.getMessage());;
                }
                break;
            case "Layout":
                try {
                    cp.esborrarLayout(Integer.valueOf(clau));
                }
                catch (Excepcions e) {
                    cp.Excepcio(pantalla, e.getTipus(), e.getMessage());;
                }
                catch(Exception e) {
                    cp.Excepcio(pantalla, "Error: ", e.getMessage());;
                }
                break;
        }
        System.out.println(tipus + " " + clau + " -> eliminat");
    }    
}

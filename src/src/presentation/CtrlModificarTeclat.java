package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.exceptions.TeclatNoExisteix;

public class CtrlModificarTeclat {
    private CtrlPresentacio ctrlPresentacio;                // instancia unica de ctrlPresentacio
    private JPanel PNorth, PCenter, PSouth;                 // panels per organitzar el layout de la vista
    private JFrame vista;                                   // vista on es mostra tota la informació de la pantalla
    private String teclat;                                  // nom del teclat a modificar

    public CtrlModificarTeclat(String elementAMostrar) {
        teclat = elementAMostrar;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initPanels();
        addElementsFrame();
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(Utils.initLabel("Modificar Teclat", "title"));

        JPanel PNom = new JPanel();
        PNom.add(Utils.initLabel(teclat, "text"));

        JPanel PTeclat = panelTeclat(teclat);

        JPanel PButtons = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), null);
        PButtons.add(Utils.Button("OK", null));
        PButtons.add(Utils.Button("?", null));

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PNom, BorderLayout.NORTH);
        PCenter.add(PTeclat, BorderLayout.CENTER);
        PCenter.add(PButtons, BorderLayout.SOUTH);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(Utils.Button(null, "cross"));
        PSouth.add(Utils.Button(null, "check"));
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("ModificarTeclat");
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private int checkSize(char[] row) {
        int size = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] != '-') size++;
        }
        return size;
    }

    private JPanel panelTeclat(String teclat) {
        JPanel panel = new JPanel();
        try {
            char[][] distribucio = ctrlPresentacio.getDistribucio(teclat);
            panel = Utils.JPanel(new GridLayout(distribucio.length, 1), null);
            for (int i = 0; i < distribucio.length; i++) {
                int size = checkSize(distribucio[i]);
                JPanel PRow = Utils.JPanel(new GridLayout(1, size), null);
                for (int j = 0; j < distribucio[i].length; j++) {
                    String character = String.valueOf(distribucio[i][j]);
                    if (! character.equals("-")) PRow.add(Utils.Button(character, null));
                }
                panel.add(PRow);
            }
        } catch (TeclatNoExisteix e) {
            ctrlPresentacio.Excepcio(vista, e.getTipus(), "Teclat " + teclat + " no exiteix");
        }
        return panel;
    }
}
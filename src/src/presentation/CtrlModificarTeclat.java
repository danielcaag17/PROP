package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.exceptions.Excepcions;
import src.exceptions.TeclatNoExisteix;

public class CtrlModificarTeclat {
    private CtrlPresentacio ctrlPresentacio;                // instancia unica de ctrlPresentacio
    private JPanel PNorth, PCenter, PSouth;                 // panels per organitzar el layout de la vista
    private JButton buttonOK, buttonHelp, cancelar, confirmar;
    private JFrame vista;                                   // vista on es mostra tota la informació de la pantalla
    private String teclat;                                  // nom del teclat a modificar
    private Character primera, segona;                      // primera i segona lletra per a fer una modificació
    private JButton buttonPrimer, buttonSegon;              // els dos botons seleccionats
    private Map<Character, Character> canvis;

    public CtrlModificarTeclat(String elementAMostrar) {
        teclat = elementAMostrar;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        canvis = new HashMap<>();
        primera = segona = Character.MIN_VALUE;             // character buit
        buttonPrimer = buttonSegon = null;

        buttonOK = Utils.Button("OK", null);
        buttonOK.addActionListener(e -> parellaOK());

        buttonHelp = Utils.Button("?", null);
        buttonHelp.addActionListener(e -> help());

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> cancelar());

        confirmar = Utils.Button(null, "check");
        confirmar.addActionListener(e -> confirmar());
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(Utils.initLabel("Modificar Teclat", "title"));

        JPanel PNom = new JPanel();
        PNom.add(Utils.initLabel(teclat, "text"));

        JPanel PTeclat = panelTeclat(teclat);

        JPanel PButtons = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), null);
        PButtons.add(buttonOK);
        PButtons.add(buttonHelp);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PNom, BorderLayout.NORTH);
        PCenter.add(PTeclat, BorderLayout.CENTER);
        PCenter.add(PButtons, BorderLayout.SOUTH);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(cancelar);
        PSouth.add(confirmar);
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
                    if (! character.equals("-")) {
                        JButton button = Utils.Button(character, null);
                        char c = distribucio[i][j];
                        button.addActionListener(e -> lletraSeleccionada(c, button));
                        PRow.add(button);
                    }
                }
                panel.add(PRow);
            }
        } catch (TeclatNoExisteix e) {
            ctrlPresentacio.Excepcio(vista, e.getTipus(), "Teclat " + teclat + " no exiteix");
        }
        return panel;
    }

    private void parellaOK() {
        if (primera != Character.MIN_VALUE && segona != Character.MIN_VALUE) {
            canvis.put(primera, segona);
            ctrlPresentacio.Excepcio(vista, "OK", "Canvi modificat correctament");
            String aux = buttonPrimer.getText();
            buttonPrimer.setText(buttonSegon.getText());
            buttonSegon.setText(aux);

            primera = segona = Character.MIN_VALUE;
            buttonPrimer.setEnabled(true);
            buttonSegon.setEnabled(true);
            buttonPrimer = buttonSegon = null;
        }
        else if (primera != Character.MIN_VALUE && segona == Character.MIN_VALUE) {
            ctrlPresentacio.Excepcio(vista, "FaltaUnaLletra", "Falta seleccionar una lletra més per fer un canvi");
        }
        else {
            ctrlPresentacio.Excepcio(vista, "FaltaSeleccionarDuesLletres", "Falta seleccionar dues lletres");
        }
    }

    private void help() {
        String msg = "Has de seleccionar dues lletres a intercanviar i clicar OK, un cop acabades totes les modificacions -> confirmar";
        ctrlPresentacio.Excepcio(vista, "Ajuda", msg);
    }

    private void cancelar() {
        Utils.canviPantalla(vista, "LlistaTeclats");
    }

    private void confirmar() {
        try {
            ctrlPresentacio.modificarTeclat(teclat, canvis);
        } catch (Excepcions | IOException e) {
            String msg = "";
            String tipus = "";
            if (e instanceof FileNotFoundException) {
                msg = e.getMessage();
                tipus = "FileNotFoundException";
            }
            else if (e instanceof Excepcions) {
                msg = e.getMessage();
                tipus = "FileNotFoundException";
            }
            ctrlPresentacio.Excepcio(vista, tipus, msg);
        }
        Utils.canviPantalla(vista, "LlistaTeclats");
    }

    private void lletraSeleccionada(char c, JButton button) {
        if (segona == Character.MIN_VALUE) {
            if (primera == Character.MIN_VALUE) primera = c;
            else segona = c;
            
            button.setEnabled(false);
            if (buttonPrimer == null) buttonPrimer = button;
            else buttonSegon = button;
        }
        else ctrlPresentacio.Excepcio(vista, "JaDuesLletresSeleccionades", "Ja has seleccioant dues lletres");
    }
}
package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.domain.classes.Pair;

import src.exceptions.Excepcions;
import src.exceptions.TeclatNoExisteix;

public class CtrlModificarTeclat {
    private CtrlPresentacio ctrlPresentacio;                    // instancia unica de ctrlPresentacio
    private JPanel PNorth, PCenter, PSouth;                     // panels per organitzar el layout de la vista
    private JButton buttonOK, buttonHelp, cancelar, confirmar;  // botons de la pantalla
    private JFrame vista;                                       // vista on es mostra tota la informació de la pantalla
    private String teclat;                                      // nom del teclat a modificar
    private Character primera, segona;                          // primera i segona lletra per a fer una modificació
    private JButton buttonPrimer, buttonSegon;                  // els dos botons seleccionats per a fer l'intercanvi
    private ArrayList<Pair<Character, Character>> canvis;       // map que guarda els canvis fets en el teclat

    public CtrlModificarTeclat(String elementAMostrar) {
        teclat = elementAMostrar;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        canvis = new ArrayList<Pair<Character, Character>>();
        primera = segona = Character.MIN_VALUE;                 // character buit (' ')
        buttonPrimer = buttonSegon = null;

        buttonOK = Utils.Button("Afegir canvi", null);
        buttonOK.addActionListener(e -> parellaOK());

        buttonHelp = Utils.Button("?", null);
        buttonHelp.addActionListener(e -> help());

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> cancelar());

        confirmar = Utils.Button("Confirmar canvis",null);
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

    /**
     * Pre:
     * Post: es retorna el nombre de tecles per aquella fila
     * 
     * @param row array de char que conté els caràcters d'una fila del teclat
     * 
     * @return el nombre de lletres per aquella fila
     */
    private int checkSize(char[] row) {
        int size = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] != '-') size++;
        }
        return size;
    }

    /**
     * Pre:
     * Post: es retorna un panel amb la forma del teclat dessitjat
     * 
     * @param teclat nom del teclat a mostrar
     * 
     * @return un panel amb la forma del teclat dessitjat
     */
    private JPanel panelTeclat(String teclat) {
        JPanel panel = new JPanel();
        try {
            char[][] distribucio = ctrlPresentacio.getDistribucio(teclat);
            panel = Utils.JPanel(new GridLayout(distribucio.length, 1), null);   // Crear un panel amb gridLayout amb tantes files com el teclat té
            for (int i = 0; i < distribucio.length; i++) {
                int size = checkSize(distribucio[i]);                                               // Calcular el nombre de lletres per aquella fila
                JPanel PRow = Utils.JPanel(new GridLayout(1, size), null);       // Crear un panel amb gridLayout amb tantes columnes com lletres per la fila
                for (int j = 0; j < distribucio[i].length; j++) {
                    String character = String.valueOf(distribucio[i][j]);
                    if (! character.equals("-")) {                                          // Si el caràcter és vàlid
                        JButton button = Utils.Button(character, null);
                        button.addActionListener(e -> lletraSeleccionada(button));                  // Marcar que s'ha seleccionat la lletra
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

    // Pre: s'ha clicat el botó 'OK'
    // Post: s'ha afegit al map de canvis les parelles de lletra i s'han intercanviat
    private void parellaOK() {
        // Dues lletres seleccionades
        if (primera != Character.MIN_VALUE && segona != Character.MIN_VALUE) {
            Pair<Character, Character> parella = new Pair<Character, Character>(primera, segona);
            canvis.add(parella);                                        // Map de tots els canvis fets

            String aux = buttonPrimer.getText();                        // Swap dels botons
            buttonPrimer.setText(buttonSegon.getText());
            buttonSegon.setText(aux);
            ctrlPresentacio.Excepcio(vista, "OK", "Canvi modificat correctament");

            primera = segona = Character.MIN_VALUE;                     // Retornar les variables a l'estat inicial
            buttonPrimer.setEnabled(true);
            buttonSegon.setEnabled(true);
            buttonPrimer = buttonSegon = null;
        }
        // Només una lletra seleccionada
        else if (primera != Character.MIN_VALUE && segona == Character.MIN_VALUE) {
            ctrlPresentacio.Excepcio(vista, "FaltaUnaLletra", "Falta seleccionar una lletra més per fer un canvi");
        }
        // No hi ha cap lletra seleccionada
        else {
            ctrlPresentacio.Excepcio(vista, "FaltaSeleccionarDuesLletres", "Falta seleccionar dues lletres");
        }
    }

    // Mostra pantalla d'ajuda
    private void help() {
        String msg = "Has de seleccionar dues lletres a intercanviar i clicar OK, un cop acabades totes les modificacions → confirmar";
        ctrlPresentacio.Excepcio(vista, "Ajuda", msg);
    }

    // Es cancela la modifiació i es mostra llista teclats
    private void cancelar() {
        Utils.canviPantalla(vista, "LlistaTeclats");
    }

    // Es confirmen tots els canvis fets
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
        // Retornar a la llista de teclats
        Utils.canviPantalla(vista, "LlistaTeclats");
    }

    /**
     * Pre:
     * Post: es marca quina lletra s'ha seleccionat
     * 
     * @param button botó (lletra) seleccionat
     */
    private void lletraSeleccionada(JButton button) {
        // Una o cap lletra seleccionada
        if (segona == Character.MIN_VALUE) {
            // Cap lletra seleccionada
            if (primera == Character.MIN_VALUE) primera = button.getText().charAt(0);
            // Una lletra seleccionada
            else segona = button.getText().charAt(0);
            
            button.setEnabled(false);
            // Cap lletra seleccionada
            if (buttonPrimer == null) buttonPrimer = button;
            // Una lletra seleccionada
            else buttonSegon = button;
        }
        // Ja hi ha dues lletres
        else ctrlPresentacio.Excepcio(vista, "JaDuesLletresSeleccionades", "Ja has seleccionat dues lletres");
    }
}
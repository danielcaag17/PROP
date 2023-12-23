package src.presentation;

import java.awt.*;
import javax.swing.*;

import src.exceptions.*;

public class CtrlAfegirLayout {
    private CtrlPresentacio cp;                         // instancia unica de ctrlPresentacio
    private JLabel title, labelIndicaIdLayout;          // títol de la pantalla + label informativa per indicar el nom del Layout
    private JSpinner inputIdLayout;                     // input pel nom del Layout
    private Integer idLayout;                           // id del Layout escrit per l'usuari
    private JButton cancelar, confirmar;                // butons per cancelar o confirmar
    private JFrame vista;                               // pantalla on es mostren tots els elements
    private JPanel PNorth, PCenter, PSouth;

    public CtrlAfegirLayout() {
        cp = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        title = Utils.initLabel("Afegir layout", "title");
        idLayout = 22;
        labelIndicaIdLayout = Utils.initLabel("Indica la mida del layout: ", "text");

        SpinnerModel sm = new SpinnerNumberModel(22, 1, 100, 1);
        inputIdLayout = new JSpinner(sm);
        inputIdLayout.setPreferredSize(new Dimension(200, 50));
        inputIdLayout.setFont(Utils.getFontText());
        inputIdLayout.setValue(idLayout);
        inputIdLayout.addChangeListener(e -> idLayout = (Integer) inputIdLayout.getValue());

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> cancelar());

        confirmar = Utils.Button("Confirmar", null);
        confirmar.addActionListener(e -> confirmar());
    }

    private void cancelar() {
        Utils.canviPantalla(vista, "LlistaLayouts");
    }

    private void confirmar() {
        try {
            cp.afegirLayout(idLayout);
            System.out.println("Layout mida " + idLayout + " -> creat");
            cp.Excepcio(vista, "Layout afegit", "Layout afegit correctament");
        }
        catch (LayoutJaExisteix e) {
            cp.Excepcio(vista, e.getTipus(), e.getMessage());
        }
        catch (Exception e) {
            // Excepcions que no passaran gairebé mai
            cp.Excepcio(vista, "Error: " + e.getClass().getSimpleName(), e.getMessage());
        }
        Utils.canviPantalla(vista, "LlistaLayouts");
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PCenter = Utils.JPanel(new FlowLayout(), null);
        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title, BorderLayout.CENTER);
        PCenter.add(labelIndicaIdLayout, BorderLayout.CENTER);
        PCenter.add(inputIdLayout, BorderLayout.CENTER);
        PSouth.add(cancelar, BorderLayout.CENTER);
        PSouth.add(confirmar, BorderLayout.CENTER);
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("AfegirLayout");
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

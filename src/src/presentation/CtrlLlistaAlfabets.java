package src.presentation;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlLlistaAlfabets {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol;
    private JButton afegir, back; 
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista;

    public CtrlLlistaAlfabets() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("Llista Alfabets", "title");

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {

    }
}

package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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

        back = Utils.backButton();
        back.addActionListener(e -> back());
        afegir = new JButton();

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = new JPanel();
        PSouth.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);

        PAfegir = new JPanel();
        PAfegir.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir);

        PLlista = new JPanel();
        PLlista.setLayout(new GridLayout());
        // la mida és tota la restant de la pantall
        

        PCenter = new JPanel();
        PCenter.setLayout(new BorderLayout());
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(PLlista, BorderLayout.SOUTH);

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private void back() {
        // Eliminar la vista
        ctrlPresentacio.canviVista("LlistaTeclats");
    }
}

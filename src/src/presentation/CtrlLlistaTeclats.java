package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlLlistaTeclats {
    private JLabel títol;
    private JButton afegir, llistaAlfabets, llistaLayouts, exit; 
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista;

    public CtrlLlistaTeclats() {
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("Llista Teclats", "title");

        afegir = Utils.addButton();
        afegir.addActionListener(e -> Utils.canviPantalla(vista, "AfegirTeclat"));

        exit = new JButton();
        exit.setPreferredSize(new Dimension(100, 50));
        exit.setText("Exit");
        exit.setFont(Utils.getFontText());
        exit.setFocusable(false);
        exit.addActionListener(e -> exit());

        llistaAlfabets = new JButton();
        llistaAlfabets.setText("Alfabets");
        llistaAlfabets.setFocusable(false);
        llistaAlfabets.setSize(new Dimension(0, 100));
        llistaAlfabets.setFont(Utils.getFontText());
        llistaAlfabets.addActionListener(e -> Utils.canviPantalla(vista,"LlistaAlfabets"));

        llistaLayouts = new JButton();
        llistaLayouts.setText("Layouts");
        llistaLayouts.setFocusable(false);
        llistaLayouts.setSize(new Dimension(0, 100));
        llistaLayouts.setFont(Utils.getFontText());
        llistaLayouts.addActionListener(e -> Utils.canviPantalla(vista,"LlistaLayouts"));

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = new JPanel();
        PSouth.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        // PSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        PSouth.setLayout(new BorderLayout());
        PSouth.add(exit, BorderLayout.CENTER);
        PSouth.add(llistaAlfabets, BorderLayout.WEST);
        PSouth.add(llistaLayouts, BorderLayout.EAST);

        PAfegir = new JPanel();
        PAfegir.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir);

        PLlista = new JPanel();
        PLlista.setLayout(new GridLayout()); // o boxLayout
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

    private void exit() {
        // Sortir i guardar les dades
        vista.dispose();
    }
}


package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlLlistaTeclats {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol;
    private JButton afegir, llistaAlfabets, llistaLayouts, exit; 
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista;

    public CtrlLlistaTeclats() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("Llista Teclats", "title");

        afegir = Utils.Button("+", null);
        afegir.addActionListener(e -> addTeclat());

        exit = Utils.Button("Exit", null);
        exit.addActionListener(e -> exit());

        llistaAlfabets = Utils.Button("Alfabets", null);
        llistaAlfabets.addActionListener(e -> Utils.canviPantalla(vista,"LlistaAlfabets"));

        llistaLayouts = Utils.Button("Layouts", null);
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

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir);

        PLlista = Utils.JPanel(new GridLayout(), null); // o boxLayout
        // la mida és tota la restant de la pantall
        
        PCenter = Utils.JPanel(new BorderLayout(), null);
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

    private void addTeclat() {
        String[] s = ctrlPresentacio.getNomAlfabets();
        if (s.length == 0) {
            // no es pot crear un teclat perque no hi ha cap alfabet
        }
        else Utils.canviPantalla(vista, "AfegirTeclat");
    }
}


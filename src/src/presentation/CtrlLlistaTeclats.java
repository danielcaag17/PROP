package src.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlLlistaTeclats {
private CtrlPresentacio ctrlPresentacio;
    private JLabel títol;
    private JButton afegir, llistaAlfabets, llistaLayouts; 
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista;

    public CtrlLlistaTeclats() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("Llista Teclats", "title");

        afegir = new JButton();
        afegir.setSize(new Dimension(100, 50));

        llistaAlfabets = new JButton();
        llistaAlfabets.setText("Alfabets");
        llistaAlfabets.setFocusable(false);
        llistaAlfabets.setSize(new Dimension(100, 50));
        llistaAlfabets.setFont(Utils.getFontText());
        llistaAlfabets.addActionListener(e -> llistaAlfabets());

        llistaLayouts = new JButton();
        llistaLayouts.setText("Layouts");
        llistaLayouts.setFocusable(false);
        llistaLayouts.setSize(new Dimension(100, 50));
        llistaLayouts.setFont(Utils.getFontText());
        llistaLayouts.addActionListener(e -> llistaLayouts());

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = new JPanel();
        PSouth.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.setLayout(new BorderLayout());
        PSouth.setBackground(Color.BLACK);
        PSouth.add(llistaAlfabets,  BorderLayout.WEST);
        PSouth.add(llistaLayouts, BorderLayout.EAST);

        PAfegir = new JPanel();
        PAfegir.setBackground(Color.red);
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

    private void llistaAlfabets() {
        // Eliminar la vista
        ctrlPresentacio.canviVista("LlistaAlfabets");
    }

    private void llistaLayouts() {
        // Eliminar la vista
        ctrlPresentacio.canviVista("LlistaLayouts");
    }
}


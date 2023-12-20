package src.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CtrlLlistaTeclats {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol;
    private JButton afegir, llistaAlfabets, llistaLayouts, exit; 
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista;

    public CtrlLlistaTeclats() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        initPanels();
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
    }

    private void initPanels() {
        PLlista = new JPanel();
        PLlista.setLayout(new BoxLayout(PLlista, BoxLayout.Y_AXIS));    //panel que no es pot instanciar amb Utils
        String[] teclats = ctrlPresentacio.getNomTeclats();
        for (int i = 0; i < teclats.length; i++) {
            String nt = teclats[i];
            JLabel label = Utils.initLabel(nt, "text");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton edit = Utils.Button(null, "edit");
            edit.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "ModificarTeclat", nt));
            JButton delete = Utils.Button(null, "delete");
            delete.addActionListener(e -> ctrlPresentacio.elimina("Teclat", nt, vista, "LlistaTeclats"));

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.add(label);
            panel.add(edit);
            panel.add(delete);

            PLlista.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(PLlista);
        scrollPane.setBorder(null);     // No se que queda millor

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
        
        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("LlistaTeclats");
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
            ctrlPresentacio.Excepcio(vista, "NoEsPotCrearTeclat", "No hi ha cap alfabet creat per a generar un teclat");
        }
        else Utils.canviPantalla(vista, "AfegirTeclat");
    }
}


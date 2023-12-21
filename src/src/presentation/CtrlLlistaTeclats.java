package src.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
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
    private JPanel PTítol, PSouth, PCenter, PAfegir, PLlista, PEast, PWest;

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
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Utils.canviPantallaElementMostrar(vista, "MostrarTeclat", nt);
                }
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
            });
            label.setBackground(Utils.getBackgroundColorElement());
            JButton edit = Utils.Button(null, "edit");
            edit.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "ModificarTeclat", nt));
            JButton delete = Utils.Button(null, "delete");
            delete.addActionListener(e -> ctrlPresentacio.elimina("Teclat", nt, vista, "LlistaTeclats"));

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
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

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(llistaAlfabets);
        PSouth.add(exit);
        PSouth.add(llistaLayouts);

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir, BorderLayout.CENTER);
        
        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);

        PEast = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
        PWest = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("LlistaTeclats");
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
        vista.add(PEast, BorderLayout.EAST);
        vista.add(PWest, BorderLayout.WEST);
    }

    private void exit() {
        // Sortir i guardar les dades
        // ctrlPresentacio.guarda();
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


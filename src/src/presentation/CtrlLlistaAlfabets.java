package src.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CtrlLlistaAlfabets {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel title;
    private JButton afegir, back; 
    private JFrame vista;
    private JPanel PNorth, PAfegir, PCenter, PSouth, PEast, PWest;
    private Box PLlista;

    public CtrlLlistaAlfabets() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        title = Utils.initLabel("Llista Alfabets", "title");

        afegir = Utils.Button("+", null);
        afegir.addActionListener(e -> Utils.canviPantalla(vista, "AfegirAlfabet"));

        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title);

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir, BorderLayout.CENTER);

        PLlista = Box.createVerticalBox();
        //PLlista.setLayout(new BoxLayout(PLlista, BoxLayout.Y_AXIS));    //panel que no es pot instanciar amb Utils
        String[] alfabets = ctrlPresentacio.getNomAlfabets();
        for (int i = 0; i < alfabets.length; i++) {
            String na = alfabets[i];
            JLabel label = Utils.initLabel(na, "element");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Utils.canviPantallaElementMostrar(vista, "MostrarAlfabet", na);
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
            JButton delete = Utils.Button(null, "delete");
            delete.addActionListener(e -> ctrlPresentacio.elimina("Alfabet", na, vista, "LlistaAlfabets"));

            JPanel panel = Utils.JPanel(new BorderLayout(), null);
            panel.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
            
            JPanel labelPanel = Utils.JPanel(new BorderLayout(), null);
            labelPanel.add(new JPanel(), BorderLayout.WEST);
            labelPanel.add(label, BorderLayout.CENTER);
            JPanel buttonsPanel = Utils.JPanel(new BorderLayout(), null);
            buttonsPanel.add(delete, BorderLayout.CENTER);

            panel.add(labelPanel, BorderLayout.CENTER);
            panel.add(buttonsPanel, BorderLayout.EAST);

            PLlista.add(panel);
        }
        JPanel layoutPLlista = Utils.JPanel(new BorderLayout(), null);
        layoutPLlista.add(PLlista, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(layoutPLlista);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);     // No se que queda millor

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);

        PEast = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
        PWest = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("LlistaAlfabets");
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
        vista.add(PEast, BorderLayout.EAST);
        vista.add(PWest, BorderLayout.WEST);
    }
}

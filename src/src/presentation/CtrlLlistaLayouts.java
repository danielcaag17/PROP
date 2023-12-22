package src.presentation;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;

public class CtrlLlistaLayouts {
    private CtrlPresentacio cp;
    private JFrame vista;
    private JLabel title;
    private JButton afegir, back;
    private JPanel PNorth, PAfegir, PLlista, PCenter, PSouth, PEast, PWest;

    public CtrlLlistaLayouts() {
        cp = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        linkFrameElements();
    }

    private void initElements() {
        title = Utils.initLabel("Llista Layouts", "title");

        afegir = Utils.Button("+", null);
        afegir.addActionListener(e -> Utils.canviPantalla(vista, "AfegirLayout"));

        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title);

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir, BorderLayout.CENTER);

        PLlista = new JPanel();
        PLlista.setLayout(new BoxLayout(PLlista, BoxLayout.Y_AXIS));    //panel que no es pot instanciar amb Utils
        Integer[] layouts = cp.getNomLayouts();
        for (int i = 0; i < layouts.length; i++) {
            Integer nl = layouts[i];
            JLabel label = Utils.initLabel("Mida: " + Integer.toString(nl), "element");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Utils.canviPantallaElementMostrar(vista, "MostrarLayout", Integer.toString(nl));
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
            //label.setBackground(Utils.getBackgroundColorElement());

            JButton delete = Utils.Button(null, "delete");
            delete.addActionListener(e -> cp.elimina("Layout", Integer.toString(nl), vista, "LlistaLayouts"));

            JPanel panel = Utils.JPanel(new BorderLayout(), null);
            panel.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
            
            JPanel labelPanel = Utils.JPanel(new BorderLayout(), null);
            labelPanel.add(new JPanel(), BorderLayout.WEST);
            labelPanel.add(label, BorderLayout.CENTER);
            JPanel buttonsPanel = Utils.JPanel(new FlowLayout(FlowLayout.CENTER), null);
            buttonsPanel.add(delete);

            panel.add(labelPanel, BorderLayout.CENTER);
            panel.add(buttonsPanel, BorderLayout.EAST);

            PLlista.add(panel);
        }
        JScrollPane scrollPane = new JScrollPane(PLlista);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);     // No se que queda millor

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.setBorder(BorderFactory.createLineBorder(Utils.getBackgroundColorElement(), 2));
        PCenter.add(scrollPane, BorderLayout.CENTER);
        PCenter.add(PAfegir, BorderLayout.NORTH);

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(), Utils.getScreenHeight()/6));
        PSouth.add(back, BorderLayout.CENTER);

        PEast = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
        PWest = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6, Utils.getScreenHeight()));
    }

    private void linkFrameElements() {
        vista = Utils.initFrame("LlistaLayouts");

        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
        vista.add(PEast, BorderLayout.EAST);
        vista.add(PWest, BorderLayout.WEST);
    }
}

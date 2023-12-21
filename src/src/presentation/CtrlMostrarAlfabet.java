package src.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CtrlMostrarAlfabet {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel title, labelNomAlfabet;
    private JButton back;
    private String nomAlfabet;                          // nom de l'alfabet escrit per l'usuari
    private JPanel PNorth, PCenter, PSouth;
    private JFrame vista;

    public CtrlMostrarAlfabet(String nomAlfabet) {
        this.nomAlfabet = nomAlfabet;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        title = Utils.initLabel("Mostrar Alfabet", "title");
        labelNomAlfabet = Utils.initLabel(nomAlfabet, "text");
        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaAlfabets"));
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title);

        JPanel PNomAlfabet = new JPanel();
        PNomAlfabet.add(labelNomAlfabet);
        JPanel PAbecedari = new JPanel();
        PAbecedari.setLayout(new BoxLayout(PAbecedari, BoxLayout.Y_AXIS));
        Map<Character, Double> characters = ctrlPresentacio.getCharacters(nomAlfabet);
        for (Character c : characters.keySet()) {
            String lletra = String.valueOf(c);
            Double freq = characters.get(c) * 100.0;
            lletra += " -> " + freq;
            JLabel label = Utils.initLabel(lletra, "text");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Utils.canviPantallaElementMostrar(vista, "MostrarAlfabet", na);
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

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.add(label);

            PAbecedari.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(PAbecedari);
        //scrollPane.setBorder(null);     // No se que queda millor

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PNomAlfabet, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);

        JButton delete = Utils.Button(null, "delete");
        delete.addActionListener(e -> ctrlPresentacio.elimina("Alfabet", nomAlfabet, vista, "LlistaAlfabets"));

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);
        PSouth.add(delete);
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("LlistaAlfabets");
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

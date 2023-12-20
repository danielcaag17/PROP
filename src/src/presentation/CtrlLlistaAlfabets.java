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

        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));
        
        afegir = Utils.Button("+", null);
        afegir.addActionListener(e -> Utils.canviPantalla(vista, "AfegirAlfabet"));

        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir);

        PLlista = new JPanel();
        PLlista.setLayout(new BoxLayout(PLlista, BoxLayout.Y_AXIS));    //panel que no es pot instanciar amb Utils
        String[] alfabets = ctrlPresentacio.getNomAlfabets();
        for (int i = 0; i < alfabets.length; i++) {
            JLabel label = Utils.initLabel(alfabets[i], "text");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton delete = Utils.Button(null, "delete");

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.add(label);
            panel.add(delete);

            PLlista.add(panel);
        }
        
        JScrollPane scrollPane = new JScrollPane(PLlista);
        scrollPane.setBorder(null);     // No se que queda millor

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

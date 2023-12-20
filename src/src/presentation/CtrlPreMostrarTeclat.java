package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlPreMostrarTeclat {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, nomTeclat;
    private JButton descartar, editar, confirmar;
    private JFrame vista;
    private JPanel PTítol, PSouth, PNom, PCenter, PTeclat;
    private String teclat;
    private char[][] distribucio;

    public CtrlPreMostrarTeclat(String teclat) {
        this.teclat = teclat;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("PreMostrar Teclat", "title");
        nomTeclat = Utils.initLabel(teclat, "text");

        descartar = Utils.Button(null, "cross");
        editar = Utils.Button(null, "edit");
        confirmar = Utils.Button(null, "check");

        distribucio = ctrlPresentacio.getDistribucio(teclat);
        PTeclat = new JPanel();
        PTeclat.setLayout(new GridLayout(distribucio.length, distribucio[0].length));
        for (int i = 0; i < distribucio.length; i++) {
            for (int j = 0; j < distribucio[i].length; j++) {
                String character = String.valueOf(distribucio[i][j]);
                PTeclat.add(Utils.Button(character, null));
            }
        }

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PNom = new JPanel();
        PNom.add(nomTeclat);

        PCenter = new JPanel();
        PCenter.setLayout(new BorderLayout());
        PCenter.add(PNom, BorderLayout.NORTH);
        PCenter.add(PTeclat, BorderLayout.CENTER);

        PSouth = new JPanel();
        PSouth.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        PSouth.add(descartar);
        PSouth.add(editar);
        PSouth.add(confirmar);

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

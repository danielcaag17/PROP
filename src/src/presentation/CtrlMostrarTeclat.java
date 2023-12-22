package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.exceptions.TeclatNoExisteix;

public class CtrlMostrarTeclat {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, nomTeclat;
    private JButton eliminar, editar, confirmar, provar;
    private JFrame vista;
    private JPanel PTítol, PSouth, PNom, PCenter, PTeclat;
    private String teclat;
    private char[][] distribucio;

    public CtrlMostrarTeclat(String teclat) {
        this.teclat = teclat;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("Mostrar Teclat", "title");
        nomTeclat = Utils.initLabel(teclat, "text");

        eliminar = Utils.Button(null, "delete");
        eliminar.addActionListener(e -> ctrlPresentacio.elimina("Teclat", teclat, vista, "LlistaTeclats"));
        editar = Utils.Button(null, "edit");
        editar.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "ModificarTeclat", teclat));
        confirmar = Utils.Button(null, "backArrow");
        confirmar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));
        provar = Utils.Button("Provar", null);
        provar.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "ProvarTeclat", teclat));

        try {
            distribucio = ctrlPresentacio.getDistribucio(teclat);
        } catch (TeclatNoExisteix e) {
            ctrlPresentacio.Excepcio(vista, e.getTipus(), "Teclat " + teclat + " no exiteix");
        }

        PTeclat = Utils.JPanel(new GridLayout(distribucio.length, 1), null);
        for (int i = 0; i < distribucio.length; i++) {
            int size = checkSize(distribucio[i]);
            JPanel PRow = Utils.JPanel(new GridLayout(1, size), null);
            for (int j = 0; j < distribucio[i].length; j++) {
                String id = String.valueOf(distribucio[i][j]);
                if (!id.equals("-")) {
                    JButton button = Utils.Button(id, null);
                    PRow.add(button);
                }
            }
            PTeclat.add(PRow);
        }

        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PNom = new JPanel();
        PNom.add(nomTeclat, BorderLayout.CENTER);
        PNom.add(provar, BorderLayout.WEST);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PNom, BorderLayout.NORTH);
        PCenter.add(PTeclat, BorderLayout.CENTER);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(confirmar);
        PSouth.add(eliminar);

        vista = Utils.initFrame("MostrarTeclat");
    }

    private int checkSize(char[] row) {
        int size = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] != '-') size++;
        }
        return size;
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

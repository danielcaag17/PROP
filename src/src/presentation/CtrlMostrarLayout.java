package src.presentation;

import java.awt.*;
import javax.swing.*;

import src.exceptions.Excepcions;

public class CtrlMostrarLayout {
    private CtrlPresentacio cp;
    private String idLayout;
    private JFrame vista;
    private JLabel titol, nomLayout;
    private JPanel PTitol, PCenter, PLayout, PMida, PSouth;
    private JButton eliminar, back;
    private int[][] distribucio;

    public CtrlMostrarLayout(String idLayout) {
        this.idLayout = idLayout;
        cp = CtrlPresentacio.getInstance();
       
        init();
        addElementsFrame();
    }

    private void init() {
        titol = Utils.initLabel("Mostrar Layout", "title");
        nomLayout = Utils.initLabel("Distribució de les IDs del Layout de mida: " + idLayout, "text");

        eliminar = Utils.Button(null, "delete");
        eliminar.addActionListener(e -> cp.elimina("Layout", idLayout, vista, "LlistaLayouts"));

        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaLayouts"));

        try {
            distribucio = cp.getDistribucioLayout(Integer.parseInt(idLayout));
        } catch (Excepcions e) {
            cp.Excepcio(vista, e.getTipus(), e.getMessage());
        }

        PLayout = Utils.JPanel(new GridLayout(distribucio.length, 1), null);
        for (int i = 0; i < distribucio.length; i++) {
            int size = checkSize(distribucio[i]);
            JPanel PRow = Utils.JPanel(new GridLayout(1, size), null);
            for (int j = 0; j < distribucio[i].length; j++) {
                Integer id = distribucio[i][j];
                if (id != -1) {
                    JButton button = Utils.Button(Integer.toString(id), null);
                    PRow.add(button);
                }
            }
            PLayout.add(PRow);
        }

        PTitol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTitol.add(titol, BorderLayout.CENTER);

        PMida = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PMida.add(nomLayout, BorderLayout.CENTER);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PLayout, BorderLayout.CENTER);
        PCenter.add(PMida, BorderLayout.NORTH);

        PSouth = Utils.JPanel(new FlowLayout(), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back, BorderLayout.CENTER);
        PSouth.add(eliminar, BorderLayout.CENTER);

        vista = Utils.initFrame("MostrarLayout");
    }

    private void addElementsFrame() {
        vista.add(PTitol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private int checkSize(int[] row) {
        int size = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] != -1) size++;
        }
        return size;
    }
}

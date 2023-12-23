package src.presentation;

import java.awt.*;

import javax.swing.*;

import src.exceptions.*;

public class CtrlProvarTeclat {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel titol, nomTeclat;
    private JButton eliminar, editar, back, delLastChar;
    private JPanel PNorth, PTitol, PSouth, PNom, PCenter, PTeclat, PTextField;
    private JFrame vista;
    private JTextField testingField;
    private String teclat;
    private String inputTeclat = "";
    private char[][] distribucio;

    
    public CtrlProvarTeclat(String teclat) {
        this.teclat = teclat;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        titol = Utils.initLabel("Provar Teclat", "title");
        nomTeclat = Utils.initLabel(teclat, "text");

        eliminar = Utils.Button(null, "delete");
        eliminar.addActionListener(e -> ctrlPresentacio.elimina("Teclat", teclat, vista, "LlistaTeclats"));
        editar = Utils.Button(null, "edit");
        editar.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "ModificarTeclat", teclat));
        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantallaElementMostrar(vista, "LlistaLayouts", teclat));

        testingField = new JTextField();
        testingField.setPreferredSize(new Dimension(Utils.getScreenWidth()/2, Utils.getScreenHeight()/10));
        testingField.setFont(Utils.getFontText());
        testingField.setHorizontalAlignment(JTextField.CENTER);

        delLastChar = Utils.Button("<----", null);
        delLastChar.addActionListener(e -> {
            if (inputTeclat.length() > 0) {
                inputTeclat = inputTeclat.substring(0, inputTeclat.length() - 1);
                testingField.setText(inputTeclat);
            }
        });

        try {
            distribucio = ctrlPresentacio.getDistribucio(teclat);
        } catch (TeclatNoExisteix e) {
            ctrlPresentacio.Excepcio(vista, e.getTipus(), "Teclat " + teclat + " no existeix");
        }

        PTeclat = Utils.JPanel(new GridLayout(distribucio.length, 1), null);
        for (int i = 0; i < distribucio.length; i++) {
            int size = checkSize(distribucio[i]);
            JPanel PRow = Utils.JPanel(new GridLayout(1, size), null);
            for (int j = 0; j < distribucio[i].length; j++) {
                String id = String.valueOf(distribucio[i][j]);
                if (!id.equals("-")) {
                    JButton button = Utils.Button(id, null);
                    button.addActionListener(e -> {
                        inputTeclat += id;
                        testingField.setText(inputTeclat);
                    });
                    PRow.add(button);
                }
            }
            PTeclat.add(PRow);
        }

        PNorth = Utils.JPanel(new BorderLayout(), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTitol = new JPanel();
        PTitol.add(titol);
        PNom = new JPanel();
        PNom.add(nomTeclat);

        PNorth.add(PTitol, BorderLayout.CENTER);
        PNorth.add(PNom, BorderLayout.SOUTH);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PTextField = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0), null);
        PTextField.add(testingField, BorderLayout.EAST);
        PTextField.add(delLastChar, BorderLayout.WEST);
        PCenter.add(PTextField, BorderLayout.NORTH);
        PCenter.add(PTeclat, BorderLayout.CENTER);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);
        PSouth.add(editar);
        PSouth.add(eliminar);

        vista = Utils.initFrame("ProvarTeclat");
    }

    private int checkSize(char[] row) {
        int size = 0;
        for (int i = 0; i < row.length; i++) {
            if (!String.valueOf(row[i]).equals("-")) size++;
        }
        return size;
    }

    private void addElementsFrame() {
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

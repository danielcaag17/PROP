package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.exceptions.Excepcions;

public class CtrlAfegirAlfabet {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, indicaNom;
    private JTextField nomAlfabet;
    private JComboBox<String> tipusEntrada;
    private String[] arrayTipusEntrada;
    private String nomAlf;
    private String tipus;   // tipus seleccionat per l'usuari
    private String path;
    private JFileChooser fileChooser;
    private JButton cancelar, confirmar, openFile;
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PNom;
    private Boolean pathSelected = false;

    public CtrlAfegirAlfabet() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    public void init() {
        títol = Utils.initLabel("Crear alfabet", "title");

        indicaNom = Utils.initLabel("Indica el nom de l'alfabet: ", "text");
        nomAlfabet = new JTextField();
        nomAlfabet.setPreferredSize(new Dimension(200, 50));
        nomAlfabet.setFont(Utils.getFontText());

        PNom = new JPanel();
        PNom.add(indicaNom);
        PNom.add(nomAlfabet);

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaAlfabets"));

        confirmar = Utils.Button("Confirmar", null);
        confirmar.setEnabled(false);
        confirmar.addActionListener(e -> confirmar());

        nomAlfabet.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                nomAlf = nomAlfabet.getText();
                if (! nomAlf.equals("") && pathSelected) confirmar.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nomAlf = nomAlfabet.getText();
                if (nomAlf.equals("")) confirmar.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
            }
            
        });

        openFile = Utils.Button("Selecciona fitxer", null);
        openFile.addActionListener(e -> seleccionaFitxer());

        arrayTipusEntrada = ctrlPresentacio.getListTipusEntrada();
        tipusEntrada = new JComboBox<>(arrayTipusEntrada);

        // FALTA POSAR MILLOR LA UI
        PCenter = new JPanel();
        PCenter.add(PNom);
        PCenter.add(tipusEntrada);
        PCenter.add(openFile);

        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(cancelar);
        PSouth.add(confirmar);

        vista = Utils.initFrame();
    }

    public void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private void seleccionaFitxer() {
        fileChooser = new JFileChooser();
        // Per obrir el file chooser directament en el directori actual
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            pathSelected = true;
            if (! nomAlf.equals("")) confirmar.setEnabled(true);
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            path = file.toString();
        }
        else {
            pathSelected = false;
            confirmar.setEnabled(false);
        }
    }

    private void confirmar() {
        nomAlf = nomAlfabet.getText();
        System.out.println(nomAlf);
        String[] s = ctrlPresentacio.getListAlfabets();
        for (int i = 0; i < s.length; i++) {
            if (s[i] == nomAlf) {}// throw new AlfabetJaExisteix();
        }
        tipus = (String) tipusEntrada.getSelectedItem();
        try {
            ctrlPresentacio.afegirAlfabet(nomAlf, tipus, path);
            // pantalla informativa
        }
        catch(Excepcions e) {
            String msg;
            switch (e.getTipus()) {
                case "FormatDadesNoValid":
                    msg = "El format de les dades del fitxer "+ path + " no és vàlid. (Veure more_info)";
                    break;
                case "EntradaLlegidaMalament":
                    msg = "Hi ha hagut un error en el processament de les dades.";
                    break;
                default:
                    msg = e.getMessage();
            }
            ctrlPresentacio.Excepcio(e.getTipus(), msg);
        } catch (FileNotFoundException e) {
            // No fer res, sempre es troba el fitxer
        }
        Utils.canviPantalla(vista, "LlistaAlfabets");
    }
}
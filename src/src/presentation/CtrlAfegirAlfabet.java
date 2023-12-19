package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CtrlAfegirAlfabet {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, indicaNom;
    private JTextField nomAlfabet;
    private JComboBox tipusEntrada;
    private String[] arrayTipusEntrada;
    private String nomAlf;
    private String tipus;   // tipus seleccionat per l'usuari
    private String path;
    private JFileChooser fileChooser;
    private JButton cancelar, confirmar, openFile;
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter, PNom;

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

        cancelar = new JButton();
        cancelar.setPreferredSize(new Dimension(100, 50));
        cancelar.setText("Cancelar");
        cancelar.setFont(Utils.getFontText());
        cancelar.setFocusable(false);
        cancelar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaAlfabets"));

        confirmar = new JButton();
        confirmar.setPreferredSize(new Dimension(100, 50));
        confirmar.setText("Confirmar");
        confirmar.setFont(Utils.getFontText());
        confirmar.setFocusable(false);
        confirmar.setEnabled(false);
        confirmar.addActionListener(e -> confirmar());

        nomAlfabet.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                nomAlf = nomAlfabet.getText();
                if (! nomAlf.equals("")) confirmar.setEnabled(true);
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

        openFile = new JButton();
        openFile.setPreferredSize(new Dimension(100, 50));
        openFile.setText("Selecciona fitxer");
        openFile.setFont(Utils.getFontText());
        openFile.setFocusable(false);
        openFile.addActionListener(e -> seleccionaFitxer());

        arrayTipusEntrada = ctrlPresentacio.getListTipusEntrada();
        tipusEntrada = new JComboBox<>(arrayTipusEntrada);

        // FALTA POSAR MILLOR LA UI
        PCenter = new JPanel();
        PCenter.add(PNom);
        PCenter.add(tipusEntrada);
        PCenter.add(openFile);

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        PSouth = new JPanel();
        PSouth.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
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
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            path = file.toString();
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
        /* try {
            ctrlPresentacio.afegirAlfabet(nomAlf, tipus, path);
        }
        catch (FormatDadesNoValid e) {

        }
        catch (EntradaLlegidaMalament e) {

        }
        */ 

        Utils.canviPantalla(vista, "LlistaAlfabets");
    }
}
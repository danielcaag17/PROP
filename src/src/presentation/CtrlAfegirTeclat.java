package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.exceptions.Excepcions;

public class CtrlAfegirTeclat {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, indicaNom;
    private JTextField nomTeclat;
    private JComboBox<String> listAlfabets, listGeneradors;
    private String[] arrayAlfabets, arrayGeneradors;
    private JButton cancelar, confirmar;
    private JFrame vista;
    private JPanel PTítol, PSouth, PNom, PCenter;

    public CtrlAfegirTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    public void init() {
        títol = Utils.initLabel("Crear teclat", "title");

        indicaNom = Utils.initLabel("Indica el nom del teclat: ", "text");
        nomTeclat = new JTextField();
        nomTeclat.setPreferredSize(new Dimension(200, 50));
        nomTeclat.setFont(Utils.getFontText());

        PNom = new JPanel();
        PNom.add(indicaNom);
        PNom.add(nomTeclat);

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));

        confirmar = Utils.Button("Confirmar", null);
        confirmar.setEnabled(false);
        confirmar.addActionListener(e -> PreMostrarTeclat());

        nomTeclat.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (! nomTeclat.getText().equals("")) confirmar.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (nomTeclat.getText().equals("")) confirmar.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
            }
            
        });

        // Veure si cal un Panel per un Label
        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);

        arrayAlfabets = ctrlPresentacio.getNomAlfabets();
        listAlfabets = new JComboBox<>(arrayAlfabets);

        arrayGeneradors = ctrlPresentacio.getListGeneradors();
        listGeneradors = new JComboBox<>(arrayGeneradors);

        // FALTA POSAR MILLOR LA UI
        PCenter = new JPanel();
        PCenter.add(PNom);
        PCenter.add(listGeneradors);
        PCenter.add(listAlfabets);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(cancelar);
        PSouth.add(confirmar);

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private void PreMostrarTeclat() {
        String alfabet = (String) listAlfabets.getSelectedItem();
        String generador = (String) listGeneradors.getSelectedItem();
        try {
            ctrlPresentacio.crearNouTeclat(nomTeclat.getText(), alfabet, generador);
        } catch (Excepcions e) {
            switch (e.getTipus()) {
                case "TeclatJaExisteix": 
                    System.out.println("Ja existeix un Teclat amb el nom: " + nomTeclat + ". Prova amb un altre nom.");
                    break;
                case "AlfabetNoExisteix":
                    System.out.println("No existeix un Alfabet amb el nom: " + alfabet + ".");
                    break;
                case "MidesDiferents":
                    // EXC que ja no ha d'estar
                    System.out.println("Les mides de l'Alfabet seleccionat i del Layout han de ser iguals.");
                    break;
            }
        }
        Utils.canviPantallaElementMostrar(vista, "PreMostrarTeclat", nomTeclat.getText());
    }
}

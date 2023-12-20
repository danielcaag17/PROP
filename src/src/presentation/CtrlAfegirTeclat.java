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

        cancelar = new JButton();
        cancelar.setPreferredSize(new Dimension(100, 50));
        cancelar.setText("Cancelar");
        cancelar.setFont(Utils.getFontText());
        cancelar.setFocusable(false);
        cancelar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));

        confirmar = new JButton();
        confirmar.setPreferredSize(new Dimension(100, 50));
        confirmar.setText("Confirmar");
        confirmar.setFont(Utils.getFontText());
        confirmar.setFocusable(false);
        confirmar.addActionListener(e -> Utils.canviPantalla(vista, "PreMostrarTeclat"));

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
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
}

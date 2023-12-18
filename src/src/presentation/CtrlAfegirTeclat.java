package src.presentation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CtrlAfegirTeclat {
    private JLabel títol;
    private JTextField nomTeclat;
    private JList listAlfabets, generadors;
    private String[] arryAlfabets;
    private JButton cancelar, confirmar;
    private JFrame vista;
    private CtrlPresentacio ctrlPresentacio;
    private JPanel PTítol, PSouth;

    public CtrlAfegirTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    public void init() {

    }

    public void addElementsFrame() {
        //vista.add
    }
}

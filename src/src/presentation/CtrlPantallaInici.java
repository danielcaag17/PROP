package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlPantallaInici {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel títol, membre1, membre2, membre3;
    private JButton start;
    private JFrame vista;
    private JPanel PTítol, PMembre1, PMembre2, PMembre3, PStart, PCenter;

    public CtrlPantallaInici() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        init();
        addElementsFrame();
    }

    private void init() {
        initElements();
        initFrame();
    }

    private void initElements() {
        títol = new JLabel();
        initLabel("títol", "PROP");

        membre1 = new JLabel();
        initLabel("membre1", "Pau Rambla");
        
        membre2 = new JLabel();
        initLabel("membre2", "Daniel Canizares");

        membre3 = new JLabel();
        initLabel("membre3", "Jordi Otal");

        start = new JButton();
        start.setPreferredSize(new Dimension(100,50));
        start.setText("START");
        start.setFocusable(false);
        start.addActionListener(e -> start());

        PTítol = new JPanel();
        PTítol.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);
        
        PMembre1 = new JPanel();
        PMembre1.add(membre1);

        PMembre2 = new JPanel();
        PMembre2.add(membre2);

        PMembre3 = new JPanel();
        PMembre3.add(membre3);

        PStart = new JPanel();
        PStart.setPreferredSize(new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PStart.add(start);

        PCenter = new JPanel();
        PCenter.setLayout(new BorderLayout());
        PCenter.add(PMembre1, BorderLayout.WEST);
        PCenter.add(PMembre2, BorderLayout.CENTER);
        PCenter.add(PMembre3, BorderLayout.EAST);
    }

    private void initLabel(String labelName, String name) {
        switch (labelName) {
            case "títol":
                títol.setVisible(true);
                títol.setText(name);
                vista.setFont(Utils.getFontTitle());
                break;
            case "membre1":
                membre1.setVisible(true);
                membre1.setText(name);
                membre1.setFont(Utils.getFontText());
                break;
            case "membre2":
                membre2.setVisible(true);
                membre2.setText(name);
                membre2.setFont(Utils.getFontText());
                break;
            case "membre3":
                membre3.setVisible(true);
                membre3.setText(name);
                membre3.setFont(Utils.getFontText());
                break;
            default:
                // Error
                break;
        }
    }

    private void initFrame() {
        vista = new JFrame();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
        vista.setSize(Utils.getScreenWidth(), Utils.getScreenHeight());
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setLayout(new BorderLayout());
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PStart, BorderLayout.SOUTH);
    }

    private void start() {
        vista.setVisible(false); // Diferent entre no fer visible i eliminar la pantalla 
        ctrlPresentacio.canviVista("LlistaTeclats");
    }
}

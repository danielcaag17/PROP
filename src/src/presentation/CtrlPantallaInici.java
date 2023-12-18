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

    public CtrlPantallaInici(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        init();
        addElementsFrame();
    }

    private void init() {
        títol = Utils.initLabel("PROP", "title");

        membre1 = Utils.initLabel("Pau Rambla", "text");
        membre2 = Utils.initLabel("Daniel Canizares", "text");
        membre3 = Utils.initLabel("Jordi Otal", "text");

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

        vista = Utils.initFrame();
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PStart, BorderLayout.SOUTH);
    }

    private void start() {
        vista.setVisible(false); // Diferent entre no fer visible i eliminar la pantalla 
        ctrlPresentacio.canviVista("LlistaAlfabets");
    }
}

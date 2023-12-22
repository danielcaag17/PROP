package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlPantallaInici {
    private JLabel títol, membre1, membre2, membre3;            // Labels amb el títol de la pantalla i el nom dels tres membres de l'equip
    private JButton start;                                      // Button per començar l'aplicatiu
    private JFrame vista;                                       // Frame de la vista d'aquesta pantalla
    private JPanel PTítol, PStart, PCenter, PEast, PWest;       // Panels del Frame que té un BorderLayout (NORTH, SOUTH, CENTER, EAST, WEST)

    public CtrlPantallaInici() {
        initElements();                 // Inicialitzar els elements de la pantalla
        initPanels();                   // Inicialitzar els panels de la pantalla 
        addElementsFrame();             // Afegir tots els elements necessaris al Frame principal
    }

    private void initElements() {
        títol = Utils.initLabel("PROP", "title");

        membre1 = Utils.initLabel("Pau Rambla", "text");
        membre2 = Utils.initLabel("Daniel Canizares", "text");
        membre3 = Utils.initLabel("Jordi Otal", "text");

        start = Utils.Button("START", null);
        start.addActionListener(e -> Utils.canviPantalla(vista,"LlistaTeclats"));

        vista = Utils.initFrame("PantallaInici");
    }

    private void initPanels() {
        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(títol);
        
        JPanel PMembre1 = new JPanel();
        PMembre1.add(membre1);

        JPanel PMembre2 = new JPanel();
        PMembre2.add(membre2);

        JPanel PMembre3 = new JPanel();
        PMembre3.add(membre3);

        PStart = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PStart.add(start);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PMembre1, BorderLayout.WEST);
        PCenter.add(PMembre2, BorderLayout.CENTER);
        PCenter.add(PMembre3, BorderLayout.EAST);

        PEast = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6,Utils.getScreenHeight()));
        PWest = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6,Utils.getScreenHeight()));
    }

    private void addElementsFrame() {
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PStart, BorderLayout.SOUTH);
        vista.add(PEast, BorderLayout.EAST);
        vista.add(PWest, BorderLayout.WEST);
    }
}

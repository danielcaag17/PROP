package src.presentation;

import javax.swing.*;
import java.awt.*;

public class CtrlLlistaLayouts {
    private CtrlPresentacio cp;
    private JFrame vista;
    private JLabel title;
    private JButton afegir, back;
    private JPanel PNorth, PAfegir, PLlista, PCenter, PSouth;

    public CtrlLlistaLayouts() {
        cp = CtrlPresentacio.getInstance();
        vista = Utils.initFrame("LlistaLayouts");
        initElements();
        initPanels();
        linkFrameElements();
    }

    private void initElements() {
        title = Utils.initLabel("Llista Layouts", "title");

        afegir = Utils.Button("+", null);
        afegir.addActionListener(e -> Utils.canviPantalla(vista, "AfegirLayout"));

        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title);

        PAfegir = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PAfegir.add(afegir);

        PLlista = new JPanel();
        PLlista.setLayout(new BoxLayout(PLlista, BoxLayout.Y_AXIS));    //panel que no es pot instanciar amb Utils
        Integer[] layouts = cp.getNomLayouts();
        for (int i = 0; i < layouts.length; i++) {
            Integer nl = layouts[i];
            JLabel label = Utils.initLabel("Mida: " + Integer.toString(nl), "text");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton delete = Utils.Button(null, "delete");
            delete.addActionListener(e -> cp.elimina("Layout", Integer.toString(nl), vista, "LlistaLayouts"));

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.add(label);
            panel.add(delete);

            PLlista.add(panel);
        }

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PLlista, BorderLayout.CENTER);
        PCenter.add(PAfegir, BorderLayout.NORTH);

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);
    }

    private void linkFrameElements() {
        vista = Utils.initFrame("LlistaLayouts");

        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }
}

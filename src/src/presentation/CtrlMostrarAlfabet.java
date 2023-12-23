package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import src.exceptions.Excepcions;

public class CtrlMostrarAlfabet {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel title, labelNomAlfabet;
    private JButton back;
    private String nomAlfabet;                          // nom de l'alfabet escrit per l'usuari
    private JPanel PNorth, PCenter, PSouth, PEast, PWest;
    private JFrame vista;

    public CtrlMostrarAlfabet(String nomAlfabet) {
        this.nomAlfabet = nomAlfabet;
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        title = Utils.initLabel("Mostrar Alfabet", "title");
        labelNomAlfabet = Utils.initLabel(nomAlfabet, "text");
        back = Utils.Button(null, "backArrow");
        back.addActionListener(e -> Utils.canviPantalla(vista, "LlistaAlfabets"));
    }

    private void initPanels() {
        PNorth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PNorth.add(title);
        
        JPanel PNomAlfabet = new JPanel();
        PNomAlfabet.add(labelNomAlfabet);
        JPanel PAbecedari = new JPanel();
        PAbecedari.setLayout(new BoxLayout(PAbecedari, BoxLayout.Y_AXIS));
        // PAbecedari.setLayout(new BoxLayout(PAbecedari, BoxLayout.Y_AXIS));
        // PAbecedari.setLayout(new GridLayout(2,13));
        Map<Character, Double> characters = ctrlPresentacio.getCharacters(nomAlfabet);
        for (Character c : characters.keySet()) {
            String lletra = String.valueOf(c);

            JButton buttonCharacter = Utils.Button(lletra, null);
            buttonCharacter.addActionListener(e -> {
                try {
                    double[] frequenciesCharacter = ctrlPresentacio.getFrequenciesCharacter(nomAlfabet, lletra);
                    Character[] abecedari = ctrlPresentacio.getAbecedari(nomAlfabet);

                    Double freq = characters.get(c) * 100.0;
                    String freqString = String.format("%.3f", freq);
                    freqString += "%";
                    
                    JPanel PEastDialog = Utils.JPanel(new FlowLayout(), null);
                    JPanel PWestDialog = Utils.JPanel(new FlowLayout(), null);

                    JPanel PCenterDialog = new JPanel(new GridLayout(frequenciesCharacter.length/3, 3));
                    for (int i = 0; i < frequenciesCharacter.length; i++) {
                        String character = String.valueOf(abecedari[i]);
                        String f = String.format("%.3f", frequenciesCharacter[i] * 100);
                        String text = "";
                        if (i + 1 < frequenciesCharacter.length) text += character + " -> " + f + "%, ";
                        else text += character + " -> " + f + "%";

                        JLabel label = Utils.initLabel(text, "text");
                        PCenterDialog.add(label);
                    }
                    
                    JLabel labelFreqAbs = Utils.initLabel("Freqüència absoluta: " + freqString, "text");
                    labelFreqAbs.setHorizontalAlignment(JLabel.CENTER);
                    labelFreqAbs.setVerticalAlignment(JLabel.CENTER);
                    
                    JDialog dialogFreqs = new JDialog(vista, "Freqüències", true);
                    dialogFreqs.setLayout(new BorderLayout());
                    JButton buttonOk = Utils.Button("OK", null);
                    buttonOk.addActionListener(act -> {
                        dialogFreqs.dispose();
                    });
                    dialogFreqs.add(buttonOk, BorderLayout.SOUTH);
                    dialogFreqs.add(PCenterDialog, BorderLayout.CENTER);
                    dialogFreqs.add(PEastDialog, BorderLayout.EAST);
                    dialogFreqs.add(PWestDialog, BorderLayout.WEST);
                    dialogFreqs.add(labelFreqAbs, BorderLayout.NORTH);
                    dialogFreqs.setSize(new Dimension(PCenter.getWidth(), PCenter.getHeight() + labelFreqAbs.getHeight() + buttonOk.getHeight()));  
                    dialogFreqs.setLocationRelativeTo(vista);
                    dialogFreqs.setVisible(true);
                    dialogFreqs.setResizable(false);
                } catch (Excepcions exc) {
                    ctrlPresentacio.Excepcio(vista, exc.getTipus(), "No s'ha trobat l'alfabet");
                }
            });

            JPanel panel = Utils.JPanel(new FlowLayout(), null);
            panel.add(buttonCharacter);

            PAbecedari.add(panel);
        }


        JScrollPane scrollPane = new JScrollPane(PAbecedari);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);  

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PNomAlfabet, BorderLayout.NORTH);
        PCenter.add(scrollPane, BorderLayout.CENTER);

        JButton delete = Utils.Button(null, "delete");
        delete.addActionListener(e -> ctrlPresentacio.elimina("Alfabet", nomAlfabet, vista, "LlistaAlfabets"));

        PSouth = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(back);
        PSouth.add(delete);

        PEast = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6,Utils.getScreenHeight()));
        PWest = Utils.JPanel(null, new Dimension(Utils.getScreenWidth()/6,Utils.getScreenHeight()));
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("LlistaAlfabets");
        vista.add(PNorth, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
        vista.add(PEast, BorderLayout.EAST);
        vista.add(PWest, BorderLayout.WEST);
    }
}

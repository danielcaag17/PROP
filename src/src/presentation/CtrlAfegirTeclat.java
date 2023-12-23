package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.exceptions.Excepcions;

public class CtrlAfegirTeclat {
    private CtrlPresentacio ctrlPresentacio;
    private JLabel title, labelIndicaNomTeclat, labelLoading;
    private JTextField textFieldNomTeclat;
    private JComboBox<String> listAlfabets, listGeneradors;
    private JButton cancelar, confirmar;
    private JFrame vista;
    private JPanel PTítol, PSouth, PCenter;
    private SwingWorker<Void, Void> worker;

    public CtrlAfegirTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        initElements();
        initPanels();
        addElementsFrame();
    }

    private void initElements() {
        title = Utils.initLabel("Afegir teclat", "title");

        labelIndicaNomTeclat = Utils.initLabel("Indica el nom del teclat: ", "text");
        textFieldNomTeclat = new JTextField();
        textFieldNomTeclat.setPreferredSize(new Dimension(200, 50));
        textFieldNomTeclat.setFont(Utils.getFontText());
        // Afegir document listener per detectar canvis en el textField i així acitvar o desactivar el botó de confirmar
        afegirDocumentListener();

        String[] arrayGeneradors = ctrlPresentacio.getListGeneradors();
        listGeneradors = new JComboBox<>(arrayGeneradors);

        String[] arrayAlfabets = ctrlPresentacio.getNomAlfabets();
        listAlfabets = new JComboBox<>(arrayAlfabets);        

        cancelar = Utils.Button("Cancelar", null);
        cancelar.addActionListener(e -> Utils.canviPantalla(vista, "LlistaTeclats"));

        confirmar = Utils.Button("Confirmar", null);
        confirmar.setEnabled(false);

        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmar.setEnabled(false);
                cancelar.setEnabled(false);
                labelLoading.setVisible(true);

                worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        PreMostrarTeclat();
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Re-enable the button when the task is done
                        confirmar.setEnabled(true);
                        labelLoading.setVisible(false);
                        cancelar.setEnabled(true);
                        vista.repaint();
                    }
                };
                /*
                worker.addPropertyChangeListener(evt -> {
                    labelLoading.setVisible(true); 
                    //vista.repaint();
                });
                */
                worker.execute();
            }
        });

        String path = ".." + File.separator + ".." + File.separator + "data/imatges/loading.gif";
        
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        // A definir quina es la mida del botó
        // image = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        labelLoading = new JLabel(imageIcon);
        labelLoading.setVisible(false);
        labelLoading.setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        
    }

    private void initPanels() {
        // Veure si cal un Panel per un Label
        PTítol = Utils.JPanel(null, new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PTítol.add(title);

        JPanel PIndicaNomAlfabet = new JPanel();
        PIndicaNomAlfabet.add(labelIndicaNomTeclat);
        PIndicaNomAlfabet.add(textFieldNomTeclat);        

        JPanel PAfegir = new JPanel();
        PAfegir.add(PIndicaNomAlfabet);
        PAfegir.add(listGeneradors);
        PAfegir.add(listAlfabets);

        JPanel PCarregant = new JPanel();
        PCarregant.add(labelLoading);

        PCenter = Utils.JPanel(new BorderLayout(), null);
        PCenter.add(PAfegir, BorderLayout.NORTH);
        PCenter.add(PCarregant, BorderLayout.CENTER);

        PSouth = Utils.JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10), new Dimension(Utils.getScreenWidth(),Utils.getScreenHeight()/6));
        PSouth.add(cancelar);
        PSouth.add(confirmar);
    }

    private void addElementsFrame() {
        vista = Utils.initFrame("AfegirTeclat");
        vista.add(PTítol, BorderLayout.NORTH);
        vista.add(PCenter, BorderLayout.CENTER);
        vista.add(PSouth, BorderLayout.SOUTH);
    }

    private void PreMostrarTeclat() {
        String alfabet = (String) listAlfabets.getSelectedItem();
        String generador = (String) listGeneradors.getSelectedItem();
        String nomTeclat = textFieldNomTeclat.getText();
        if (nomesEspaisBlanc(nomTeclat)) ctrlPresentacio.Excepcio(vista, "NomTeclatNoValid", "No es pot crear un teclat amb aquest nom");
        else {
            try {
                ctrlPresentacio.crearNouTeclat(nomTeclat, alfabet, generador);
                Utils.canviPantallaElementMostrar(vista, "PreMostrarTeclat", nomTeclat);
            } catch (Excepcions e) {
                String msg = "";
                switch (e.getTipus()) {
                    case "TeclatJaExisteix": 
                        msg = "Ja existeix un Teclat amb el nom " + nomTeclat;
                        break;
                    default:
                        msg = e.getMessage();
                        break;
                }
                cancelar.setEnabled(true);
                labelLoading.setVisible(false);
                vista.repaint();
                ctrlPresentacio.Excepcio(vista, e.getTipus(), msg);
            }
            catch(Exception e) {
                cancelar.setEnabled(true);
                labelLoading.setVisible(false);
                vista.repaint();
                ctrlPresentacio.Excepcio(vista, "Error: ", e.getMessage());;
            }
        }
    }

    private void afegirDocumentListener() {
        textFieldNomTeclat.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (! textFieldNomTeclat.getText().equals("")) confirmar.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textFieldNomTeclat.getText().equals("")) confirmar.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
            }
            
        });  
    }

    private Boolean nomesEspaisBlanc(String nom) {
        for (int i = 0; i < nom.length(); i++) {
            char c = nom.charAt(i);
            if (c != ' ') return false;
        }
        return true;
    }
}

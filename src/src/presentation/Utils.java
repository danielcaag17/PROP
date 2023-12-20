package src.presentation;

import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

public class Utils {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenWidth = (int) screenSize.getWidth();
    private static int screenHeight = (int) screenSize.getHeight();
    private static int elementHeight = 100;     // Altura d'element de les vistes de llista
    
    // potser no cal
    // private static int elementWidth = 100;      // Altura d'element de les vistes de llista


    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    // potser privat
    public static Font getFontTitle() {
        // A definir
        return new Font("Serif", Font.BOLD, 30);
    }

    // potser privat
    public static Font getFontText() {
        // A definir
        return new Font("Serif", Font.PLAIN, 16);
    }

    public static int getElementHeight() {
        return elementHeight;
    }

    public static Color getBackgroundColorVista() {
        // A redefinir
        return Color.white;
    }

    public static Color getBackgroundColorElement() {
        // A redefinir
        return Color.lightGray;
    }

    //potser privat
    public static ImageIcon getImage(String imatge) {
        // descarregar imatge + path + definir nom carpeta
        String path = ".." + File.separator + ".." + File.separator + "data/imatges/" + imatge + ".png";
        return new ImageIcon(path);
    }

    public static JLabel initLabel(String name, String type) {
        JLabel label = new JLabel();
        label.setText(name);
        label.setVisible(true);
        if (type.equals("title")) label.setFont(getFontTitle());
        else if (type.equals("text")) label.setFont(getFontText());
        return label;
    }

    public static JFrame initFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Utils.getScreenWidth()/3, Utils.getScreenHeight()/3);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        // frame.setIconImage(null);        // Si volem que surti algun logo en la part superior esquerra
        return frame;
    }

    public static JButton Button(String text, String imatge) {
        JButton button = new JButton();
        if (text != null) {
            button.setPreferredSize(new Dimension(100, 50));
            button.setText(text);
            button.setFont(getFontText());
            button.setFocusable(false);
        }
        else if (imatge != null) {
            ImageIcon imageIcon = getImage(imatge);
            Image image = imageIcon.getImage();
            // A definir quina es la mida del botó
            image = image.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            button.setIcon(imageIcon);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));   // Donar sensació al usuari
            button.setContentAreaFilled(false);               // Només es vegi la foto i no el botó
            button.setBorder(null);
        }
        
        return button;
    }

    public static JPanel JPanel(LayoutManager mgr, Dimension preferredSize) {
        JPanel panel = new JPanel();
        if (mgr != null) panel.setLayout(mgr);
        if (preferredSize != null) panel.setPreferredSize(preferredSize);
        return panel;
    }

    // Tanca la pantalla actual i mostra la nova pantalla
    public static void canviPantalla(Window vista, String pantalla) {
        vista.dispose();
        CtrlPresentacio.getInstance().canviVista(pantalla, null);
    }

    // pantalla que necessita algun parametre addicional
    public static void canviPantallaElementMostrar(JFrame vista, String pantalla, String elementAMostrar) {
        vista.dispose();
        if (elementAMostrar != null) {
            CtrlPresentacio.getInstance().canviVista(pantalla, elementAMostrar);
        }
        else {
            // salta EXC
        }
    }

}
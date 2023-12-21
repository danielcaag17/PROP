package src.presentation;

import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Utils {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenWidth = (int) screenSize.getWidth();
    private static int screenHeight = (int) screenSize.getHeight();
    private static int elementHeight = 100;     // Altura d'element de les vistes de llista
    
    // potser no cal
    // private static int elementWidth = 100;      // Altura d'element de les vistes de llista

    /**
     * @return Altura de la pantalla
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /*+
     * @return Amplada de la pantalla
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    // potser privat
    /**
     * @return Retorna la font per als títols
     */
    public static Font getFontTitle() {
        // A definir
        return new Font("Serif", Font.BOLD, 30);
    }

    // potser privat
    /**
     * @return Retorna la font per als textos
     */
    public static Font getFontText() {
        // A definir
        return new Font("Serif", Font.PLAIN, 16);
    }

    /**
     * @return Retorna l'altura dels elements de les vistes de llista
     */
    public static int getElementHeight() {
        return elementHeight;
    }

    /**
     * @return Retorna el color de fons de les vistes
     */
    public static Color getBackgroundColorVista() {
        // A redefinir
        return Color.white;
    }

    /**
     * @return Retorna el color de fons dels elements
     */
    public static Color getBackgroundColorElement() {
        // A redefinir
        return Color.lightGray;
    }

    //potser privat
    /**
     * @param imatge nom del fitxer de la imatge
     * @return retorna la imatge com a ImageIcon
     */
    public static ImageIcon getImage(String imatge) {
        // descarregar imatge + path + definir nom carpeta
        String path = ".." + File.separator + ".." + File.separator + "data/imatges/" + imatge + ".png";
        return new ImageIcon(path);
    }

    /**
     * @param name nom del JLabel
     * @param type tipus de JLabel (title o text)
     * @return Retorna un JLabel amb el nom i tipus especificats
     */
    public static JLabel initLabel(String name, String type) {
        JLabel label = new JLabel();
        label.setText(name);
        label.setVisible(true);
        if (type.equals("title")) label.setFont(getFontTitle());
        else if (type.equals("text")) label.setFont(getFontText());
        return label;
    }

    /**
     * @param title títol del JFrame
     * @return Retorna un JFrame amb el títol especificat
     */
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

    /**
     * @param text text del JButton
     * @param imatge nom del fitxer de la imatge
     * @return Retorna un JButton amb el text o imatge especificats
     */
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

    /**
     * @param mgr LayoutManager del JPanel
     * @param preferredSize dimensions del JPanel
     * @return Retorna un JPanel amb el LayoutManager i les dimensions
     */
    public static JPanel JPanel(LayoutManager mgr, Dimension preferredSize) {
        JPanel panel = new JPanel();
        if (mgr != null) panel.setLayout(mgr);
        if (preferredSize != null) panel.setPreferredSize(preferredSize);
        return panel;
    }

    // Tanca la pantalla actual i mostra la nova pantalla
    /**
     * Realitza el canvi de pantalla cridant a CtrlPresentacio
     * @param vista pantalla actual
     * @param pantalla nom de la pantalla a la que es vol canviar
     */
    public static void canviPantalla(Window vista, String pantalla) {
        vista.dispose();
        CtrlPresentacio.getInstance().canviVista(pantalla, null);
    }

    // pantalla que necessita algun parametre addicional
    /**
     * Realitza el canvi de pantalla cridant a CtrlPresentacio
     * @param vista pantalla actual
     * @param pantalla nom de la pantalla a la que es vol canviar
     * @param parametresPantalla informació addicional que necessita la pantalla a la que es vol canviar
     */
    public static void canviPantallaElementMostrar(JFrame vista, String pantalla, String parametresPantalla) {
        vista.dispose();
        if (parametresPantalla != null) {
            CtrlPresentacio.getInstance().canviVista(pantalla, parametresPantalla);
        }
        else {
            // salta EXC
        }
    }

}
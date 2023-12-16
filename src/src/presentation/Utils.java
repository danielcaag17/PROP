package src.presentation;

import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;

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

    public static Font getFontTitle() {
        // A definir
        return new Font("Serif", Font.BOLD, 30);
    }

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

    public static ImageIcon getReturnImage() {
        // descarregar imatge + path + definir nom carpeta
        String path = ".." + File.separator + ".." + File.separator + "data/imatges/backArrow.png";
        return new ImageIcon(path);
    }
}

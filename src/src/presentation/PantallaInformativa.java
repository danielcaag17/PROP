package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PantallaInformativa {
    public PantallaInformativa(JFrame pantalla, String title, String msg) {
        JDialog dialog = new JDialog(pantalla, "NoEsPotCrearTeclat", true);
        dialog.setLayout(new BorderLayout());
        JButton button = Utils.Button("OK", null);
        dialog.add(new JLabel(msg), BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);
        dialog.setSize(new Dimension(420,200));
        dialog.setLocationRelativeTo(pantalla);
        dialog.setVisible(true);
        dialog.setResizable(false);
        
    }
}

package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PantallaInformativa {
    private JDialog dialog;

    public PantallaInformativa(JFrame pantalla, String title, String msg) {
        dialog = new JDialog(pantalla, "NoEsPotCrearTeclat", true);
        dialog.setLayout(new BorderLayout());
        JButton button = Utils.Button("OK", null);
        button.addActionListener(e -> close());
        dialog.add(new JLabel(msg), BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);
        dialog.setSize(new Dimension(420,200));
        dialog.setLocationRelativeTo(pantalla);
        dialog.setVisible(true);
        dialog.setResizable(false);
    }

    private void close(){
        dialog.dispose();
    }
}

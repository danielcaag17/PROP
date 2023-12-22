package src.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class CtrlPantallaInformativa {
    private JDialog dialog;

    public CtrlPantallaInformativa(JFrame pantalla, String title, String msg) {        
        JLabel label = Utils.initLabel(msg, "text");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        JButton button = Utils.Button("OK", null);
        button.addActionListener(e -> close());
        JPanel POKButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        POKButton.add(button);

        dialog = new JDialog(pantalla, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.add(label, BorderLayout.CENTER);
        dialog.add(POKButton, BorderLayout.SOUTH);
        dialog.setSize(new Dimension(420,200));
        dialog.setLocationRelativeTo(pantalla);
        dialog.setVisible(true);
        dialog.setResizable(false);
    }

    private void close(){
        dialog.dispose();
    }
}

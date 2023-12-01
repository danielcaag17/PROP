import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.BlockElement;

public class Main implements ActionListener{
    static JLabel label1, label2, label3, label4;
    static JButton button;
    public static void main(String[] args) {
        label1 = new JLabel();
        label1.setVisible(false);
        label1.setText("PROP");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setBounds(0,0,1000,100);

        label2 = new JLabel();
        label2.setVisible(false);
        label2.setText("Pau Rambla");
        label2.setHorizontalAlignment(JLabel.LEFT);
        label2.setVerticalAlignment(JLabel.TOP);
        label2.setBounds(15,100,300,200);

        label3 = new JLabel();
        label3.setVisible(false);
        label3.setText("Daniel Canizares");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.TOP);
        label3.setBounds(300,100,300,200);

        label4 = new JLabel();
        label4.setVisible(false);
        label4.setText("Jordi Otal");
        label4.setHorizontalAlignment(JLabel.RIGHT);
        label4.setVerticalAlignment(JLabel.TOP);
        label4.setBounds(600,100,300,200);

        // ImageIcon imageIcon = new ImageIcon("OK.png");


        button = new JButton();
        button.setBounds(450,475,100,50);
        // posar que es canvii la vista per exemple
        // button.addActionListener(e -> System.out.println("CLICK"));
        // button.addActionListener(e -> button.setEnabled(false));
        button.addActionListener(e -> setVisible(true));

        // No funciona perque el main es una classe estatica
        // button.addActionListener(this);
        button.setText("START");
        button.setFocusable(false);
        button.setForeground(Color.getHSBColor(00,00,00));
        button.setBackground(Color.GREEN);
        button.setBorder(BorderFactory.createEtchedBorder());
        
        /* No agafa la imatge
        JButton button2 = new JButton();
        button2.setBounds(450,675,100,50);
        button2.setIcon(imageIcon);
        */

        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);
        frame.add(button);
        // frame.add(button2);
        // frame.pack();
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println("HOLA");
            label1.setVisible(true);
            label2.setVisible(true);
            label3.setVisible(true);
            label4.setVisible(true);
        }
    }

    private static void setVisible (Boolean b) {
        label1.setVisible(b);
        label2.setVisible(b);
        label3.setVisible(b);
        label4.setVisible(b);
    }
}

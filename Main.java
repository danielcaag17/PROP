import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
// import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
// import javax.swing.filechooser.FileNameExtensionFilter;

public class Main implements ActionListener{
    static JLabel label1, label2, label3, label4;
    static JButton button;
    static JMenu menu;
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        Font serifFont = new Font("Serif", Font.BOLD, 30);
        Font sansSeriFont = new Font("SansSerif", Font.PLAIN, 12);
        Font monospacedFont = new Font("Monospaced", Font.PLAIN, 12);
        Font boldFont = new Font("Arial", Font.BOLD, 12);
        Font timesNewRomanFont = new Font("Times New Roman", Font.PLAIN, 20);


        label1 = new JLabel();
        label1.setVisible(false);
        label1.setText("PROP");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setBounds(0,0,screenWidth,screenHeight/6);
        label1.setFont(serifFont);

        label2 = new JLabel();
        label2.setVisible(false);
        label2.setText("Pau Rambla");
        label2.setHorizontalAlignment(JLabel.LEFT);
        label2.setVerticalAlignment(JLabel.TOP);
        label2.setBounds(0,screenHeight/3,screenWidth/3,2*screenHeight/3);
        label2.setFont(serifFont);

        label3 = new JLabel();
        label3.setVisible(false);
        label3.setText("Daniel Canizares");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.TOP);
        label3.setBounds(screenWidth/3,screenHeight/3,screenWidth/3,2*screenHeight/3);
        label3.setFont(serifFont);

        label4 = new JLabel();
        label4.setVisible(false);
        label4.setText("Jordi Otal");
        label4.setHorizontalAlignment(JLabel.RIGHT);
        label4.setVerticalAlignment(JLabel.TOP);
        label4.setBounds(2*screenWidth/3,screenHeight/3,(screenWidth/3),2*screenHeight/3);
        label4.setFont(serifFont);

        // ImageIcon imageIcon = new ImageIcon("OK.png");


        button = new JButton();
        button.setBounds((screenWidth/2) - 50, (2 * screenHeight/3),100,50);
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

        /*
        JButton buttonA = new JButton();
        JButton buttonB = new JButton();
        buttonA.setText("A");
        buttonA.setBounds(100,100,100,100);
        buttonA.setSelected(true);
        buttonB.setText("B");
        buttonB.setBounds(200,100,100,100);
        */

        // JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem();
        // checkBox.setBounds(0,0,20,20);
        // checkBox.setBounds(0,0,100,100);

        /*
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        }*/

        
        JFrame frame = new JFrame();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);
        frame.add(button);
        // frame.add(checkBox);
        // frame.add(colorChooser);
        // frame.add(buttonA);
        // frame.add(buttonB);
        // frame.add(button2);
        // frame.add(menu);
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
        menu = new JMenu();
        menu.setBounds(0,0,500,500);
    }
}

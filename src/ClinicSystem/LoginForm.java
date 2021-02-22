package ClinicSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginForm extends JPanel{

    private JPanel panel1;
    private JPasswordField passwordField;
    private JTextField textField2;
    private JButton cancelButton;
    private JButton loginButton;
    private JLabel PicLabel;
    private JLabel imgLabel;
    BufferedImage img;

    public LoginForm() {
        PicLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        PicLabel.setBounds(new Rectangle(256, 256));
        PicLabel.setBackground(Color.gray);

        passwordField.setBorder(BorderFactory.createRaisedBevelBorder());
        textField2.setBorder(BorderFactory.createRaisedBevelBorder());
        textField2.setFont(new Font("Times New Roman", Font.BOLD, 16));
        passwordField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JFrame frame = new JFrame("Login");
        //frame.setSize(500,500);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      //  panel1.setLayout(new BorderLayout());

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField2.getText().equals("ShafiqueAhmed") && passwordField.getText().equals("admin")){
                    frame.setVisible(false);

                    PatientRegistration.Main(null);
                }
                else{
                    JOptionPane.showMessageDialog(panel1, "Invalid Login");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(panel1, "Do you really want to exit?");
                if(opt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
    }


}

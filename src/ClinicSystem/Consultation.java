package ClinicSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Consultation {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton printButton;
    private JButton totalButton1;
    int clicks  = 0;
    public Consultation(JFrame fr, String mr){
        fr.setVisible(false);
        JFrame frame = new JFrame("Payment");
        frame.setSize(420, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setResizable(false);
        frame.setVisible(true);
        textField5.setText("3000");
        textField4.setText("0");
        printButton.setEnabled(false);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintSlip(textField5.getText(), textField4.getText(), textField2.getText(), textField3.getText(), mr);
                frame.setVisible(false);
                PatientRegistration.main(null);
            }
        });
        totalButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBill();
            }


        });
        textField4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(Character.isDigit(ch) || ch == '\b'){
                    textField4.setEditable(true);
                }
                else if(ch == '\n'){
                        calculateBill();
                }
                else{
                    textField4.setEditable(false);
                }
            }
        });
    }
    public void calculateBill() {
        int total = 0; 
        try {
            int price = Integer.parseInt(textField5.getText());
            int pftPrice = Integer.parseInt(textField4.getText());
             total = price + pftPrice;
            textField1.setText(total + "");
        } catch (Exception exp){
            JOptionPane.showMessageDialog(null, "Please type 0 if PFT fee is not included.");
        }
        String payment = JOptionPane.showInputDialog("Your Total: " + total + "\nEnter Patient Payment Here:");
        try {
            int paymentAmount = Integer.parseInt(payment);
            textField2.setText(paymentAmount + "");
            textField3.setText(paymentAmount - total + "");
            printButton.setEnabled(true);
            // JOptionPane.showMessageDialog(null, "You slip is printed");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Please enter amount correctly");
        }
    }
}

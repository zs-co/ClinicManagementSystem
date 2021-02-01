package ClinicSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PatientRegistration {
    private String patientName, Father_name, husband_name, PatientAddress, patientPhoneNumber;
    private boolean New = false, followUp = false;
    private int age;
    int serialNum = 0;

    private JPanel mainPane;
    private JTextField MrField;
    private JTextField LastEntryField;
    private JTextField sessionEntries;
    private JTextField nameBox;
    private JRadioButton MRRadioButton;
    private JRadioButton misRadioButton;
    private JRadioButton MRSRadioButton;
    private JTextField spouseNameBox;
    private JCheckBox FOLLOWUPCheckBox;
    private JCheckBox NEWCheckBox;
    private JTextField AddressBox;
    private JTextField phoneNumBox;
    private JButton SAVEButton;
    private JButton CANCELButton;
    private JButton SUMMARYButton1;
    private JTextArea ageField;

    String fileName = "Patients.csv";
    int entries = 0;
    public PatientRegistration(){

        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessionEntries.setText("0");
        ButtonGroup group = new ButtonGroup();
        group.add(misRadioButton);
        group.add(MRRadioButton);
        group.add(MRSRadioButton);
        String data = mrGenerator(fileName);
        String[] dataSplitforMr = data.split(",");
        MrField.setText(dataSplitforMr[1]);

        MrField.setEditable(false);
        LastEntryField.setEditable(false);
        ButtonGroup newGroup = new ButtonGroup();
        newGroup.add(NEWCheckBox);
        newGroup.add(FOLLOWUPCheckBox);
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String time = dtf.format(now);

                LastEntryField.setText(time);
                ++entries;
                sessionEntries.setText(entries + "");
                if(MRRadioButton.isSelected())
                    patientName = "MR.";
                else if(MRSRadioButton.isSelected())
                    patientName = "MRS.";
                else if(misRadioButton.isSelected())
                    patientName = "Mis.";
                else
                    JOptionPane.showMessageDialog(null, "Please select MR/Mis/MRS");
                try {
                    FileWriter writer = new FileWriter(fileName, true);

                    String data = mrGenerator(fileName);
                    String[] dataSplitforMr = data.split(",");
                    MrField.setText(dataSplitforMr[1]);
                    data += "," + patientName+nameBox.getText() + "," + ageField.getText();
                    data += "," + AddressBox.getText() + "," + phoneNumBox.getText() + "," + time;
                    if(NEWCheckBox.isSelected()) {
                        data += "," + "New" + '\n';
                        New = true;
                    }
                    else if(FOLLOWUPCheckBox.isSelected()) {
                        data += "," + "F/up" + '\n';
                        followUp = true;
                    }else
                        JOptionPane.showMessageDialog(null, "Please select new or Follow Up");
                    if(!(patientName.equals(null)) && (New == true || followUp == true)){
                        writer.write(data);
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Patient is successfully registered");
                    }


                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });



        ageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(Character.isDigit(ch) || ch == '\b'){
                    ageField.setEditable(true);
                }
                else
                    ageField.setEditable(false);
            }
        });
        nameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(Character.isLetter(ch) || ch == ' ' || ch == '\b' || ch == '-'){
                    nameBox.setEditable(true);
                }
                else{
                    nameBox.setEditable(false); //Box means textfieldbox****
                }
            }
        });
        spouseNameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(Character.isLetter(ch) || ch == ' ' || '\b' == ch || ch == '-'){
                    spouseNameBox.setEditable(true);
                }
                else{
                    spouseNameBox.setEditable(false); //Box means textfieldbox****
                }
            }
        });
        phoneNumBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(Character.isDigit(ch) || ch == '\b'){
                    phoneNumBox.setEditable(true);

                }
                else{
                    phoneNumBox.setEditable(false);
                }
            }
        });
        SUMMARYButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileReader reader = new FileReader(fileName);
                    Scanner input = new Scanner(reader);
                    while(input.hasNextLine()){
                        String data = input.nextLine();
                        String[] separated = data.split(",");
                        System.out.println(data);
                        System.out.println(separated[6]);
                    }
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }

            }
        });
    }

    public String mrGenerator(String filepath){
        int sr_number = 0;
        int midValue = 0, lastValue = 0; // hc-00(midValue)-000(lastValue)
        String data = "";
        String formatString = "";

        FileReader reader = null;
        try {
            reader = new FileReader(filepath);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        Scanner inputFromFile = new Scanner(reader);
        int lines = 0;
        int indexLoop = 0;
        while (true) {
            if (inputFromFile.hasNextLine()) {
                String Data = inputFromFile.nextLine();
                String[] wholeLineSplit = Data.split(",");
                if (wholeLineSplit.length == 8) {
                    String[] MrSplit = wholeLineSplit[1].split("-");
                    midValue = Integer.parseInt(MrSplit[1]);
                    lastValue = Integer.parseInt(MrSplit[2]);
                }
                lines++;

            } else {
                break;
            }
            sr_number = lines;
        }

        if(lastValue == 1000) {
            midValue++;
            lastValue = 0 ;
            int lastFigures = 0; // HC-01-000(Lastfigures)
            if(midValue < 10) {
                formatString = "HC-0" + midValue + "-" +"000";
            }
            else if(midValue > 9){
                formatString = "HC-" + midValue + "-" + "000";
            }

        }else if(lastValue < 1000){
            lastValue++;
            if(lastValue < 10)
                if(midValue < 1)
                    formatString = "HC-00-00" + lastValue;
                else if(midValue > 0 && midValue < 10)
                    formatString = "HC-0" + midValue + "-" + lastValue;
                else
                    formatString = "HC-" + midValue + "-" + lastValue;
            else if(lastValue < 99)
                if(midValue < 1)
                    formatString = "HC-00-0" + lastValue;
                else if(midValue > 0 && midValue < 10)
                    formatString = "HC-0" + midValue + "-" + lastValue;
                else
                    formatString = "HC-" + midValue + "-" + lastValue;


            else
            if(midValue < 1)
                formatString = "HC-00-" + lastValue;
            else if(midValue > 0 && midValue < 10)
                formatString = "HC-0" + midValue + "-" + lastValue;
            else
                formatString = "HC-" + midValue + "-" + lastValue;

        }
        ++sr_number;
        data = sr_number + "," + formatString;


        return data;
    }

    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 try {
                     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                 } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                 } catch (InstantiationException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 } catch (UnsupportedLookAndFeelException e) {
                     e.printStackTrace();
                 }
                 JFrame frame = new JFrame("Patient Registration");
                 frame.setSize(1024,768);
                 frame.setContentPane(new PatientRegistration().mainPane);
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 frame.setVisible(true);

             }
         });
    }
}

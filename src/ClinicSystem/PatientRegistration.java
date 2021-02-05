package ClinicSystem;

/*Classes import*/

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

/*Main Class bind with page Registration form*/

public class PatientRegistration {

    //Basic variable declaration not used everywhere...

    private String patientName, Father_name, husband_name, PatientAddress, patientPhoneNumber;
    private boolean New = false, followUp = false;
    private int age;
    int serialNum = 0;

    /******************************************
                components declaration
     *****************************************/

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
    private JComboBox comboBox1;
    private JTextField dateHere;
    private JButton PAYMENTSUMMARYButton;
    private JPanel backGround;

    /*****************************
        File and indexing variables
      *****************************/

        int index = -1;
        String fileName = "Patients.csv";
        static int entries = 0;

    /***************Constructor******************/

    public PatientRegistration(JFrame frame) {

        File file = new File(fileName); // File creates
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessionEntries.setText("0");
        /*******************************
         *        Grouping buttons     *
         *******************************/

        ButtonGroup newGroup = new ButtonGroup();
        newGroup.add(NEWCheckBox);
        newGroup.add(FOLLOWUPCheckBox);

        /*
        mrGenerator generates formatted MR# HC-00-000
        returns serial Number and Mr Number from last line of file
         */

        String[] data = mrGenerator(fileName);
        //data = sr,Mr#,date
        MrField.setText(data[1]);              //MRField = Mr#
        sessionEntries.setEditable(false);
        nameBox.setEditable(false);
        LastEntryField.setText(data[2]);
        sessionEntries.setText(entries + "");

        /*
        adding items to combo box
         */
        comboBox1.addItem("Mis");
        comboBox1.addItem("Mr");
        comboBox1.addItem("Mrs");

        //setting combo box selection to null
        comboBox1.setSelectedIndex(-1);
        //By default nothing is selected.
        MrField.setEditable(false);
        LastEntryField.setEditable(false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        time = time.substring(0, 10);
        dateHere.setText(time);
        dateHere.setEditable(false);
        ++entries;
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String time = dtf.format(now);


                try {
                    FileWriter writer = new FileWriter(fileName, true);
                    if (nameBox.getText().equals("") || ageField.getText().equals("") || spouseNameBox.getText().equals("")
                            || phoneNumBox.getText().equals("") || AddressBox.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Insufficient information is provided",
                                "Error!", JOptionPane.ERROR_MESSAGE);

                    } else {
                        String newData = "";
                        String[] requiredData = mrGenerator(fileName);
                        MrField.setText(requiredData[1]);
                        newData = requiredData[0];
                        newData += "," + requiredData[1];
                        newData += "," + nameBox.getText() + "," + spouseNameBox.getText() + "," + ageField.getText();
                        newData += "," + AddressBox.getText() + "," + phoneNumBox.getText() + "," + time;
                        if (NEWCheckBox.isSelected()) {
                            newData += "," + "New(initial)" + '\n';
                            New = true;
                        } else if (FOLLOWUPCheckBox.isSelected()) {
                            newData += "," + "F/up" + '\n';
                            followUp = true;
                        }
                        writer.write(newData);
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Patient is successfully Registered",
                                "Registration", JOptionPane.PLAIN_MESSAGE);

                        entries++;
                        ageField.setText("");
                        nameBox.setText("");
                        AddressBox.setText("");
                        ageField.setText("");
                        spouseNameBox.setText("");
                        phoneNumBox.setText("");
                        NEWCheckBox.setSelected(false);
                        FOLLOWUPCheckBox.setSelected(false);
                        nameBox.setEditable(false);
                        comboBox1.setSelectedIndex(-1);
                        LastEntryField.setText(time);


                        new Consultation(frame, requiredData[1]);
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
                if (Character.isDigit(ch) || ch == '\b') {
                    ageField.setEditable(true);
                } else
                    ageField.setEditable(false);
            }
        });
        nameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                index = -1;
                char ch = e.getKeyChar();
                index = comboBox1.getSelectedIndex();
                if (index != -1) {


                    if (Character.isLetter(ch) || ch == ' ' || ch == '\b' || ch == '-') {
                        nameBox.setEditable(true);
                    } else {
                        nameBox.setEditable(false); //Box means textfieldbox****
                    }
                }
            }
        });
        spouseNameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if (Character.isLetter(ch) || ch == ' ' || '\b' == ch || ch == '-') {
                    spouseNameBox.setEditable(true);
                } else {
                    spouseNameBox.setEditable(false); //Box means textfieldbox****
                }
            }
        });
        phoneNumBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if (Character.isDigit(ch) || ch == '\b') {
                    phoneNumBox.setEditable(true);

                } else {
                    phoneNumBox.setEditable(false);
                }
            }
        });
        SUMMARYButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                if (entries != 0)
                    --entries;
                else
                    entries = 0;
                new Summary();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedIndex() == 0) {


                    nameBox.setText("Mis.");


                } else if (comboBox1.getSelectedIndex() == 1) {

                    nameBox.setText("Mr.");

                } else if (comboBox1.getSelectedIndex() == 2) {
                    nameBox.setText("Mrs.");

                } else {
                    nameBox.setEditable(false);
                }
                index = comboBox1.getSelectedIndex();
                nameBox.setEditable(true);
            }
        });
        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Do you really want to exit?");
                if (opt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        backGround.addFocusListener(new FocusAdapter() {
        });
    }

    public String[] mrGenerator(String filepath) {
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
        String lastEntry = "";
        while (true) {
            if (inputFromFile.hasNextLine()) {
                String Data = inputFromFile.nextLine();
                String[] wholeLineSplit = Data.split(",");
                lastEntry = wholeLineSplit[7];
                if (wholeLineSplit.length == 9) {
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

        if (lastValue == 1000) {
            midValue++;
            lastValue = 0;
            int lastFigures = 0; // HC-01-000(Lastfigures)
            if (midValue < 10) {
                formatString = "HC-0" + midValue + "-" + "000";
            } else if (midValue > 9) {
                formatString = "HC-" + midValue + "-" + "000";
            }

        } else if (lastValue < 1000) {
            lastValue++;
            if (lastValue < 10)
                if (midValue < 1)
                    formatString = "HC-00-00" + lastValue;
                else if (midValue > 0 && midValue < 10)
                    formatString = "HC-0" + midValue + "-" + lastValue;
                else
                    formatString = "HC-" + midValue + "-" + lastValue;
            else if (lastValue <= 99)
                if (midValue < 1)
                    formatString = "HC-00-0" + lastValue;
                else if (midValue > 0 && midValue < 10)
                    formatString = "HC-0" + midValue + "-" + lastValue;
                else
                    formatString = "HC-" + midValue + "-" + lastValue;


            else if (midValue < 1)
                formatString = "HC-00-" + lastValue;
            else if (midValue > 0 && midValue < 10)
                formatString = "HC-0" + midValue + "-" + lastValue;
            else
                formatString = "HC-" + midValue + "-" + lastValue;

        }
        ++sr_number;
        String[] Data = {String.valueOf(sr_number),formatString ,lastEntry};


        return Data;
    }

    public static void main(String[] args) {
        /*
        try {
            FileWriter file = new FileWriter("Patients.csv");
            file.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    // If Nimbus is not available, fall back to cross-platform
                    try {
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    } catch (Exception ex) {
                        // Not worth my time
                    }
                }

                JFrame frame = new JFrame("Patient Registration");
                frame.setSize(1024, 768);
                frame.setLayout(null);
                frame.setContentPane(new PatientRegistration(frame).mainPane);
                frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

            }
        });

    }
}



package ClinicSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Consultation {
    private JPanel mainPane;
    private JTable entryTable;
    private JButton BACKButton;
    private JRadioButton d5RadioButton;
    private JRadioButton d1RadioButton;
    private JRadioButton PFTRefundRadioButton;
    private JRadioButton freeRadioButton;
    private JRadioButton noDiscountRadioButton;
    private JRadioButton a50RadioButton;
    double discount = 0;
    String discountType = "No discount";
    public Consultation(JFrame fr, String mr, String n, String a, String p, String v) {
        fr.setVisible(false);
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {"Sr.", "Title", "Rate"});
        entryTable.setModel(model);
        TableColumn col = entryTable.getColumn("Sr.");
        col.setMaxWidth(40);
        col = entryTable.getColumn("Title");
        col.setMinWidth(300);

        ButtonGroup group = new ButtonGroup();
        group.add(freeRadioButton);
        group.add(noDiscountRadioButton);
        group.add(d1RadioButton);
        group.add(d5RadioButton);
        group.add(PFTRefundRadioButton);
        group.add(a50RadioButton);

        col = entryTable.getColumn("Rate");
        col.setMinWidth(80);
        JTableHeader header = entryTable.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.gray);
        header.setForeground(Color.BLACK);

        model.insertRow(0, new Object[] {"1", "Consultation Fee Dr. Shazli Manzoor", "Rs.3000/-"});
        model.insertRow(1, new Object[] {"2", "Pulmonary Function Test Fee", "Rs.1000/-"});
        model.insertRow(2, new Object[] {"3", "Consultation + PFT" , "RS.4000/-"});
        JFrame frame = new JFrame("Consultation Payment");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(mainPane);
        frame.setResizable(true);
        frame.setVisible(true);
        String fileName = "Payments.csv";

        File file = new File(fileName);
        try {
            file.createNewFile();

        } catch ( IOException exception){
            //
        }
        BACKButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(ch == '\n'){
                    frame.setVisible(false);
                    PatientRegistration.Main(null);
                }
            }
        });
        entryTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {


                if(freeRadioButton.isSelected())
                    discountType = "(Free)";
                else if(d5RadioButton.isSelected())
                    discountType = "(D5)";
                else if(d1RadioButton.isSelected())
                    discountType = "(D1)";
                else if(a50RadioButton.isSelected())
                    discountType = "(50%)";
                else if(PFTRefundRadioButton.isSelected())
                    discountType = "(PFT refund)";
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();


                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

                    row = table.getSelectedRow();
                    String amount = "";
                    String pft = "";
                    double d = 0;
                    if(row == 0){
                        if(noDiscountRadioButton.isSelected()) {
                            discount = 0;
                        }
                        else if(d1RadioButton.isSelected()){
                            discount = 1000;

                        }
                        else if(d5RadioButton.isSelected()){
                            discount = 500;
                        }
                        else if(a50RadioButton.isSelected()){
                            discount = 1500;
                        }
                        else if(PFTRefundRadioButton.isSelected()){
                            discount = 1100;
                        }
                        else{
                            discount = 3000;
                        }
                        amount = "3000";
                        pft = "0";
                    }
                    else if (row == 1){
                        if(noDiscountRadioButton.isSelected()) {
                            discount = 0;
                        }
                        else if(d1RadioButton.isSelected()){
                            discount = 1000;

                        }
                        else if(d5RadioButton.isSelected()){
                            discount = 500;
                        }
                        else if(a50RadioButton.isSelected()){
                            discount = 1500;
                        }
                        else if(PFTRefundRadioButton.isSelected()){
                            discount = 1100;
                        }
                        else {
                            discount = 3000;
                        }
                        amount = "0";
                        pft = "1000";
                    }
                    else{
                        if(noDiscountRadioButton.isSelected()) {
                            discount = 0;
                        }
                        else if(d1RadioButton.isSelected()){
                            discount = 1000;

                        }
                        else if(d5RadioButton.isSelected()){
                            discount = 500;
                        }
                        else if(a50RadioButton.isSelected()){
                            discount = 1500;
                        }
                        else if(PFTRefundRadioButton.isSelected()){
                            discount = 1100;
                             d = 1000 ;
                        }
                        else if(freeRadioButton.isSelected()){
                            discount = 3000;
                        }
                        amount = "3000";
                        pft = "1000";
                    }

                    int opt = JOptionPane.showConfirmDialog(null, "Do you want to take print?");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);
                    String date = time.substring(0, 10);
                    d = discount;
                    if (d == 1100)
                        d = 1000;
                    String Amount = String.valueOf(Double.parseDouble(amount) + Double.parseDouble(pft) - d);

                    FileWriter writer = null;
                    if(file.length() == 0) {
                        try {
                            writer = new FileWriter(fileName, true);
                            String data = time + "," + mr + "," + n + "," + Amount + "," + discountType+  '\n';
                            writer.write(data);
                           // JOptionPane.showMessageDialog(null, "Successfully saved");
                            writer.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }else {
                        //File updates from here..
                        //Temp file creation,
                        File file = new File("temp.txt");
                        try {
                            file.createNewFile();


                        } catch (Exception ext) {
                            ext.printStackTrace();
                        }

                        //Both files open for read and write..
                        //temp for write..
                        //Payment.csv for read

                        try {
                            String writeToFile = "";
                            FileReader reader = new FileReader(fileName);
                            FileWriter writer1 = new FileWriter("temp.txt", true);
                            Scanner readFromFile = new Scanner(reader);
                            boolean foundInFile = false;
                            while (readFromFile.hasNextLine()) {
                                String data = readFromFile.nextLine();

                                String[] br = data.split(",");
                                if (br.length > 3) {
                                    if (br[0].substring(0, 10).equals(date) && br[1].equals(mr)) {
                                       br[3] = Amount;
                                       br[4] = discountType;
                                        foundInFile = true;

                                    }
                                        writeToFile = br[0] + "," + br[1] + "," + br[2] + "," + br[3] + ","  + br[4] + '\n';
                                        writer1.write(writeToFile);

                                }

                            }
                            if(foundInFile == false) {
                                String newRecord = time + "," + mr + "," + n + "," + Amount + "," + discountType + '\n';
                                writer1.write(newRecord);
                            }

                            reader.close();
                            writer1.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        File oldFile = new File("temp.txt");
                        File newFile = new File(fileName);
                        newFile.delete();
                        oldFile.renameTo(newFile);


                    }

                    if(opt == JOptionPane.YES_OPTION)
                        new PrintSlipForm(mr, n, a,p, v, amount, pft, discount);
                    else if(opt == JOptionPane.NO_OPTION)
                        PatientRegistration.Main(null);
                    frame.setVisible(false);
                }
            }
        });
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                PatientRegistration.Main(null);
            }
        });
    }


}

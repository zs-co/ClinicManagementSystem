package ClinicSystem;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.*;
import java.nio.channels.FileLockInterruptionException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Summary {
    private JButton dailySummaryButton;
    private JButton monthlySummaryButton;
    private JTable table1;
    private JButton backButton;
    private JPanel SummaryPane;
    private JButton searchButton;
    private JTextField textField1;
    DefaultTableModel model = new DefaultTableModel();
    String hintText = "ph. or Mr# here";
    public Summary(){
        JFrame fr = new JFrame("Patient Records");
        fr.setSize(1024,600);
        model.setColumnIdentifiers(new Object[] {"sr.", "MR No.", "Patient Name", "Father/Husband Name", "Age", "Address","Phone Number",
                                                    "Visited at", "Visit Status"});
        table1.setModel(model);
        JTableHeader header = table1.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.BLACK);
        Font font = new Font("Arial", Font.ROMAN_BASELINE,14);
        header.setBackground(Color.gray);
        header.setFont(font);
        table1.setTableHeader(header);
        fr.setContentPane(SummaryPane);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        textField1.setForeground(Color.gray);

        textField1.setText(hintText);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(false);
                PatientRegistration.main(null);
            }
        });
        dailySummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSummary("today");
                dailySummaryButton.setEnabled(false);
                monthlySummaryButton.setEnabled(true);

            }
        });
        monthlySummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthlySummaryButton.setEnabled(false);
                dailySummaryButton.setEnabled(true);
                showSummary("monthly");
            }
            });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dailySummaryButton.setEnabled(true);
                monthlySummaryButton.setEnabled(true);
                searchPatient(textField1.getText());
            }
        });

        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(textField1.getText().equals(hintText)){
                    textField1.setText("");
                }
                textField1.setEditable(true);
                textField1.setForeground(Color.black);
            }
        });



        table1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

                     row = table.getSelectedRow();
                     String mrNum = (String) table.getValueAt(row, 1);

                    new Consultation(fr, mrNum);
                }
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(ch == '\n'){
                    searchPatient(textField1.getText());
                }
            }
        });
    }

    public void showSummary(String Today_or_Monthly){
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.setRowCount(0);
        table1.setGridColor(Color.BLACK);
        int lastIndex = 0;
        if(Today_or_Monthly.equals("today"))
            lastIndex = 10;
        else if(Today_or_Monthly.equals("monthly"))
            lastIndex = 7;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            String dayToday = time.substring(0, lastIndex);
            TableColumn index = table1.getColumn("sr.");
            index.setMaxWidth(40);
            index = table1.getColumn("MR No.");
            index.setMaxWidth(100);
            index = table1.getColumn("Age");
            index.setMaxWidth(40);


            FileReader reader = new FileReader("Patients.csv");
            Scanner inputFromFile = new Scanner(reader);
            while (inputFromFile.hasNextLine()) {
                String record = inputFromFile.nextLine();
                if (record.length() > 1) {
                    String[] splitRecord = record.split(",");
                    String date = splitRecord[7];
                    date = date.substring(0, lastIndex);
                    if (date.equals(dayToday)) {

                        String[] row = {splitRecord[0], splitRecord[1], splitRecord[2], splitRecord[3], splitRecord[4],
                                splitRecord[5], splitRecord[6], splitRecord[7], splitRecord[8]};
                        model.addRow(row);
                    }

                }
            }
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void searchPatient(String mrNo){
        try {
            boolean found = true;
            FileReader reader = new FileReader("Patients.csv");
            Scanner input = new Scanner(reader);
            while(input.hasNextLine()){
                String data = input.nextLine();
                if(data.length() > 1){
                    String[] splitRecord = data.split(",");
                    String mrNum = splitRecord[1];
                    String getPhNum = splitRecord[6];
                    if(mrNo.equalsIgnoreCase(getPhNum) || mrNo.equalsIgnoreCase(mrNum)){
                        model.setRowCount(0);
                        String[] row = {splitRecord[0], splitRecord[1], splitRecord[2], splitRecord[3], splitRecord[4],
                                splitRecord[5], splitRecord[6], splitRecord[7], splitRecord[8]};
                        model.addRow(row);
                        found = true;
                        break;

                    }
                    else
                        found = false;
                }
            }
            if(found == false){
                JOptionPane.showMessageDialog(null, "Sorry! We couldn't find patient(s)");
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

    }
}

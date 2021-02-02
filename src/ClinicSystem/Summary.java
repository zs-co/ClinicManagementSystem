package ClinicSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Summary {
    private JButton dailySummaryButton;
    private JButton monthlySummaryButton;
    private JTable table1;
    private JButton backButton;
    private JPanel SummaryPane;
DefaultTableModel model = new DefaultTableModel();
    public Summary(){
        JFrame fr = new JFrame("Summary!");
        fr.setSize(800,600);
        model.setColumnIdentifiers(new Object[] {"sr#", "Mr#", "Name", "Father/Husband", "Age", "Address","Phone Number",
                                                    "Date of Visit", "Visit Status"});
        table1.setModel(model);
        fr.setContentPane(SummaryPane);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);

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
                DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
                dtm.setRowCount(0);

                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);
                    String dayToday = time.substring(0,10);
                    TableColumn index = table1.getColumn("sr#");
                    index.setMaxWidth(30);
                    index = table1.getColumn("Mr#");
                    index.setMaxWidth(100);
                    index = table1.getColumn("Age");
                    index.setMaxWidth(40);


                    FileReader reader =new FileReader("Patients.csv");
                    Scanner inputFromFile = new Scanner(reader);
                    while(inputFromFile.hasNextLine()){
                        String record = inputFromFile.nextLine();
                        if(record.length() > 1){
                            String[] splitRecord = record.split(",");
                            String date = splitRecord[7];
                            date = date.substring(0,10);
                            if(date.equals(dayToday)){
                            String[] row = {splitRecord[0], splitRecord[1], splitRecord[2], splitRecord[3],splitRecord[4],
                            splitRecord[5],splitRecord[6], splitRecord[7], splitRecord[8]};
                            model.addRow(row);
                            }

                        }
                    }
                    dailySummaryButton.setEnabled(false);
                    monthlySummaryButton.setEnabled(true);

                    reader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });
        monthlySummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthlySummaryButton.setEnabled(false);
                dailySummaryButton.setEnabled(true);
                DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
                dtm.setRowCount(0);
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);
                    String dayToday = time.substring(0, 7);
                    TableColumn index = table1.getColumn("sr#");
                    index.setMaxWidth(30);
                    index = table1.getColumn("Mr#");
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
                            date = date.substring(0, 7);
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
            });
    }
}

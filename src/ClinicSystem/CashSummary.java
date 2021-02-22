package ClinicSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CashSummary extends Component {
    private JPanel mainpane;
    private JTable table1;
    private JButton backButton;
    private JButton printButton;
    private JScrollPane tablepanel;
    private JButton clearSummaryButton;
    private JButton showSummaryButton;
    int serialNum = 0;
    double GrandTotal = 0;
    DefaultTableModel model = new DefaultTableModel();
    public CashSummary(){

        model.setColumnIdentifiers(new String[] {"Sr.", "\tDate" , "\tMr Number", "\tPatient name", "Amount + PFT(Including Discounts)", "\tDiscount Type"});
        table1.setModel(model);
        table1.setOpaque(false);
        JTableHeader header = table1.getTableHeader();
        header.setForeground(Color.black);
        header.setOpaque(false);
        header.setBackground(Color.gray);
        Font font = new Font("Times New Roman", Font.PLAIN, 16);
        header.setFont(font);
        TableColumn col = table1.getColumn("Sr.");
        col.setMaxWidth(40);
        table1.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 14));
        col = table1.getColumn("\tDate");
        col.setMinWidth(100);
        table1.setGridColor(Color.gray);

        try {
            FileReader reader = new FileReader("Payments.csv");
            Scanner inputFromFile = new Scanner(reader);
            while(inputFromFile.hasNextLine()){
                String data = inputFromFile.nextLine();
                ++serialNum;
                try{
                    String[] splitData = data.split(",");
                    String[] row = {String.valueOf(serialNum), splitData[0], splitData[1],splitData[2], splitData[3], splitData[4]};
                    GrandTotal += Double.parseDouble(splitData[3]);
                    model.addRow(row);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            reader.close();

            String totalingRow[] = {"", "", "", "", "Grand Total = " + String.valueOf(GrandTotal), ""};
            model.insertRow(serialNum, totalingRow);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        table1.setModel(model);
        JFrame frame = new JFrame("Cash Summary");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(600, 600);
        frame.setContentPane(mainpane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                PatientRegistration.Main(null);
            }
        });
        backButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch =e.getKeyChar();
                if(ch == '\n'){
                    frame.setVisible(false);
                    PatientRegistration.Main(null);
                }
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                printRecord(tablepanel);

            }
        });
        clearSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
            }
        });
        showSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                Show_summary();
            }
        });

    }

    private void Show_summary(){
        try {
            serialNum = 0;
            GrandTotal = 0;
            FileReader reader = new FileReader("Payments.csv");
            Scanner inputFromFile = new Scanner(reader);
            while(inputFromFile.hasNextLine()){
                String data = inputFromFile.nextLine();
                ++serialNum;
                try{
                    String[] splitData = data.split(",");
                    String[] row = {String.valueOf(serialNum), splitData[0], splitData[1],splitData[2], splitData[3], splitData[4]};
                    GrandTotal += Double.parseDouble(splitData[3]);
                    model.addRow(row);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            reader.close();

            String totalingRow[] = {"", "", "", "", "Grand Total = " + String.valueOf(GrandTotal), ""};
            model.insertRow(serialNum, totalingRow);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    private void printRecord(JScrollPane table) {
        // Create PrinterJob Here
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        // Set Printer Job Name
        printerJob.setJobName("Print Record");
        // Set Printable
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                // Check If No Printable Content
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                // Make 2D Graphics to map content
                Graphics2D graphics2D = (Graphics2D) graphics;
                // Set Graphics Translations
                // A Little Correction here Multiplication was not working so I replaced with addition
                graphics2D.translate(pageFormat.getImageableX() + 8, pageFormat.getImageableY() + 12);
                // This is a page scale. Default should be 0.3 I am using 0.5
                graphics2D.scale(0.43, 0.43);

                // Now paint panel as graphics2D
                table.paint(graphics2D);

                // return if page exists
                return Printable.PAGE_EXISTS;
            }
        });
        // Store printerDialog as boolean
        boolean returningResult = printerJob.printDialog();
        // check if dilog is showing
        if (returningResult) {
            // Use try catch exeption for failure
            try {
                // Now call print method inside printerJob to print
                printerJob.print();
            } catch (PrinterException printerException) {
                JOptionPane.showMessageDialog(this, "Print Error: " + printerException.getMessage());
            }
        }
    }


}

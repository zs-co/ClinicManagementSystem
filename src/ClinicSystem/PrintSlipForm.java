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
import java.awt.print.*;
import java.io.FileWriter;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PrintSlipForm extends Component {
    private JPanel panelToPrint;
    private JLabel mrHere;
    private JLabel nameHere;
    private JLabel ageHere;
    private JLabel phNumHere;
    private JLabel VisitStatus;
    private JTable table1;
    private JLabel PRINTTIMELabel;
    private JLabel date;
    private JPanel LogoPane;
    private JLabel timeStempHere;
    private JLabel Logo;
    private JLabel logoField;
    private JButton printButton;
    private String discount1, discount2,discount, payable, payable1, payable2;
    public PrintSlipForm(String MRno, String name, String age, String phone, String visitStat, String amount, String pft, double discount) {
        mrHere.setText(MRno);
        nameHere.setText(name);
        ageHere.setText(age);
        phNumHere.setText(phone);
        VisitStatus.setText(visitStat);
        if(discount == 1100){
            this.discount2 = String.valueOf(discount-100);
            discount = 0;
        }
        else {
            this.discount2 = "0";
        }
            this.discount1 = String.valueOf(discount);
            this.payable1 = String.valueOf(Double.parseDouble(amount) - Double.parseDouble(discount1));
            this.payable2 = (Double.parseDouble(pft) - Double.parseDouble(discount2)) + "";
            this.payable = (Double.parseDouble(payable1) + Double.parseDouble(payable2)) + "";
            this.discount = (Double.parseDouble(discount1) + Double.parseDouble(discount2)) + "";

        String total = Double.parseDouble(amount) + Double.parseDouble(pft) + "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        Date dateStamp = new Date();
        long stamp =dateStamp.getTime();
        timeStempHere.setText(stamp+"");
        PRINTTIMELabel.setText("Print Time: " + time);
        date.setText(time);
        JFrame frame = new JFrame("PrintSlipForm");
        frame.setLayout(new BorderLayout());
        JTableHeader header = table1.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.gray);
        header.setForeground(Color.BLACK);
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Title", "Performing consultant", "No", "Rate", "Discount", "Payable"});

        table1.setModel(model);


        TableColumn column = table1.getColumn("Performing consultant");
        column.setMinWidth(300);
        column = table1.getColumn("No");
        column.setMaxWidth(45);
        column = table1.getColumn("Rate");
        column.setMinWidth(100);
        column = table1.getColumn("Title");
        column.setMinWidth(150);

        String[] row1 = {"Consultation Fee", "Dr. Shazli Manzoor", "  1","Rs." + amount + "/-","Rs." + discount1 + "/-", "Rs." +payable1+ "/-"};
        String[] row2 = {"PFT Fee", "", "  2", "Rs." +pft+ "/-","Rs." + discount2+ "/-","Rs." + payable2+ "/-"};
        String[] row = {"", "", "-------", "----------", "----------", "----------"};
        String[] row3 = {"", "", "Total", "Rs." +total+ "/-", "Rs." +this.discount+"/-","Rs."  + payable+ "/-"};
        model.addRow(row1);
        model.addRow(row2);
        model.addRow(row);
        model.addRow(row3);
        frame.setContentPane(panelToPrint);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setBackground(Color.white);
        frame.setVisible(true);
        printRecord(panelToPrint);
        frame.dispose();
        PatientRegistration.Main(null);

        /*
        printButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char ch = e.getKeyChar();
                if(ch == '\n'){
                    printRecord(panelToPrint);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please Press 'Enter' to take print");
                }
            }
        });

         */
    }

    private void printRecord(JPanel panel) {
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
                graphics2D.translate(pageFormat.getImageableX() + 8, pageFormat.getImageableY() + 10);
                // This is a page scale. Default should be 0.3 I am using 0.5
                graphics2D.scale(0.7, 0.7);

                // Now paint panel as graphics2D
                panel.paint(graphics2D);

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


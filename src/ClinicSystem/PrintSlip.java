package ClinicSystem;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PrintSlip  extends javax.swing.JFrame {


    /**
     * Creates new form Home
     */


    //  String billNo="";
    Double totalAmount = 0.0;
    Double cash = 0.0;
    Double balance = 0.0;
    Double bHeight = 0.0;
   private String fee, pftFee, paid, Balance, Mr;

    public PrintSlip(String fee, String pftFee, String paid, String balance, String Mr) {
        this.fee = fee;
        this.pftFee = pftFee;
        this.paid = paid;
        this.Balance = balance;
        this.Mr = Mr;

        bHeight = 2.0;
        //JOptionPane.showMessageDialog(rootPane, bHeight);

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_jButton2ActionPerformed


    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(21);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }


    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }



        public class BillPrintable implements Printable {

            public int print(Graphics graphics, PageFormat pageFormat,int pageIndex)
                    throws PrinterException
            {


                //ImageIcon icon=new ImageIcon("C:UsersccsDocumentsNetBeansProjectsvideo TestPOSInvoicesrcposinvoicemylogo.jpg");
                int result = NO_SUCH_PAGE;
                if (pageIndex == 0) {

                    Graphics2D g2d = (Graphics2D) graphics;
                    double width = pageFormat.getImageableWidth();
                    g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());



                    //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));

                    try{
                        int y=20;
                        int yShift = 10;
                        int headerRectHeight=15;
                        // int headerRectHeighta=40;

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String time = dtf.format(now);
                        g2d.setFont(new Font("Monospaced",Font.PLAIN,12));
                  //      g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
                        g2d.drawString("        -------------------------------------",12,y);y+=yShift;
                        g2d.drawString("                Heart&Chest Clinic        ",12,y);y+=yShift;
                        g2d.drawString("            Sector F 7/4 St. 54 House 52 ",12,y);y+=yShift;
                        g2d.drawString("                Blue Area, Islamabad      ",12,y);y+=yShift;
                        g2d.drawString("                    :phone number:         ",12,y);y+=yShift;
                        g2d.drawString("        -------------------------------------",12,y);y+=yShift;
                        g2d.drawString("                      03315378141      ",12,y);y+=yShift;
                        g2d.drawString("        -------------------------------------",12,y);y+=headerRectHeight;
                        g2d.drawString("    Date:" + time + "    MR#: "+Mr , 12,y ); y+=yShift;
                        g2d.drawString("        -------------------------------------",12,y);y+=yShift;
                        g2d.drawString("           Checkup                  Price   ",10,y);y+=yShift;
                        g2d.drawString("        -------------------------------------",10,y);y+=headerRectHeight;


                            g2d.drawString(" "+"       Consultation Fee: "+"       Rs."+ fee + "/-",15,y);y+=yShift;
                            g2d.drawString("       .......................................",15,y);y+=yShift;
                            g2d.drawString("      "   +  "  PFT Fee:                 Rs."+ pftFee + "/-",15,y);y+=yShift;

                        g2d.drawString("      -----------------------------------------",15,y);y+=yShift;
                        g2d.drawString("        Total amount:            Rs."+String.valueOf(Integer.parseInt(fee)+Integer.parseInt(pftFee)) +""+ "/-   " ,15,y);y+=yShift;
                        g2d.drawString("      -----------------------------------------",15,y);y+=yShift;
                        g2d.drawString("        Cash    :                Rs."+paid+"/-   ",15,y);y+=yShift;
                        g2d.drawString("      -----------------------------------------",15,y);y+=yShift;
                        g2d.drawString("        Balance :                Rs."+Balance+"/-   ",15,y);y+=yShift;
                        g2d.drawString("                                                   ",15,y);y+=yShift;
                        g2d.drawString("      ********************************************",10,y);y+=yShift;
                        g2d.drawString("                 THANK YOU COME AGAIN            ",10,y);y+=yShift;
                        g2d.drawString("      ********************************************",10,y);y+=yShift;
                        g2d.drawString("                                                    ",10,y);y+=yShift;
                        g2d.drawString("                DEVELOPER:Zain ul Abdin          ",10,y);y+=yShift;
                        g2d.drawString("                CONTACT: zulabdin21@gmail.com       ",10,y);y+=yShift;
                        g2d.drawString("      <------------------------------------------>",15,y);y+=yShift;

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    result = PAGE_EXISTS;
                }
                return result;
            }
        }


}

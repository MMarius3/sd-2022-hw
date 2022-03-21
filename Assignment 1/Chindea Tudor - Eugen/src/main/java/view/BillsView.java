package view;
import javax.swing.*;
import java.awt.event.ActionListener;
import static javax.swing.BoxLayout.Y_AXIS;

public class BillsView extends JFrame{
    private JButton btnTransfer;
    private JButton btnPayBill;
    private JTextField tfAmount;
    private JTextField tfAccountIdFrom;
    private JTextField tfAccountIdTo;
    private JTextField tfAmount2;
    private JTextField tfAccountIdFrom2;
    private JTextField tfAccountIdTo2;
    public BillsView(){
        setSize(1000,720);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfAmount2);add(tfAmount);
        add(tfAccountIdFrom2);add(tfAccountIdFrom);
        add(tfAccountIdTo2);add(tfAccountIdTo);
        add(btnPayBill);
        add(btnTransfer);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields(){
        btnPayBill = new JButton("Pay Bill");
        btnTransfer = new JButton("Transfer Money");
        tfAccountIdFrom = new JTextField();
        tfAccountIdTo = new JTextField();
        tfAmount = new JTextField();
        tfAmount2 = new JTextField("Amount");
        tfAccountIdFrom2 = new JTextField("Transfer from");
        tfAccountIdTo2 = new JTextField("Transfer to");
    }
    public String getAmount(){return tfAmount.getText();};
    public String getIdFrom(){return tfAccountIdFrom.getText();};
    public String getIdTo(){return tfAccountIdTo.getText();};
    public void setAmount(String amount){tfAmount.setText(amount);};

    public void addPayBillButtonListener(ActionListener payButtonListener){
        btnPayBill.addActionListener(payButtonListener);
    }
    public void addTransferButtonListener(ActionListener transferButtonListener){
        btnTransfer.addActionListener(transferButtonListener);
    }
}

package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class BillView extends JFrame {

    private JTextField accountId;
    private JTextField billPrice;
    private JButton payBill;

    public BillView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
    }

    public void initializeFields() {
        payBill = new JButton("Pay Bill");
        accountId = new JTextField();
        billPrice = new JTextField();
        add(new JLabel("Account id"));
        add(accountId);
        add(new JLabel("Bill total"));
        add(billPrice);
        add(payBill);
    }

    public String getAccountId(){
        return accountId.getText();
    }
    public String getBillPrice(){
        return billPrice.getText();
    }

    public void payBillBtnListener(ActionListener btnListener) {
        payBill.addActionListener(btnListener);
    }
}

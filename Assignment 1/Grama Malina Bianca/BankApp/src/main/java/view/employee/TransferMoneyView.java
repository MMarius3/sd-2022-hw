package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class TransferMoneyView extends JFrame{
    private JTextField account1Id;
    private JTextField account2Id;
    private JTextField amount;
    private JButton transfer;

    public TransferMoneyView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(account1Id);
        add(account2Id);
        add(amount);
        add(transfer);
    }

    private void initializeFields() {
        account1Id = new JTextField("ID of account FROM which to transfer money");
        account2Id = new JTextField("ID of account TO which to transfer money");
        amount = new JTextField("Amount of $ to be transferred");
        transfer = new JButton("Transfer");
    }

    public String getAccount1Id() {
        return account1Id.getText();
    }

    public String getAccount2Id() {
        return account2Id.getText();
    }

    public String getAmount() {
        return amount.getText();
    }

    public void setTransferListener (ActionListener transferListener) {
        transfer.addActionListener(transferListener);
    }
}

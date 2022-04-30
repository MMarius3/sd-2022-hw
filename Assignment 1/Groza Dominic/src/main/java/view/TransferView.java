package view;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class TransferView extends JFrame {
    private JTextField accountFromId;
    private JTextField accountToId;
    private JTextField transferedAmount;
    private JButton transfer;

    public TransferView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
    }

    public void initializeFields() {
        transfer = new JButton("Transfer money");
        accountFromId = new JTextField();
        accountToId = new JTextField();
        transferedAmount = new JTextField();
        add(new JLabel("Source account"));
        add(accountFromId);
        add(new JLabel("Destination account"));
        add(accountToId);
        add(new JLabel("Amount to be transfered"));
        add(transferedAmount);
        add(transfer);
    }

    public String getAccountFromId(){
        return accountFromId.getText();
    }
    public String getAccountToId(){
        return accountToId.getText();
    }
    public String getTransferedAmount(){
        return transferedAmount.getText();
    }

    public void transferAmountListener(ActionListener btnListener) {
        transfer.addActionListener(btnListener);
    }
}

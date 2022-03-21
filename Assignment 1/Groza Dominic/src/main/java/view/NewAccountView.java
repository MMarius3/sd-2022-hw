package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class NewAccountView extends JFrame {

    private JButton saveAccount;
    private JTextField clientId;
    private JTextField accountType;
    private JTextField accountBalance;

    public NewAccountView() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        addComponents();
    }

    private void initializeFields() {
        saveAccount = new JButton("Create account");

        clientId = new JTextField();
        accountType = new JTextField();
        accountBalance = new JTextField();

    }

    private void addComponents() {
        add(new JLabel("Client id"));
        add(clientId);
        add(new JLabel("Account type"));
        add(accountType);
        add(new JLabel("Sarting balance"));
        add(accountBalance);
        add(saveAccount);
    }

    public void saveAccountBtnListener(ActionListener btnListener) {
        saveAccount.addActionListener(btnListener);
    }

    public String getClientId() {
        return clientId.getText();
    }

    public String getAccountType() {
        return accountType.getText();
    }

    public String getAccountBalance() {
        return accountBalance.getText();
    }
}

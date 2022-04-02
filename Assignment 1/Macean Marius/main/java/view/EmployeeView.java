package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JLabel labelClientId;
    private JLabel labelClientName;
    private JLabel labelClientIdCardNumber;
    private JLabel labelClientIdCode;
    private JLabel labelAccountId;
    private JLabel labelAccountType;
    private JLabel labelAccountMoneyAmount;
    private JLabel labelAccountIdToOperateWith;
    private JLabel labelMoneyAmountToOperateWith;
    private JTextField clientId;
    private JTextField clientName;
    private JTextField clientIdCardNumber;
    private JTextField clientIdCode;
    private JTextField accountId;
    private JTextField accountType;
    private JTextField accountMoneyAmount;
    private JTextField accountIdToOperateWith;
    private JTextField moneyAmountToOperateWith;
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClient;
    private JButton btnAddAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccount;
    private JButton btnTransferMoney;
    private JButton btnExecuteBill;

    public EmployeeView() {
        setSize(700, 700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(labelClientId);
        add(clientId);
        add(labelClientName);
        add(clientName);
        add(labelClientIdCardNumber);
        add(clientIdCardNumber);
        add(labelClientIdCode);
        add(clientIdCode);
        add(labelAccountId);
        add(accountId);
        add(labelAccountType);
        add(accountType);
        add(labelAccountMoneyAmount);
        add(accountMoneyAmount);
        add(labelAccountIdToOperateWith);
        add(accountIdToOperateWith);
        add(labelMoneyAmountToOperateWith);
        add(moneyAmountToOperateWith);
        add(btnAddClient);
        add(btnUpdateClient);
        add(btnViewClient);
        add(btnAddAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
        add(btnViewAccount);
        add(btnTransferMoney);
        add(btnExecuteBill);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        labelClientId = new JLabel("Client ID:");
        labelClientName = new JLabel("Client Name:");
        labelClientIdCardNumber = new JLabel("Client ID Card Number:");
        labelClientIdCode = new JLabel("Client ID Code:");
        labelAccountId = new JLabel("Account ID:");
        labelAccountType = new JLabel("Account type:");
        labelAccountMoneyAmount = new JLabel("Account Money Amount:");
        labelAccountIdToOperateWith = new JLabel("Account to Operate with:");
        labelMoneyAmountToOperateWith = new JLabel("Money Amount to Operate with:");
        clientId = new JTextField();
        clientName = new JTextField();
        clientIdCardNumber = new JTextField();
        clientIdCode = new JTextField();
        accountId = new JTextField();
        accountType = new JTextField();
        accountMoneyAmount = new JTextField();
        accountIdToOperateWith = new JTextField();
        moneyAmountToOperateWith = new JTextField();
        btnAddClient = new JButton("addClient");
        btnUpdateClient = new JButton("updateClient");
        btnViewClient = new JButton("viewClient");
        btnAddAccount = new JButton("addAccount");
        btnUpdateAccount = new JButton("updateAccount");
        btnDeleteAccount = new JButton("deleteAccount");
        btnViewAccount = new JButton("viewAccount");
        btnTransferMoney = new JButton("transferMoney");
        btnExecuteBill = new JButton("executeBill");
    }

    public String getClientId() {
        return clientId.getText();
    }

    public String getClientName() {
        return clientName.getText();
    }

    public String getClientIdCardNumber() {
        return clientIdCardNumber.getText();
    }

    public String getClientIdCode() {
        return clientIdCode.getText();
    }

    public String getAccountId() {
        return accountId.getText();
    }

    public String getAccountType() { return accountType.getText(); }

    public String getAccountMoneyAmount() {
        return accountMoneyAmount.getText();
    }

    public String getAccountIdToOperateWith() {
        return accountIdToOperateWith.getText();
    }

    public String getMoneyAmountToOperateWith() {
        return moneyAmountToOperateWith.getText();
    }

    public void addAddClientButtonListener(ActionListener addClientButtonListener) {
        btnAddClient.addActionListener(addClientButtonListener);
    }

    public void addUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void addViewClientButtonListener(ActionListener viewClientButtonListener) {
        btnViewClient.addActionListener(viewClientButtonListener);
    }

    public void addAddAccountButtonListener(ActionListener addAccountButtonListener) {
        btnAddAccount.addActionListener(addAccountButtonListener);
    }

    public void addUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void addDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void addViewAccountButtonListener(ActionListener viewAccountButtonListener) {
        btnViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void addTransferMoneyButtonListener(ActionListener transferMoneyButtonListener) {
        btnTransferMoney.addActionListener(transferMoneyButtonListener);
    }

    public void addExecuteBillButtonListener(ActionListener executeBillButtonListener) {
        btnExecuteBill.addActionListener(executeBillButtonListener);
    }
}

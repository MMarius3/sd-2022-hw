package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {
    private JButton btnBack;

    // Client Info
    private JTextField tfFullName;
    private JTextField tfIdNumber;
    private JTextField tfCNP;
    private JTextField tfAddress;

    private JButton btnShowClients;

    private JButton btnAddClient;
    private JButton btnUpdateClient;

    private JTable clientInfoTable;
//    private JScrollPane scrollPane;
    private JPanel panel;

    // Client Account
    private JTextField tfClientId;
    private JTextField tfAccountType;
    private JTextField tfMoneyAmount;

    private JTextField tfAccountId1;
    private JTextField tfAccountId2;
    private JTextField tfTransferAmount;
    private JTextField tfBill;

    private JButton btnShowAccounts;

    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;

    private JButton btnTransferMoney;
    private JButton btnProcessBills;

    private JTable clientAccountsTable;
    private JPanel panel2;

    public EmployeeView(){
        setSize(1000, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

//        add(scrollPane);
        add(panel);

//        add(clientInfoTable);
        add(btnShowClients);
        add(btnAddClient);
        add(btnUpdateClient);

        add(new JLabel("Full Name"));
        add(tfFullName);
        add(new JLabel("ID Number"));
        add(tfIdNumber);
        add(new JLabel("CNP"));
        add(tfCNP);
        add(new JLabel("Address"));
        add(tfAddress);

        add(panel2);

        add(btnShowAccounts);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);

        add(new JLabel("Client"));
        add(tfClientId);
        add(new JLabel("Account Type"));
        add(tfAccountType);
        add(new JLabel("Money Amount"));
        add(tfMoneyAmount);

        add(new JLabel("Account 1"));
        add(tfAccountId1);
        add(new JLabel("Account 2"));
        add(tfAccountId2);
        add(new JLabel("Transfer Amount"));
        add(tfTransferAmount);

        add(btnTransferMoney);

        add(new JLabel("Bill value"));
        add(tfBill);

        add(btnProcessBills);

        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        String[] columns = {"ID", "Full Name", "ID Number", "CNP", "Address"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
//        model.addColumn("ID");
//        model.addColumn("Full Name");
//        model.addColumn("ID Number");
//        model.addColumn("CNP");
//        model.addColumn("Address");
        clientInfoTable = new JTable(model);
//        clientInfoTable.setBounds(30,40,200,300);
//        scrollPane = new JScrollPane(clientInfoTable);
//        scrollPane.setPreferredSize(new Dimension(40, 30));
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(clientInfoTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(clientInfoTable);

        tfFullName = new JTextField();
        tfIdNumber = new JTextField();
        tfCNP = new JTextField();
        tfAddress = new JTextField();

        btnShowClients = new JButton("Show Clients' Information");
        btnAddClient = new JButton("Add Client");
        btnUpdateClient = new JButton("Update Client");

        String[] columns2 = {"ID", "Client ID", "Account Type", "Money Amount", "Creation Date"};
        DefaultTableModel model2 = new DefaultTableModel(columns2, 0);
        clientAccountsTable = new JTable(model2);
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.add(clientAccountsTable.getTableHeader(), BorderLayout.NORTH);
        panel2.add(clientAccountsTable);

        tfClientId = new JTextField();
        tfAccountType = new JTextField();
        tfMoneyAmount = new JTextField();
        tfAccountId1 = new JTextField();
        tfAccountId2 = new JTextField();
        tfTransferAmount = new JTextField();
        tfBill = new JTextField();

        btnShowAccounts = new JButton("Show Clients' Accounts");
        btnCreateAccount = new JButton("Add Account");
        btnUpdateAccount = new JButton("Update Account");
        btnDeleteAccount = new JButton("Delete Account");

        btnTransferMoney = new JButton("Transfer Money");
        btnProcessBills = new JButton("Process Bills");

        btnBack = new JButton("Back button");
    }

    public void addViewClientInfoButtonListener(ActionListener actionListener) {
        btnShowClients.addActionListener(actionListener);
    }

    public void addClientCreatorButtonListener(ActionListener actionListener) {
        btnAddClient.addActionListener(actionListener);
    }

    public void addClientUpdaterButtonListener(ActionListener actionListener) {
        btnUpdateClient.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

    public void addViewClientAccountsButtonListener(ActionListener actionListener){
        btnShowAccounts.addActionListener(actionListener);
    }

    public void addCreateClientAccountButtonListener(ActionListener actionListener){
        btnCreateAccount.addActionListener(actionListener);
    }

    public void addUpdateClientAccountsButtonListener(ActionListener actionListener){
        btnUpdateAccount.addActionListener(actionListener);
    }

    public void addDeleteClientAccountButtonListener(ActionListener actionListener){
        btnDeleteAccount.addActionListener(actionListener);
    }

    public void addTransferMoneyButtonListener(ActionListener actionListener){
        btnTransferMoney.addActionListener(actionListener);
    }

    public void addBillAccountButtonListener(ActionListener actionListener){
        btnProcessBills.addActionListener(actionListener);
    }

    public JTextField getTfFullName() {
        return tfFullName;
    }

    public JTextField getTfIdNumber() {
        return tfIdNumber;
    }

    public JTextField getTfCNP() {
        return tfCNP;
    }

    public JTextField getTfAddress() {
        return tfAddress;
    }

    public JTable getClientInfoTable() {
        return clientInfoTable;
    }

    public JTextField getTfClientId() {
        return tfClientId;
    }

    public JTextField getTfAccountType() {
        return tfAccountType;
    }

    public JTextField getTfMoneyAmount() {
        return tfMoneyAmount;
    }

    public JTextField getTfAccountId1() {
        return tfAccountId1;
    }

    public JTextField getTfAccountId2() {
        return tfAccountId2;
    }

    public JTextField getTfBill() {
        return tfBill;
    }

    public JTable getClientAccountsTable() {
        return clientAccountsTable;
    }

    public JTextField getTfTransferAmount() {
        return tfTransferAmount;
    }
}

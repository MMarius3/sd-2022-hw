package presentation_layer.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JButton btnViewClients;
    private JButton btnUpdateClients;
    private JButton btnAddClients;
    private JButton btnViewAccount;
    private JButton btnUpdateAccount;
    private JButton btnAddAccount;
    private JButton btnDeleteAccount;
    private JButton btnTransferMoney;
    private JButton processBills;

    private JLabel lUsername1Client;
    private JLabel lPassword1Client;
    private JLabel lId_Series1Client;
    private JLabel lId_Number1Client;
    private JLabel lPnc1Client;
    private JLabel lAddress1Client;

    private JTextField tfUsername1Client;
    private JTextField tfPassword1Client;
    private JTextField tfId_Series1Client;
    private JTextField tfId_Number1Client;
    private JTextField tfPnc1Client;
    private JTextField tfAddress1Client;

    private JLabel lUsername2Client;
    private JLabel lPassword2Client;
    private JLabel lId_Series2Client;
    private JLabel lId_Number2Client;
    private JLabel lPnc2Client;
    private JLabel lAddress2Client;

    private JTextField tfUsername2Client;
    private JTextField tfPassword2Client;
    private JTextField tfId_Series2Client;
    private JTextField tfId_Number2Client;
    private JTextField tfPnc2Client;
    private JTextField tfAddress2Client;

    private JLabel lId1Account;
    private JLabel lType1Account;
    private JLabel lMoney1Account;
    private JLabel lDate1Account;

    private JTextField tfId1Account;
    private JTextField tfType1Account;
    private JTextField tfMoney1Account;
    private JTextField tfDate1Account;

    private JLabel lId2Account;
    private JLabel lType2Account;
    private JLabel lMoney2Account;
    private JLabel lDate2Account;

    private JTextField tfId2Account;
    private JTextField tfType2Account;
    private JTextField tfMoney2Account;
    private JTextField tfDate2Account;


    private JTable allAccountsTable;
    private JTable allClientsTable;

    public EmployeeView() {
        setSize(1400, 900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(btnViewClients);
        add(btnUpdateClients);
        add(btnAddClients);
        add(btnViewAccount);
        add(btnUpdateAccount);
        add(btnAddAccount);
        add(btnDeleteAccount);
        add(btnTransferMoney);
        add(processBills);

        add(lUsername1Client);
        add(tfUsername1Client);
        add(lPassword1Client);
        add(tfPassword1Client);
        add(lId_Series1Client);
        add(tfId_Series1Client);
        add(lId_Number1Client);
        add(tfId_Number1Client);
        add(lPnc1Client);
        add(tfPnc1Client);
        add(lAddress1Client);
        add(tfAddress1Client);

        add(lUsername2Client);
        add(tfUsername2Client);
        add(lPassword2Client);
        add(tfPassword2Client);
        add(lId_Series2Client);
        add(tfId_Series2Client);
        add(lId_Number2Client);
        add(tfId_Number2Client);
        add(lPnc2Client);
        add(tfPnc2Client);
        add(lAddress2Client);
        add(tfAddress2Client);

        add(lId1Account);
        add(tfId1Account);
        add(lType1Account);
        add(tfType1Account);
        add(lMoney1Account);
        add(tfMoney1Account);
        add(lDate1Account);
        add(tfDate1Account);

        add(lId2Account);
        add(tfId2Account);
        add(lType2Account);
        add(tfType2Account);
        add(lMoney2Account);
        add(tfMoney2Account);
        add(lDate2Account);
        add(tfDate2Account);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        setLayout(null);
        btnViewClients = new JButton("View Clients");
        btnUpdateClients= new JButton("Update Clients");
        btnAddClients= new JButton("Add Clients");
        btnViewAccount= new JButton("View Account");
        btnAddAccount= new JButton("Add Account");
        btnDeleteAccount= new JButton("Delete Account");
        btnTransferMoney= new JButton("Transfer Money");
        processBills= new JButton("Process Bills");
        btnUpdateAccount = new JButton("Update Account");

        btnViewClients.setBounds( 10, 10, 150, 20);
        btnUpdateClients.setBounds( 160, 10,150, 20);
        btnAddClients.setBounds( 310, 10,150, 20);
        btnViewAccount.setBounds( 460, 10,150, 20);
        btnAddAccount.setBounds( 610, 10,150, 20);
        btnDeleteAccount.setBounds( 760, 10,150, 20);
        btnTransferMoney.setBounds( 910, 10,150, 20);
        processBills.setBounds( 1060, 10,150, 20);
        btnUpdateAccount.setBounds( 1210, 10,150, 20);

          lUsername1Client = new JLabel("Username client 1");
          lPassword1Client= new JLabel("Password client 1");
          lId_Series1Client= new JLabel("Series client 1");
          lId_Number1Client= new JLabel("Number client 1");
          lPnc1Client= new JLabel("Pnc client 1");
          lAddress1Client= new JLabel("Address client 1");
          tfUsername1Client = new JTextField();
          tfPassword1Client = new JTextField();
          tfId_Series1Client = new JTextField();
          tfId_Number1Client = new JTextField();
          tfPnc1Client = new JTextField();
          tfAddress1Client = new JTextField();

        lUsername1Client.setBounds( 10, 30, 200, 10);
        lPassword1Client.setBounds( 10, 70, 200, 10);
        lId_Series1Client.setBounds( 10, 110, 200, 10);
        lId_Number1Client.setBounds( 10, 150, 200, 10);
        lPnc1Client.setBounds( 10, 190, 200, 10);
        lAddress1Client.setBounds( 10, 230, 200, 10);
        tfUsername1Client.setBounds( 10, 50, 150, 20);
        tfPassword1Client.setBounds( 10, 90, 150, 20);
        tfId_Series1Client.setBounds( 10, 130, 150, 20);
        tfId_Number1Client.setBounds( 10, 170, 150, 20);
        tfPnc1Client.setBounds( 10, 210, 150, 20);
        tfAddress1Client.setBounds( 10, 250, 150, 20);

        lUsername2Client = new JLabel("Username client 2");
        lPassword2Client= new JLabel("Password client 2");
        lId_Series2Client= new JLabel("Series client 2");
        lId_Number2Client= new JLabel("Number client 2");
        lPnc2Client= new JLabel("Pnc client 2");
        lAddress2Client= new JLabel("Address client 2");
        tfUsername2Client = new JTextField();
        tfPassword2Client = new JTextField();
        tfId_Series2Client = new JTextField();
        tfId_Number2Client = new JTextField();
        tfPnc2Client = new JTextField();
        tfAddress2Client = new JTextField();

        lUsername2Client.setBounds( 250, 30, 200, 10);
        lPassword2Client.setBounds( 250, 70, 200, 10);
        lId_Series2Client.setBounds( 250, 110, 200, 10);
        lId_Number2Client.setBounds( 250, 150, 200, 10);
        lPnc2Client.setBounds( 250, 190, 200, 10);
        lAddress2Client.setBounds( 250, 230, 200, 10);
        tfUsername2Client.setBounds( 250, 50, 150, 20);
        tfPassword2Client.setBounds( 250, 90, 150, 20);
        tfId_Series2Client.setBounds( 250, 130, 150, 20);
        tfId_Number2Client.setBounds( 250, 170, 150, 20);
        tfPnc2Client.setBounds( 250, 210, 150, 20);
        tfAddress2Client.setBounds( 250, 250, 150, 20);

          lId1Account = new JLabel("Id account 1");
          lType1Account = new JLabel("Type account 1");
          lMoney1Account = new JLabel("Amount of money account 1");
          lDate1Account = new JLabel("Date of creation account 1");
          tfId1Account = new JTextField();
          tfType1Account = new JTextField();
          tfMoney1Account = new JTextField();
          tfDate1Account = new JTextField();

        lId1Account.setBounds( 500, 30, 200, 10);
        lType1Account.setBounds( 500, 70, 200, 10);
        lMoney1Account.setBounds( 500, 110, 200, 10);
        lDate1Account.setBounds( 500, 150, 200, 10);
        tfId1Account.setBounds( 500, 50, 150, 20);
        tfType1Account.setBounds( 500, 90, 150, 20);
        tfMoney1Account.setBounds( 500, 130, 150, 20);
        tfDate1Account.setBounds( 500, 170, 150, 20);


        lId2Account = new JLabel("Id account 2");
        lType2Account = new JLabel("Type account 2");
        lMoney2Account = new JLabel("Amount of money account 2");
        lDate2Account = new JLabel("Date of creation account 2");
        tfId2Account = new JTextField();
        tfType2Account = new JTextField();
        tfMoney2Account = new JTextField();
        tfDate2Account = new JTextField();

        lId2Account.setBounds( 750, 30, 200, 10);
        lType2Account.setBounds( 750, 70, 200, 10);
        lMoney2Account.setBounds( 750, 110, 200, 10);
        lDate2Account.setBounds( 750, 150, 200, 10);
        tfId2Account.setBounds( 750, 50, 150, 20);
        tfType2Account.setBounds( 750, 90, 150, 20);
        tfMoney2Account.setBounds( 750, 130, 150, 20);
        tfDate2Account.setBounds( 750, 170, 150, 20);
    }

    public void addViewClientButtonListener(ActionListener viewClientButtonListener) {
        btnViewClients.addActionListener(viewClientButtonListener);
    }

    public void addViewClientsTable (String[][] data, String[] columnNames)
    {
        DefaultTableModel tableClient = new DefaultTableModel(data,columnNames);
        allClientsTable = new JTable(data,columnNames);
        allClientsTable.setBounds( 10, 400, 1000, 100);
        add(allClientsTable);
    }

    public void addViewAccountsButtonListener(ActionListener viewAccountsButtonListener) {
        btnViewAccount.addActionListener(viewAccountsButtonListener);
    }

    public void addNewClientButtonListener(ActionListener addNewClientButtonListener) {
        btnAddClients.addActionListener(addNewClientButtonListener);
    }

    public void addUpdateClientButtonListener(ActionListener addUpdateClientButtonListener) {
        btnUpdateClients.addActionListener(addUpdateClientButtonListener);
    }

    public void addViewAccountsTable (String[][] data, String[] columnNames)
    {
        allAccountsTable = new JTable(data,columnNames);
        allAccountsTable.setBounds( 10, 600, 1000, 100);
        add(allAccountsTable);
    }

    public void addUpdateAccountButtonListener(ActionListener addUpdateAccountButtonListener) {
        btnUpdateAccount.addActionListener(addUpdateAccountButtonListener);
    }

    public void addDeleteAccountButtonListener(ActionListener addDeleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(addDeleteAccountButtonListener);
    }

    public void addInsertAccountButtonListener(ActionListener addInsertAccountButtonListener) {
        btnAddAccount.addActionListener(addInsertAccountButtonListener);
    }

    public void addProcessBillsButtonListener(ActionListener addProcessBillsButtonListener) {
        processBills.addActionListener(addProcessBillsButtonListener);
    }

    public void addTransferMoneyButtonListener(ActionListener addTransferMoneyButtonListener) {
        btnTransferMoney.addActionListener(addTransferMoneyButtonListener);
    }

    public String getTfUsername1Client() {
        return tfUsername1Client.getText();
    }

    public String getTfPassword1Client() {
        return tfPassword1Client.getText();
    }

    public String getTfId_Series1Client() {
        return tfId_Series1Client.getText();
    }

    public String getTfId_Number1Client() {
        return tfId_Number1Client.getText();
    }

    public String getTfPnc1Client() {
        return tfPnc1Client.getText();
    }

    public String getTfAddress1Client() {
        return tfAddress1Client.getText();
    }

    public String getTfUsername2Client() {
        return tfUsername2Client.getText();
    }

    public String getTfPassword2Client() {
        return tfPassword2Client.getText();
    }

    public String getTfId_Series2Client() {
        return tfId_Series2Client.getText();
    }

    public String getTfId_Number2Client() {
        return tfId_Number2Client.getText();
    }

    public String getTfPnc2Client() {
        return tfPnc2Client.getText();
    }

    public String getTfAddress2Client() {
        return tfAddress2Client.getText();
    }

    public String getTfId1Account() {
        return tfId1Account.getText();
    }

    public String getTfType1Account() {
        return tfType1Account.getText();
    }

    public String getTfMoney1Account() {
        return tfMoney1Account.getText();
    }

    public String getTfDate1Account() {
        return tfDate1Account.getText();
    }

    public String getTfId2Account() {
        return tfId2Account.getText();
    }

    public String getTfType2Account() {
        return tfType2Account.getText();
    }

    public String getTfMoney2Account() {
        return tfMoney2Account.getText();
    }

    public String getTfDate2Account() {
        return tfDate2Account.getText();
    }
}

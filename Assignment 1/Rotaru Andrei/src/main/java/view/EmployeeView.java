package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JTextField tfName;
    private JTextField tfIdentityCardNumber;
    private JTextField tfPersonalNumericalCode;
    private JTextField tfAddress;
    private JLabel lbName;
    private JLabel lbIdentityCardNumber;
    private JLabel lbPersonalNumericalCode;
    private JLabel lbAddress;
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClient;

    private JTable tblClients;
    private JScrollPane scrollPaneClients;
    private JTable tblAccounts;
    private JScrollPane scrollPaneAccounts;

    private JLabel separator;

    private JTextField tfType;
    private JTextField tfBalance;
    private JLabel lbType;
    private JLabel lbBalance;
    private JButton btnAddAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccount;

    private JButton btnTransfer;
    private JButton btnBill;

    private JButton btnLogout;

    public EmployeeView() throws HeadlessException{
        this.setTitle("Employee Page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 50, 1000, 700);
        this.getContentPane().setLayout(null);

        initializeFields();

        this.setVisible(false);
    }

    private void initializeFields(){
        scrollPaneClients = new JScrollPane(tblClients);
        scrollPaneClients.setBounds(440,60,460,150);
        getContentPane().add(scrollPaneClients);

        scrollPaneAccounts = new JScrollPane(tblAccounts);
        scrollPaneAccounts.setBounds(440, 220,460,150);
        getContentPane().add(scrollPaneAccounts);

        lbName = new JLabel("Name: ");
        lbName.setBounds(50,60,150,30);
        getContentPane().add(lbName);
        tfName = new JTextField();
        tfName.setBounds(200,60,150,30);
        getContentPane().add(tfName);

        lbIdentityCardNumber = new JLabel("Identity Card Number: ");
        lbIdentityCardNumber.setBounds(50,100,150,30);
        getContentPane().add(lbIdentityCardNumber);
        tfIdentityCardNumber = new JTextField();
        tfIdentityCardNumber.setBounds(200,100,150,30);
        getContentPane().add(tfIdentityCardNumber);

        lbPersonalNumericalCode = new JLabel("Personal Numerical Code: ");
        lbPersonalNumericalCode.setBounds(50,140,150,30);
        getContentPane().add(lbPersonalNumericalCode);
        tfPersonalNumericalCode = new JTextField();
        tfPersonalNumericalCode.setBounds(200,140,150,30);
        getContentPane().add(tfPersonalNumericalCode);

        lbAddress = new JLabel("Address: ");
        lbAddress.setBounds(50,180,150,30);
        getContentPane().add(lbAddress);
        tfAddress = new JTextField();
        tfAddress.setBounds(200,180,150,30);
        getContentPane().add(tfAddress);

        btnAddClient = new JButton("Add client");
        btnAddClient.setBounds(50,220,100,30);
        getContentPane().add(btnAddClient);

        btnUpdateClient = new JButton("Edit client");
        btnUpdateClient.setBounds(160,220,100,30);
        getContentPane().add(btnUpdateClient);

        btnViewClient = new JButton("View clients");
        btnViewClient.setBounds(270,220,150,30);
        getContentPane().add(btnViewClient);

        separator = new JLabel("----------------------------------------------------------------------------------------------------");
        separator.setBounds(20,260,800,20);
        getContentPane().add(separator);

        lbType = new JLabel("Type: ");
        lbType.setBounds(50,280,150,30);
        getContentPane().add(lbType);
        tfType = new JTextField();
        tfType.setBounds(200,280,150,30);
        getContentPane().add(tfType);

        lbBalance = new JLabel("Balance: ");
        lbBalance.setBounds(50, 320,150,30);
        getContentPane().add(lbBalance);
        tfBalance = new JTextField();
        tfBalance.setBounds(200,320,150,30);
        getContentPane().add(tfBalance);

        btnAddAccount = new JButton("Add account");
        btnAddAccount.setBounds(50,400,150,30);
        getContentPane().add(btnAddAccount);

        btnUpdateAccount = new JButton("Edit account");
        btnUpdateAccount.setBounds(210,400,150,30);
        getContentPane().add(btnUpdateAccount);

        btnDeleteAccount = new JButton("Delete account");
        btnDeleteAccount.setBounds(370,400,150,30);
        getContentPane().add(btnDeleteAccount);

        btnViewAccount = new JButton("View accounts");
        btnViewAccount.setBounds(530,400,150,30);
        getContentPane().add(btnViewAccount);

        btnTransfer = new JButton("Transfer money");
        btnTransfer.setBounds(50,500,150,30);
        getContentPane().add(btnTransfer);

        btnBill = new JButton("Pay bill");
        btnBill.setBounds(210,500,150,30);
        getContentPane().add(btnBill);

        btnLogout = new JButton("Log Out");
        btnLogout.setBounds(20,20,100,30);
        getContentPane().add(btnLogout);
    }

    public String getName() {
        return tfName.getText();
    }

    public String getIdentityCardNumber() {
        return tfIdentityCardNumber.getText();
    }

    public String getPersonalNumericalCode() {
        return tfPersonalNumericalCode.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getTfType(){
        return tfType.getText();
    }

    public String getBalance(){
        return tfBalance.getText();
    }

    public void setAddClientButtonListener (ActionListener addClientButtonListener){
        btnAddClient.addActionListener(addClientButtonListener);
    }

    public void setUpdateClientButtonListener (ActionListener updateClientButtonListener){
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setViewClientButtonListener (ActionListener viewClientButtonListener){
        btnViewClient.addActionListener(viewClientButtonListener);
    }

    public void setAddAccountButtonListener (ActionListener addAccountButtonListener){
        btnAddAccount.addActionListener(addAccountButtonListener);
    }

    public void setUpdateAccountButtonListener (ActionListener updateAccountButtonListener){
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener (ActionListener deleteAccountButtonListener){
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setViewAccountButtonListener (ActionListener viewAccountButtonListener){
        btnViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void setTransferButtonListener (ActionListener transferButtonListener){
        btnTransfer.addActionListener(transferButtonListener);
    }

    public void setBillButtonListener (ActionListener billButtonListener){
        btnBill.addActionListener(billButtonListener);
    }

    public void setLogoutButtonListener (ActionListener logoutButtonListener){
        btnLogout.addActionListener(logoutButtonListener);
    }

    public Long getSelectedRowFromClients() {
        return Long.parseLong(tblClients.getValueAt(tblClients.getSelectedRow(), 0).toString());
    }

    public Long getSelectedRowFromAccounts() {
        return Long.parseLong(tblAccounts.getValueAt(tblAccounts.getSelectedRow(),0).toString());
    }

    public void loadClientsTable(JTable tbl) {
        this.tblClients = tbl;
        scrollPaneClients.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void loadAccountsTable(JTable tbl){
        this.tblAccounts = tbl;
        scrollPaneAccounts.setViewportView(tbl);
        revalidate();
        repaint();
    }
}

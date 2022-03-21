package view;

import controller.EmployeeController;
import model.Account;
import model.Client;
import repository.Client.ClientRepository;
import repository.Client.ClientRepositoryMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {
    List<Client> clients = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    String[] colNames = {"Name", "PNC", "Card Number", "Address"};
    String[] colNames2 = {"Account number", "Type", "Balance", "Date of creation"};
    JTable table;
    JTable table2;
    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultTableModel tableModel2 = new DefaultTableModel();
    JScrollPane pane;
    JScrollPane pane2;
    Object[] row = new Object[4];

    JTextField searchField;
    JButton searchButton;
    JButton resetButton;
    JLabel searchLabel;
    JTextField nameField;
    JTextField cardNrField;
    JTextField pncField;
    JTextField addressField;
    JLabel nameLabel;
    JLabel cardNrLabel;
    JLabel pncLabel;
    JLabel addressLabel;
    JButton addButton;
    JButton deleteButton;
    JButton updateButton;
    JTextField transferFromField;
    JTextField transferToField;
    JTextField transferAmountField;
    JButton transferButton;
    JLabel transferLabel;
    JTextField billAccount;
    JTextField billAmount;
    JButton payBillButton;
    JLabel billLabel;

    private void initializeFields() {
        this.table = new JTable();
        this.table2 = new JTable();
        this.pane = new JScrollPane(table);
        this.pane2 = new JScrollPane(table2);
        this.searchField = new JTextField();
        this.searchButton = new JButton("Search");
        this.resetButton = new JButton("Reset");
        this.searchLabel = new JLabel("PNC: ");
        this.nameField = new JTextField();
        this.cardNrField = new JTextField();
        this.pncField = new JTextField();
        this.addressField = new JTextField();
        this.nameLabel = new JLabel("Name:");
        this.cardNrLabel = new JLabel("ID card nr:");
        this.pncLabel = new JLabel("PNC:");
        this.addressLabel = new JLabel("Address:");
        this.addButton = new JButton("Add Client");
        this.deleteButton = new JButton("Delete Client");
        this.updateButton = new JButton("Update Client");
        this.transferFromField = new JTextField();
        this.transferToField = new JTextField();
        this.transferAmountField = new JTextField();
        this.transferButton = new JButton("Transfer");
        this.transferLabel = new JLabel("Transfer");
        this.billAccount = new JTextField();
        this.billAmount = new JTextField();
        this.payBillButton = new JButton("Authorize");
        this.billLabel = new JLabel("Process Bills");
    }

    public EmployeeView() throws SQLException {

        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initializeFields();

        searchLabel.setBounds(0, 5, 50, 20);
        add(searchLabel);
        searchField.setBounds(50, 5, 100, 20);
        add(searchField);
        searchButton.setBounds(160, 5, 80, 20);
        add(searchButton);
        resetButton.setBounds(250, 5, 80, 20);
        add(resetButton);

        tableModel.setColumnIdentifiers(colNames);
        table.setModel(tableModel);
        pane.setBounds(0, 30, 800, 150);
        add(pane);

        tableModel2.setColumnIdentifiers(colNames2);
        table2.setModel(tableModel2);
        pane2.setBounds(0, 200, 800, 150);
        add(pane2);

        nameLabel.setBounds(1, 395, 70, 20);
        add(nameLabel);
        nameField.setBounds(70, 395, 150, 20);
        add(nameField);
        cardNrLabel.setBounds(1, 420, 70, 20);
        add(cardNrLabel);
        cardNrField.setBounds(70, 420, 150, 20);
        add(cardNrField);
        pncLabel.setBounds(1, 445, 70, 20);
        add(pncLabel);
        pncField.setBounds(70, 445, 150, 20);
        add(pncField);
        addressLabel.setBounds(1, 470, 70, 20);
        add(addressLabel);
        addressField.setBounds(70, 470, 150, 20);
        add(addressField);

        addButton.setBounds(1, 495, 100, 30);
        add(addButton);
        updateButton.setBounds(1, 360, 120, 30);
        add(updateButton);
        deleteButton.setBounds(121, 360, 120, 30);
        add(deleteButton);

        transferLabel.setBounds(300, 395, 50, 20);
        add(transferLabel);
        transferFromField.setBounds(300, 420, 100, 20);
        add(transferFromField);
        transferToField.setBounds(300, 445, 100, 20);
        add(transferToField);
        transferAmountField.setBounds(300, 470, 100, 20);
        add(transferAmountField);
        transferButton.setBounds(300, 500, 100, 30);
        add(transferButton);

        billLabel.setBounds(450, 395, 100, 20);
        add(billLabel);
        billAccount.setBounds(450, 415, 100, 20);
        add(billAccount);
        billAmount.setBounds(450, 440, 100, 20);
        add(billAmount);
        payBillButton.setBounds(450, 465, 100, 30);
        add(payBillButton);

        setLayout(null);
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void populateClientTable() {
        tableModel.setRowCount(0);
        for (Client c : clients) {
            row[0] = c.getFullName();
            row[1] = c.getPnc();
            row[2] = c.getCardNumber();
            row[3] = c.getAddress();

            tableModel.addRow(row);
        }
    }

    public void populateAccountTable() {
        tableModel2.setRowCount(0);
        for (Account a : accounts) {
            row[0] = a.getId();
            row[1] = a.getType();
            row[2] = a.getBalance();
            row[3] = a.getDateOfCreation();

            tableModel2.addRow(row);
        }
    }

    public String getSearchPnc() {
        return this.searchField.getText();
    }

    public String[] getAddClientInfo() {
        String[] info = new String[4];
        info[0] = nameField.getText();
        info[1] = pncField.getText();
        info[2] = cardNrField.getText();
        info[3] = addressField.getText();
        return info;
    }

    public String[] update() {
        int row = table.getSelectedRow();
        String[] info = new String[4];
        info[0] = (String) table.getModel().getValueAt(row, 0);
        info[1] = (String) table.getModel().getValueAt(row, 1);
        info[2] = (String) table.getModel().getValueAt(row, 2);
        info[3] = (String) table.getModel().getValueAt(row, 3);
        return info;
    }

    public String delete() {
        int row = table.getSelectedRow();
        return (String) table.getModel().getValueAt(row, 1);
    }

    public String[] transfer() {
        if (this.transferFromField.getText().equals("") || transferToField.getText().equals("") || transferAmountField.equals("")) {
            return null;
        }

        String[] info = new String[3];
        info[0] = transferFromField.getText();
        info[1] = transferToField.getText();
        info[2] = transferAmountField.getText();
        return info;
    }

    public String[] payBill() {
        String[] info = new String[2];
        info[0] = billAccount.getText();
        info[1] = billAmount.getText();
        return info;
    }

    public void addSearchButtonActionListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    public void addResetButtonActionListener(ActionListener actionListener) {
        resetButton.addActionListener(actionListener);
    }

    public void addAddButtonActionListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void addUpdateButtonListener(ActionListener actionListener) {
        updateButton.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void addTransferButtonListener(ActionListener actionListener) {
        transferButton.addActionListener(actionListener);
    }

    public void addPayBillButtonListener(ActionListener actionListener) {
        payBillButton.addActionListener(actionListener);
    }
}

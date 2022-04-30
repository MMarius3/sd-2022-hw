package view.employee;

import model.Account;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeView extends JFrame {
  private JPanel mainPanel;
  private JTextField nameTxtFld;
  private JTextField addressTxtFld;
  private JButton saveAccountButton;
  private JButton deleteAccountButton;
  private JButton clearAccountButton;
  private JTable clientsTable;
  private JPanel infoPanel;
  private JButton updateAccountButton;
  private JComboBox accountTypeComboBox;
  private JFormattedTextField balanceFormattedTxtFld;
  private JFormattedTextField creationDateFormattedTxtFld;
  private JFormattedTextField CNPFormattedTxtFld;
  private JFormattedTextField cardNumberFormattedTxtFld;
  private JTextField clientIdTxtFld;
  private JTextField accountIdTxtFld;
  private JTable accountsTable;
  private JButton saveClientButton;
  private JButton updateClientButton;
  private JButton deleteClientButton;
  private JButton clearClientButton;

  public EmployeeView() {
    setContentPane(mainPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    pack();
    init();
  }

  private void init() {
    clientIdTxtFld.setEditable(false);
    accountIdTxtFld.setEditable(false);

    clientsTable.setModel(new DefaultTableModel(null,
            new String[]{"ID", "Name", "CNP", "Address", "Card Number"}));
    accountsTable.setModel(new DefaultTableModel(null,
            new String[]{"ID", "Account type", "Balance", "Creation date", "Client"}));

    MaskFormatter cnpFormatter = null;
    MaskFormatter cardNumberFormatter = null;
    try {
      cnpFormatter = new MaskFormatter("#############");
      cnpFormatter.setPlaceholder("_");

      cardNumberFormatter = new MaskFormatter("################");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    CNPFormattedTxtFld.setFormatterFactory(new DefaultFormatterFactory(cnpFormatter));
    cardNumberFormattedTxtFld.setFormatterFactory(new DefaultFormatterFactory(cardNumberFormatter));

    NumberFormat numberFormat = NumberFormat.getInstance();
    numberFormat.setMaximumFractionDigits(2);
    balanceFormattedTxtFld.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(numberFormat)));

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    DateFormatter df = new DateFormatter(format);
    creationDateFormattedTxtFld.setFormatterFactory(new DefaultFormatterFactory(df));
    creationDateFormattedTxtFld.setValue(new java.util.Date());
  }

  public void clearClientFields() {
    clientIdTxtFld.setText("");
    nameTxtFld.setText("");
    CNPFormattedTxtFld.setText("");
    addressTxtFld.setText("");
    cardNumberFormattedTxtFld.setText("");
  }

  public void clearAccountFields() {
    accountIdTxtFld.setText("");
    accountTypeComboBox.setSelectedIndex(0);
    balanceFormattedTxtFld.setText("");
    creationDateFormattedTxtFld.setText("");
  }

  public void showMessage(String message, int type) {
    JOptionPane.showMessageDialog(this, message, "Message", type);
  }

  public void setClientSaveButtonListener(ActionListener e) {
    saveClientButton.addActionListener(e);
  }

  public void setAccountSaveButtonListener(ActionListener e) {
    saveAccountButton.addActionListener(e);
  }

  public void setClientRowMouseListener(MouseListener e) {
    clientsTable.addMouseListener(e);
  }

  public void setAccountRowMouseListener(MouseListener e) {
    accountsTable.addMouseListener(e);
  }

  public void setClientInfoOfRow(int row) {
    String clientId = clientsTable.getValueAt(row, 0).toString();
    String clientName = clientsTable.getValueAt(row, 1).toString();
    String clientCNP = clientsTable.getValueAt(row, 2).toString();
    String clientAddress = clientsTable.getValueAt(row, 3).toString();
    String clientCardNumber = clientsTable.getValueAt(row, 4).toString();

    clientIdTxtFld.setText(clientId);
    nameTxtFld.setText(clientName);
    CNPFormattedTxtFld.setText(clientCNP);
    addressTxtFld.setText(clientAddress);
    cardNumberFormattedTxtFld.setText(clientCardNumber);
  }

  public void setAccountInfoOfRow(int row) {
    String accountId = accountsTable.getValueAt(row, 0).toString();
    String accountType = accountsTable.getValueAt(row, 1).toString();
    String accountBalance = accountsTable.getValueAt(row, 2).toString();
    String accountCreationDate = accountsTable.getValueAt(row, 3).toString();

    accountIdTxtFld.setText(accountId);
    accountTypeComboBox.setSelectedItem(accountType);
    balanceFormattedTxtFld.setValue(Double.valueOf(accountBalance));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      creationDateFormattedTxtFld.setValue(format.parse(accountCreationDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public String getName() {
    return nameTxtFld.getText();
  }

  public String getCNP() {
    return CNPFormattedTxtFld.getText();
  }

  public String getAddress() {
    return addressTxtFld.getText();
  }

  public String getCardNumber() {
    return cardNumberFormattedTxtFld.getText();
  }

  public String getAccType() {
    return (String) accountTypeComboBox.getSelectedItem();
  }

  public String getCreationDate() {
    return creationDateFormattedTxtFld.getText();
  }

  public String getBalance() {
    return balanceFormattedTxtFld.getText();
  }

  public void setName(String name) {
    nameTxtFld.setText(name);
  }

  public String getClientId() {
    return clientIdTxtFld.getText();
  }

  public String getAccountId() {
    return accountIdTxtFld.getText();
  }

  public JTable getClientsTable() {
    return clientsTable;
  }

  public JTable getAccountsTable() {
    return accountsTable;
  }

  public void fillClientTable(List<Client> clients) {
    DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
    model.setRowCount(0);
    for (Client client : clients) {
      model.addRow(new Object[]{client.getId(), client.getName(), client.getSSN(), client.getAddress(), client.getCardNumber()});
    }
  }

  public void fillAccountsTable(List<Account> accounts) {
    DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
    model.setRowCount(0);
    for (Account account : accounts) {
      model.addRow(new Object[]{account.getId(), account.getAccountType().getLabel(), account.getBalance(), account.getCreationDate(), account.getClient().getName()});
    }
  }

  public void setClearClientButtonListener(ActionListener e) {
    clearClientButton.addActionListener(e);
  }

  public void setClearAccountButtonListener(ActionListener e) {
    clearAccountButton.addActionListener(e);
  }

  public void setUpdateAccountButtonListener(ActionListener e) {
    updateAccountButton.addActionListener(e);
  }

  public void setUpdateClientButtonListener(ActionListener e) {
    updateClientButton.addActionListener(e);
  }

  public void setDeleteAccountButtonListener(ActionListener e) {
    deleteAccountButton.addActionListener(e);
  }

  public void setDeleteClientButtonListener(ActionListener e) {
    deleteClientButton.addActionListener(e);
  }
}

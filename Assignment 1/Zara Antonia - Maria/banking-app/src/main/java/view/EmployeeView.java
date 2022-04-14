package view;

import model.Client;
import model.ClientAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeView extends JFrame{

    private JButton addClientBtn;
    private JButton updateClientBtn;
    private JButton viewClientBtn;
    private JButton addClientAccountBtn;
    private JButton updateClientAccountBtn;
    private JButton viewClientAccountBtn;
    private JButton deleteClientAccountBtn;
    private JButton transferMoneyBtn;
    private JButton processUtilitiesBtn;
    
    private JLabel clientLabel;
    private JLabel clientNameLabel;
    private JLabel clientCnpLabel;
    private JLabel clientIdNumberLabel;
    private JLabel clientAddressLabel;
    private JTextField clientNameField;
    private JTextField clientCnpField;
    private JTextField clientAddressField;
    private JTextField clientIdNumberField;
    
    private JLabel clientAccountLabel;
    private JLabel clientAccountIdLabel;
    private JLabel clientAccountTypeLabel;
    private JLabel clientAccountAmountLabel;
    private JLabel clientAccountCreationDateLabel;
    private JLabel clientAccountOwnerLabel;
    private JTextField clientAccountIdField;
    private JTextField clientAccountTypeField;
    private JTextField clientAccountAmountField;
    private JTextField clientAccountCreationDateField;
    private JTextField clientAccountOwnerField;

    private JComboBox<Client> clientComboBox;
    private JComboBox<ClientAccount> clientAccountComboBox;

    private JLabel dummy1, dummy2, dummy3, dummy4, dummy5;
    

    public EmployeeView() {
        setSize(600, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(0,2));
        
        add(clientLabel);
        add(dummy1);
        add(clientComboBox);
        add(dummy4);
        add(clientNameLabel);
        add(clientNameField);
        add(clientCnpLabel);
        add(clientCnpField);
        add(clientIdNumberLabel);
        add(clientIdNumberField);
        add(clientAddressLabel);
        add(clientAddressField);
        add(addClientBtn);
        add(updateClientBtn);
        add(viewClientBtn);
        add(dummy2);
        
        add(clientAccountLabel);
        add(dummy3);
        add(clientAccountComboBox);
        add(dummy5);
        add(clientAccountOwnerLabel);
        add(clientAccountOwnerField);
        add(clientAccountIdLabel);
        add(clientAccountIdField);
        add(clientAccountTypeLabel);
        add(clientAccountTypeField);
        add(clientAccountAmountLabel);
        add(clientAccountAmountField);
        add(clientAccountCreationDateLabel);
        add(clientAccountCreationDateField);
        add(addClientAccountBtn);
        add(updateClientAccountBtn);
        add(viewClientAccountBtn);
        add(deleteClientAccountBtn);
        
        add(transferMoneyBtn);
        add(processUtilitiesBtn);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields(){
        
        clientLabel = new JLabel("Client Information");
        clientNameLabel = new JLabel("Name: ");
        clientCnpLabel = new JLabel("CNP: ");
        clientIdNumberLabel = new JLabel("Id Number: ");
        clientAddressLabel = new JLabel("Address");
        
        clientNameField = new JTextField();
        clientCnpField = new JTextField();
        clientIdNumberField = new JTextField();
        clientAddressField = new JTextField();

        clientComboBox = new JComboBox();
        
        addClientBtn = new JButton("Add");
        updateClientBtn = new JButton("Update");
        viewClientBtn = new JButton("View");
        
        clientAccountLabel = new JLabel("Client Account Information");
        clientAccountOwnerLabel = new JLabel("Owner: ");
        clientAccountIdLabel = new JLabel("ID: ");
        clientAccountTypeLabel = new JLabel("Type: ");
        clientAccountAmountLabel = new JLabel("Amount: ");
        clientAccountCreationDateLabel = new JLabel("Creation Date: ");

        clientAccountOwnerField = new JTextField();
        clientAccountOwnerField.setEditable(false);
        clientAccountIdField = new JTextField();
        clientAccountIdField.setEditable(false);
        clientAccountTypeField = new JTextField();
        clientAccountAmountField = new JTextField();
        clientAccountCreationDateField = new JTextField();
        clientAccountCreationDateField.setEditable(false);

        clientAccountComboBox = new JComboBox();

        addClientAccountBtn = new JButton("Add");
        updateClientAccountBtn = new JButton("Update");
        viewClientAccountBtn = new JButton("View");
        deleteClientAccountBtn = new JButton("Delete");

        dummy1 = new JLabel("");
        dummy2 = new JLabel("");
        dummy3 = new JLabel("");
        dummy4 = new JLabel("");
        dummy5 = new JLabel("");

        transferMoneyBtn = new JButton("Transfer Money");
        processUtilitiesBtn = new JButton("Process Utilities Bills");
    }

    public void addAddClientButtonListener(ActionListener addClientButtonListener) {
        addClientBtn.addActionListener(addClientButtonListener);
    }

    public void addViewClientButtonListener(ActionListener viewClientButtonListener) {
        viewClientBtn.addActionListener(viewClientButtonListener);
    }

    public void addUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        updateClientBtn.addActionListener(updateClientButtonListener);
    }

    public void addAddClientAccountButtonListener(ActionListener addClientAccountButtonListener) {
        addClientAccountBtn.addActionListener(addClientAccountButtonListener);
    }

    public void addViewClientAccountButtonListener(ActionListener viewClientAccountButtonListener) {
        viewClientAccountBtn.addActionListener(viewClientAccountButtonListener);
    }

    public void addUpdateClientAccountButtonListener(ActionListener updateClientAccountButtonListener) {
        updateClientAccountBtn.addActionListener(updateClientAccountButtonListener);
    }

    public void addDeleteClientAccountButtonListener(ActionListener deleteClientAccountButtonListener) {
        deleteClientAccountBtn.addActionListener(deleteClientAccountButtonListener);
    }

    public void addTransferMoneyButtonListener(ActionListener transferMoneyButtonListener) {
        transferMoneyBtn.addActionListener(transferMoneyButtonListener);
    }

    public void addProcessUtilitiesButtonListener(ActionListener processUtilitiesButtonListener) {
        processUtilitiesBtn.addActionListener(processUtilitiesButtonListener);
    }

    public String getClientName(){
        return clientNameField.getText();
    }
    
    public void setClientName(String name){ clientNameField.setText(name);} 

    public String getClientCNP() { return clientCnpField.getText(); }
    
    public void setClientCNP(String cnp) { clientCnpField.setText(cnp);}

    public String getClientAddress(){
        return clientAddressField.getText();
    }

    public void setClientAddress(String address) { clientAddressField.setText(address);}

    public String getClientIdNumber() {return clientIdNumberField.getText(); }

    public void setClientIdNumber(String idNumber) { clientIdNumberField.setText(idNumber);}

    public void setClientAccountOwner(Client client) {clientAccountOwnerField.setText(client.toString());}

    public String getClientAccountType(){
        return clientAccountTypeField.getText();
    }

    public Optional<Double> getClientAccountAmount(){
        if(clientAccountAmountField.getText().equals("") || clientAccountAmountField.getText() == null){
            return Optional.empty();
        }
        else{
            return Optional.of(Double.parseDouble(clientAccountAmountField.getText()));
        }
    }

    public void setClientAccountID(Integer id){
        clientAccountIdField.setText(String.valueOf(id));
    }

    public void setClientAccountType(String type){
        clientAccountTypeField.setText(type);
    }

    public void setClientAccountAmount(Double amount){
        clientAccountAmountField.setText(String.valueOf(amount));
    }

    public void setClientAccountCreationDate(Date creationDate){
        clientAccountCreationDateField.setText(String.valueOf(creationDate));
    }

    public Client getSelectedClient(){
        return (Client) clientComboBox.getSelectedItem();
    }

    public ClientAccount getSelectedClientAccount(){
        return (ClientAccount) clientAccountComboBox.getSelectedItem();
    }

   public void setClientAccountComboBox(ArrayList<ClientAccount> clientAccounts) {
        clientAccountComboBox.removeAllItems();
        for(ClientAccount c : clientAccounts) {
            clientAccountComboBox.addItem(c);
        }
       clientAccountComboBox.setVisible(true);
    }

    public void setClientComboBox(ArrayList<Client> clients) {
        clientComboBox.removeAllItems();
        for(Client c : clients) {
            clientComboBox.addItem(c);
        }
        clientComboBox.setVisible(true);
    }
}

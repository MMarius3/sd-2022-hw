package view;

import model.Account;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;

import java.util.List;



public class EmployeeView extends JFrame {

    private JButton addClient;
    private JButton viewClients;
    private JButton updateClients;
    private JButton createAccount;
    private JButton updateAccount;
    private JButton viewAccount;
    private JButton deleteAccount;

    private JLabel clientName;
    private JTextField tfClientName;
    private JLabel clientCnp;
    private JTextField tfClientCnp;
    private JLabel clientAddress;
    private JTextField tfClientAddress;
    private JLabel clientCardNumber;
    private JTextField tfClientCardNumber;

    private JLabel accountCardNumber;
    private JTextField tfAccountCardNumber;
    private JLabel accountType;
    private JTextField tfAccountType;
    private JLabel accountSumOfMoney;
    private JTextField tfAccountSumOfMoney;

    private JButton processBill;
    private JLabel sumOfTheBill;
    private JTextField tfSumOfTheBill;

    private JButton transferMoney;
    private JButton sender;
    private JButton receiver;
    private JLabel sumToBeTransferred;
    private JTextField tfSumToBeTransferred;

    private JTable allClients;
    private JTable allAccounts;


    public EmployeeView(){
        setSize(800,800);
        setLocationRelativeTo(null);
        initializeFields();

        add(addClient);
        add(viewClients);
        add(updateClients);

        add(createAccount);
        add(updateAccount);
        add(viewAccount);
        add(deleteAccount);

        add(clientName);
        add(tfClientName);
        add(clientCnp);
        add(tfClientCnp);
        add(clientCardNumber);
        add(tfClientCardNumber);
        add(clientAddress);
        add(tfClientAddress);

        add(accountCardNumber);
        add(tfAccountCardNumber);
        add(accountType);
        add(tfAccountType);
        add(accountSumOfMoney);
        add(tfAccountSumOfMoney);

        add(processBill);
        add(sumOfTheBill);
        add(tfSumOfTheBill);
        add(transferMoney);
        add(sender);
        add(receiver);
        add(sumToBeTransferred);
        add(tfSumToBeTransferred);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        setLayout(null);
        addClient = new JButton("Add new client");
        viewClients = new JButton("View Clients");
        updateClients = new JButton("Update Client");
        createAccount = new JButton("Create account");
        updateAccount = new JButton("Update account");
        viewAccount = new JButton("View accounts");
        deleteAccount = new JButton("Delete account");

        addClient.setBounds(0,0,150,30);
        viewClients.setBounds(0,40,150,30);
        updateClients.setBounds(0, 80,150,30);

        createAccount.setBounds(160,0,150,30);
        updateAccount.setBounds(160,40,150,30);
        viewAccount.setBounds(160,80,150,30);
        deleteAccount.setBounds(160,120,150,30);

        clientName = new JLabel("Client name");
        clientName.setBounds(0,150,100,20);
        tfClientName = new JTextField();
        tfClientName.setBounds(100,150,100,20);
        clientCnp = new JLabel("Client CNP");
        clientCnp.setBounds(0,170,100,20);
        tfClientCnp = new JTextField();
        tfClientCnp.setBounds(100,170,100,20);
        clientCardNumber = new JLabel("ID number");
        clientCardNumber.setBounds(0,190,100,20);
        tfClientCardNumber = new JTextField();
        tfClientCardNumber.setBounds(100,190,100,20);
        clientAddress = new JLabel("Client address");
        clientAddress.setBounds(0,210,100,20);
        tfClientAddress = new JTextField();
        tfClientAddress.setBounds(100,210,100,20);

        accountCardNumber = new JLabel("Card Number");
        accountCardNumber.setBounds(320,0,100,20);
        tfAccountCardNumber = new JTextField();
        tfAccountCardNumber.setBounds(420,0,100,20);
        accountType = new JLabel("Account type");
        accountType.setBounds(320,20,100,20);
        tfAccountType = new JTextField();
        tfAccountType.setBounds(420,20,100,20);
        accountSumOfMoney = new JLabel("Sum of money");
        accountSumOfMoney.setBounds(320,40,100,20);
        tfAccountSumOfMoney = new JTextField();
        tfAccountSumOfMoney.setBounds(420,40,100,20);

        processBill = new JButton("Process bill");
        processBill.setBounds(320,70,150,30);
        sumOfTheBill = new JLabel("Amount to pay");
        sumOfTheBill.setBounds(480,70,100,20);
        tfSumOfTheBill = new JTextField();
        tfSumOfTheBill.setBounds(580,70,100,20);

        transferMoney = new JButton("Transfer money");
        transferMoney.setBounds(320,115,150,30);
        sender = new JButton("Select sender");
        sender.setBounds(480,100,150,30);
        receiver = new JButton("Select receiver");
        receiver.setBounds(480,135,150,30);
        sumToBeTransferred = new JLabel("Transfer Amount");
        sumToBeTransferred.setBounds(320,180,100,20);
        tfSumToBeTransferred = new JTextField();
        tfSumToBeTransferred.setBounds(420,180,100,20);

        allClients = new JTable();

        allAccounts = new JTable();


    }

    public String getName(){
        return tfClientName.getText();
    }
    public String getCNP(){
        return tfClientCnp.getText();
    }
    public String getIDCardNumber(){
        return tfClientCardNumber.getText();
    }
    public String getAddress(){
        return tfClientAddress.getText();
    }
    public int getClientTableSelection(){return allClients.getSelectedRow();}
    public int getAccountTableSelection(){return allAccounts.getSelectedRow();}
    public Object getIdFromTable(int row){
        return allClients.getValueAt(row,0);
    }
    public Object getAccountIdFromTable(int row){
        return allAccounts.getValueAt(row,0);
    }
    public Object getCardNumberFromTable(int row){
        return allAccounts.getValueAt(row,1);
    }

    public String getCardNumber(){return tfAccountCardNumber.getText();}
    public String getAccountType(){return tfAccountType.getText();}
    public String getSumOfMoney(){return tfAccountSumOfMoney.getText();}
    public String getAmountToPay(){return tfSumOfTheBill.getText();}
    public String getSumToBeTransferred(){return tfSumToBeTransferred.getText();}

    public void setViewClientsTable(List<Client> clients){
        remove(allClients);
        String columns[]={"id","name","cardNumber","cnp","address"};
        DefaultTableModel tableModel = new DefaultTableModel(columns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(columns);
        for(Client client:clients){
            Object data[] = {client.getId(), client.getName(), client.getCardNumber(), client.getCnp(),client.getAddress()};
            tableModel.addRow(data);
        }
        allClients = new JTable(tableModel);
        add(allClients);
        allClients.setBounds(100,300,410,200);
        allClients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        allClients.getColumnModel().getColumn(2).setPreferredWidth(170);
        allClients.getColumnModel().getColumn(3).setPreferredWidth(150);
        allClients.setVisible(true);
    }

    public void setViewAccountsTable(List<Account> accounts){
        remove(allAccounts);
        String columns[]={"identificationNumber","cardNumber","type","sumOfMoney","date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(columns);
        for(Account account:accounts){
            Object data[]={account.getIdentificationNumber(),account.getCardNumber(),account.getType(),account.getSumOfMoney(),account.getCreationDate()};
            tableModel.addRow(data);
        }
        allAccounts = new JTable(tableModel);
        add(allAccounts);
        allAccounts.setBounds(100,510,410,200);
        allAccounts.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        allAccounts.getColumnModel().getColumn(1).setPreferredWidth(140);
        allAccounts.setVisible(true);
    }
    public void addNewClientButtonListener(ActionListener newClientButtonListener){
        addClient.addActionListener(newClientButtonListener);
    }

    public void viewClientsButtonListener(ActionListener viewClientsButtonListener){
        viewClients.addActionListener(viewClientsButtonListener);
    }

    public void updateClientButtonListener(ActionListener updateClientButtonListener){
        updateClients.addActionListener(updateClientButtonListener);
    }

    public void createAccountButtonListener(ActionListener createAccountButtonListener){
        createAccount.addActionListener(createAccountButtonListener);
    }

    public void viewAccountsButtonListener(ActionListener viewAccountsButtonListener){
        viewAccount.addActionListener(viewAccountsButtonListener);
    }

    public void updateAccountButtonListener(ActionListener updateAccountButtonListener){
        updateAccount.addActionListener(updateAccountButtonListener);
    }

    public void deleteAccountButtonListener(ActionListener deleteAccountButtonListener){
        deleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void processBillButtonListener(ActionListener processBillButtonListener){
        processBill.addActionListener(processBillButtonListener);
    }

    public void selectSenderButtonListener(ActionListener selectSenderButtonListener){
        sender.addActionListener(selectSenderButtonListener);
    }

    public void selectReceiverButtonListener(ActionListener selectReceiverButtonListener){
        receiver.addActionListener(selectReceiverButtonListener);
    }

    public void transferMoneyButtonListener(ActionListener transferMoneyButtonListener){
        transferMoney.addActionListener(transferMoneyButtonListener);
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    AccountDTO accountDTO;
    ClientDTO clientDTO;

    private JButton buttonCreateAccount;
    private JButton buttonUpdateAccount;
    private JButton buttonDeleteAccount;
    private JButton buttonViewAccount;
    private JButton buttonTransfer;

    private JButton buttonCreateClient;
    private JButton buttonUpdateClient;
    private JButton buttonDeleteClient;
    private JButton buttonViewClient;

    //Acount info
    private JTextField identificationNumberText;
    private JTextField typeText;
    private JTextField amountOfMoneyTxt;
    private JTextField creationDateTxt;
    private JTextField accountId1;
    private JTextField accountId2;
    private JTextField moneyAmountForTransfer;

    //Client info
    private JTextField clientIdTxt;
    private JTextField clientNameTxt;
    private JTextField clientIdentityCardNumberTxt;
    private JTextField clientAddressTxt;
    private JTextField clientPersonalNumericalCodeTxt;

    //labels
    private JLabel identificationNumberLabel;
    private JLabel typeLabel;
    private JLabel amountOfMoneyLabel;
    private JLabel creationDateLabel;
    private JLabel accountId1Label;
    private JLabel accountId2Label;
    private JLabel moneyAmountForTransferLabel;
    private JLabel clientIdLabel;
    private JLabel clientNameLabel;
    private JLabel clientAddressLabel;
    private JLabel clientIdentityCardNumberLabel;
    private JLabel clientPersonalNumericalCodeLabel;

    public EmployeeView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        //text fields
        add(identificationNumberLabel);
        add(identificationNumberText);
        identificationNumberText.setBackground(Color.YELLOW);

        add(typeLabel);
        add(typeText);
        typeText.setBackground(Color.YELLOW);

        add(amountOfMoneyLabel);
        add(amountOfMoneyTxt);
        amountOfMoneyTxt.setBackground(Color.YELLOW);

        add(creationDateLabel);
        add(creationDateTxt);
        creationDateTxt.setBackground(Color.LIGHT_GRAY);

        add(accountId1Label);
        add(accountId1);
        accountId1.setBackground(Color.LIGHT_GRAY);

        add(accountId2Label);
        add(accountId2);
        accountId2.setBackground(Color.LIGHT_GRAY);

        add(moneyAmountForTransferLabel);
        add(moneyAmountForTransfer);
        moneyAmountForTransfer.setBackground(Color.ORANGE);

        add(clientIdLabel);
        add(clientIdTxt);
        clientIdTxt.setBackground(Color.ORANGE);

        add(clientNameLabel);
        add(clientNameTxt);
        clientNameTxt.setBackground(Color.ORANGE);

        add(clientAddressLabel);
        add(clientAddressTxt);
        clientAddressTxt.setBackground(Color.cyan);

        add(clientIdentityCardNumberLabel);
        add(clientIdentityCardNumberTxt);
        clientIdentityCardNumberTxt.setBackground(Color.cyan);

        add(clientPersonalNumericalCodeLabel);
        add(clientPersonalNumericalCodeTxt);
        clientPersonalNumericalCodeTxt.setBackground(Color.cyan);

        //buttons
        add(buttonCreateAccount);
        buttonCreateAccount.setBackground(Color.BLUE);

        add(buttonUpdateAccount);
        buttonUpdateAccount.setBackground(Color.YELLOW);

        add(buttonDeleteAccount);
        buttonDeleteAccount.setBackground(Color.LIGHT_GRAY);

        add(buttonViewAccount);
        buttonViewAccount.setBackground(Color.GREEN);

        add(buttonTransfer);
        buttonTransfer.setBackground(Color.RED);

        add(buttonCreateClient);
        buttonCreateAccount.setBackground(Color.ORANGE);

        add(buttonUpdateClient);
        buttonUpdateClient.setBackground(Color.MAGENTA);

        add(buttonDeleteClient);
        buttonDeleteClient.setBackground(Color.WHITE);

        add(buttonViewClient);
        buttonViewClient.setBackground(Color.CYAN);
    }

    public void initializeFields() {
        this.clientDTO = new ClientDTO();
        this.accountDTO = new AccountDTO();
        identificationNumberLabel = new JLabel("identification nr account");
        identificationNumberText = new JTextField();

        typeLabel = new JLabel("type account");
        typeText = new JTextField();

        amountOfMoneyLabel = new JLabel("money amount");
        amountOfMoneyTxt = new JTextField();

        creationDateLabel = new JLabel("creation date");
        creationDateTxt = new JTextField();

        accountId1Label = new JLabel("id account1");
        accountId1 = new JTextField();

        accountId2Label = new JLabel("id account2");
        accountId2 = new JTextField();

        moneyAmountForTransferLabel = new JLabel("amount transfer");
        moneyAmountForTransfer = new JTextField();

        clientIdLabel = new JLabel("client ID");
        clientIdTxt = new JTextField();

        clientAddressLabel = new JLabel("client address");
        clientAddressTxt = new JTextField();

        clientNameLabel = new JLabel("client name");
        clientNameTxt = new JTextField();

        clientIdentityCardNumberLabel = new JLabel("client identity card number");
        clientIdentityCardNumberTxt = new JTextField();

        clientPersonalNumericalCodeLabel = new JLabel("client personal numerical code");
        clientPersonalNumericalCodeTxt = new JTextField();


        buttonCreateAccount = new JButton("Create Account");
        buttonUpdateAccount = new JButton("Update Account");
        buttonDeleteAccount = new JButton("Delete Account");
        buttonViewAccount = new JButton("View Account");
        buttonTransfer = new JButton("Transfer");
        buttonCreateClient = new JButton("Create Client");
        buttonUpdateClient = new JButton("Update Client");
        buttonDeleteClient = new JButton("Delete Client");
        buttonViewClient = new JButton("View Client");
    }

    public String getIdentificationNumber() {
        return identificationNumberText.getText();
    }

    public String getTypeTxt() {
        return typeText.getText();
    }

    public String getAmountOfMoney() {
        return amountOfMoneyTxt.getText();
    }

    public String getCreationDate() {
        return creationDateTxt.getText();
    }

    public String getId1() {
        return accountId1.getText();
    }

    public String getId2() {
        return accountId2.getText();
    }

    public String getMoneyAmountForTransfer() {
        return moneyAmountForTransfer.getText();
    }

    public String getClientId() {
        return clientIdTxt.getText();
    }

    public String getClientName() {
        return clientNameTxt.getText();
    }

    public String getClientIdentityCardNumber() {
        return clientIdentityCardNumberTxt.getText();
    }

    public String getClientAddress() {
        return clientAddressTxt.getText();
    }

    public String getClientPersonalNumericalCode() {
        return clientPersonalNumericalCodeTxt.getText();
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener) {
        buttonCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        buttonUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        buttonDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setViewAccountButtonListener(ActionListener viewAccountButtonListener) {
        buttonViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void setTransferButtonListener(ActionListener transferAccountButtonListener) {
        buttonTransfer.addActionListener(transferAccountButtonListener);
    }

    public void setCreateClientButtonListener(ActionListener createClientButtonListener) {
        buttonCreateClient.addActionListener(createClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        buttonUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setDeleteClientButtonListener(ActionListener deleteClientButtonListener) {
        buttonDeleteClient.addActionListener(deleteClientButtonListener);
    }

    public void setViewClientButtonListener(ActionListener viewClientButtonListener) {
        buttonViewClient.addActionListener(viewClientButtonListener);
    }


    public AccountDTO getAccountDTO() {
        initializeAccountDTO();
        return accountDTO;
    }

    public ClientDTO getClientDTO() {
        initializeClientDTO();
        return clientDTO;
    }

    public void initializeAccountDTO() {

        this.accountDTO.setIdentificationNr(Integer.parseInt(getIdentificationNumber()));
        this.accountDTO.setType(getTypeTxt());
        this.accountDTO.setMoneyAmount(Integer.parseInt(getAmountOfMoney()));
        this.accountDTO.setCreationDate(LocalDate.parse(getCreationDate()));
    }

    public void initializeClientDTO() {

        this.clientDTO.setName(getClientName());
        this.clientDTO.setAddress(getClientAddress());
        this.clientDTO.setIdentificationNr(Integer.parseInt(getClientIdentityCardNumber()));
        this.clientDTO.setPersonalNumericalCode(getClientPersonalNumericalCode());
    }
}
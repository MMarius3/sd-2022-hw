package view;

import model.Client;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame {

    private JButton newAccount;
    private JButton searchAccount;
    private JButton deleteAccount;
    private JButton payBill;
    private JButton transferBtn;
    private JButton updateAccount;

    private JTextField clientId;
    private JTextField accountId;
    private JTextField accountType;
    private JTextField accountBalance;
    private JTextField accountClient;
    private JTextField accountCreatedAt;


    public AccountView() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        addComponents();
    }

    private void initializeFields() {
        newAccount = new JButton("Create a new account");
        searchAccount = new JButton("Search account by client id");

        deleteAccount = new JButton("Delete this account");
        payBill = new JButton("Pay bill");
        transferBtn =new JButton("Transfer money");
        updateAccount =new JButton("Update account information");

        clientId = new JTextField("");
        accountId = new JTextField();
        accountType = new JTextField();
        accountBalance = new JTextField();
        accountClient = new JTextField();
        accountCreatedAt = new JTextField();

    }

    private void addComponents() {
        add(newAccount);
        add(searchAccount);
        add(new JLabel("Enter client id"));
        add(clientId);
        add(new JLabel("Account id"));
        add(accountId);
        add(new JLabel("Account type"));
        add(accountType);
        add(new JLabel("Account balance"));
        add(accountBalance);
        add(new JLabel("Client name"));
        add(accountClient);
        add(new JLabel("Created at"));
        add(accountCreatedAt);
        add(deleteAccount);
        add(updateAccount);
        add(payBill);
        add(transferBtn);
    }

    public void addNewAccountBtnListener(ActionListener btnListener) {
        newAccount.addActionListener(btnListener);
    }


    public void searchAccountBtnListener(ActionListener btnListener) {
        searchAccount.addActionListener(btnListener);
    }

    public void deleteAccountBtnListener(ActionListener btnListener) {
        deleteAccount.addActionListener(btnListener);
    }
    public void payBillBtnListener(ActionListener btnListener) {
        payBill.addActionListener(btnListener);
    }
    public void transferBtnListener(ActionListener btnListener) {
        transferBtn.addActionListener(btnListener);
    }
    public void updateBtnListener(ActionListener btnListener) {
        updateAccount.addActionListener(btnListener);
    }

    public Long getClientId() {
        return Long.parseLong(clientId.getText());
    }

    public String getAccountType() {
        return accountType.getText();
    }

    public String getAccountBalance() {
        return accountBalance.getText();
    }

    public String getAccountId() {
        return accountId.getText();
    }

    public void setAccountData(String accountId, String accountType, Long accountBalance, String name, String created_at) {
        this.accountId.setText(accountId);
        this.accountType.setText(accountType);
        if (accountBalance == null) {
            this.accountBalance.setText("");
        } else {
            this.accountBalance.setText(accountBalance.toString());

        }
        this.accountCreatedAt.setText(created_at);
        this.accountClient.setText(name);
    }

}

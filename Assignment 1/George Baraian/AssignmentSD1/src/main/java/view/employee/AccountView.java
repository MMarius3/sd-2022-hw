package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame {

    private JTextField accountID;
    private JTextField accountType;
    private JTextField accountAmount;
    private JTextField clientID;


    private JButton createAccountButton;
    private JButton deleteAccountsButton;

    public AccountView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(accountID);
        add(accountType);
        add(accountAmount);
        add(clientID);
        add(createAccountButton);
        add(deleteAccountsButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        accountID = new JTextField();
        accountType = new JTextField();
        accountAmount = new JTextField();
        clientID = new JTextField();
        createAccountButton = new JButton("Create account");
        deleteAccountsButton = new JButton("Delete accounts");
    }

    public String getId(){
        return accountID.getText();
    }

    public String getAccountType(){
        return accountType.getText();
    }

    public String getAmount(){
        return accountAmount.getText();
    }

    public String getClientID(){
        return clientID.getText();
    }

    public void setCreateAccountButton(ActionListener createAccountButtonListener){
        createAccountButton.addActionListener(createAccountButtonListener);
    }

    public void setDeleteAccountsButton(ActionListener deleteAccountsButtonListener){
        deleteAccountsButton.addActionListener(deleteAccountsButtonListener);
    }

    public void setVisible(){
        this.setVisible(true);
    }



}

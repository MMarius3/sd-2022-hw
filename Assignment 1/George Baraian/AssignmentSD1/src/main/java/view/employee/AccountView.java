package view.employee;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame {

    private JTextField accountID;
    private JTextField accountType;
    private JTextField accountAmount;
    private JTextField clientID;


    private JButton createAccountButton;
    private JButton deleteAccountsButton;
    private JButton viewClientAccountsButton;

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
        add(viewClientAccountsButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        accountID = new JTextField();
        accountType = new JTextField();
        accountAmount = new JTextField();
        clientID = new JTextField();
        createAccountButton = new JButton("Create account");
        deleteAccountsButton = new JButton("Delete accounts");
        viewClientAccountsButton = new JButton("View this client's accounts");
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

    public void setViewClientAccountsButton(ActionListener viewClientAccountsButtonListener){
        viewClientAccountsButton.addActionListener(viewClientAccountsButtonListener);
    }

    public void createResultTable(List<Account> all) {
        JFrame f = new JFrame();
        String data[][] = new String[all.size()][4];
        for(int i = 0; i < all.size(); i++){
            String id = String.valueOf(all.get(i).getId());
            String type = all.get(i).getType();
            String amount= String.valueOf(all.get(i).getAmount());
            String clientId = String.valueOf(all.get(i).getClientID());

            data[i][0] = id;
            data[i][1] = type;
            data[i][2] = amount;
            data[i][3] = clientId;
        }
        String column[] = {"id", "type", "amount", "clientId"};

        JTable jT = new JTable(data, column);
        jT.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(jT);
        f.add(sp);
        f.setSize(300,400);
        f.setVisible(true);

    }


    public void setVisible(){
        this.setVisible(true);
    }



}

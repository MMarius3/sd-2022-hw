package view;

import model.ClientAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;

public class ProcessUtilitiesView extends JFrame {
    private JLabel processUtilitiesLabel;
    private JLabel dummy;
    private JLabel clientAccountLabel;
    private JComboBox<ClientAccount> clientAccountJComboBox;
    private JLabel utilityLabel;
    private JComboBox<String> utilityComboBox;
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton payButton;

    public ProcessUtilitiesView(){
        setSize(600, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(0,2));

        add(processUtilitiesLabel);
        add(dummy);
        add(clientAccountLabel);
        add(clientAccountJComboBox);
        add(utilityLabel);
        add(utilityComboBox);
        add(amountLabel);
        add(amountField);
        add(payButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields(){
        processUtilitiesLabel = new JLabel("Process Utilities");
        clientAccountLabel = new JLabel("Account: ");
        utilityLabel = new JLabel("Utility: ");
        amountLabel = new JLabel("Amount: ");
        amountField = new JTextField();
        payButton = new JButton("Pay");
        dummy = new JLabel("");

        clientAccountJComboBox = new JComboBox();
        utilityComboBox = new JComboBox();

        utilityComboBox.addItem("Electricity");
        utilityComboBox.addItem("Gas");
        utilityComboBox.addItem("Water");
        utilityComboBox.addItem("Internet");

        utilityComboBox.setVisible(true);
    }

    public void addProcessUtilitiesButtonListener(ActionListener processUtilitiesButtonListener){
        payButton.addActionListener(processUtilitiesButtonListener);
    }

    public void setClientAccountComboBox(ArrayList<ClientAccount> clientAccounts){
        clientAccountJComboBox.removeAllItems();
        for(ClientAccount c: clientAccounts){
            clientAccountJComboBox.addItem(c);
        }
        clientAccountJComboBox.setVisible(true);
    }

    public ClientAccount getSelectedClientAccount(){
        return (ClientAccount) clientAccountJComboBox.getSelectedItem();
    }

    public String getSelectedUtility(){
        return (String) utilityComboBox.getSelectedItem();
    }

    public String getAmount(){
        return amountField.getText();
    }
}

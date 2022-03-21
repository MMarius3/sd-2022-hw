package view;

import model.ClientAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;

public class TransferMoneyView extends JFrame {
    private JComboBox<ClientAccount> fromClientAccountComboBox;
    private JComboBox<ClientAccount> toClientAccountComboBox;

    private JLabel tmLabel,dummy;
    private JLabel fromClientAccountLabel;
    private JLabel toClientAccountLabel;
    private JLabel amountLabel;

    private JTextField amountField;
    private JButton transferButton;

    public TransferMoneyView(){

        setSize(600,300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(0,2));

        add(tmLabel);
        add(dummy);
        add(fromClientAccountLabel);
        add(fromClientAccountComboBox);
        add(toClientAccountLabel);
        add(toClientAccountComboBox);
        add(amountLabel);
        add(amountField);
        add(transferButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields(){
        tmLabel = new JLabel("Transfer Money");
        dummy = new JLabel("");
        fromClientAccountLabel = new JLabel("From: ");
        toClientAccountLabel = new JLabel("To: ");
        amountLabel = new JLabel("Amount: ");

        transferButton = new JButton("Transfer");
        amountField = new JTextField();

        toClientAccountComboBox = new JComboBox();
        fromClientAccountComboBox = new JComboBox();
    }

    public void addTransferMoneyButtonListener(ActionListener transferMoneyListener){
        transferButton.addActionListener(transferMoneyListener);
    }

    public String getAmount(){
        return amountField.getText();
    }

    public ClientAccount getSelectedAccountFrom() {return (ClientAccount) fromClientAccountComboBox.getSelectedItem();}

    public ClientAccount getSelectedAccountTo() {return (ClientAccount) toClientAccountComboBox.getSelectedItem();}

    public void setComboBoxes(ArrayList<ClientAccount> clientAccounts){
        fromClientAccountComboBox.removeAllItems();
        toClientAccountComboBox.removeAllItems();

        for(ClientAccount c: clientAccounts){
            fromClientAccountComboBox.addItem(c);
            toClientAccountComboBox.addItem(c);
        }

        fromClientAccountComboBox.setVisible(true);
        toClientAccountComboBox.setVisible(true);
    }
}

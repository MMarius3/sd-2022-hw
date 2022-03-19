package view.client.account;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class TransferMoneyView extends JFrame {
    private Container container;

    private JLabel fromAccountLabel;
    private JLabel toAccountLabel;
    private JLabel moneyAmountLabel;

    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField moneyAmountField;

    private JButton transferMoneyButton;
    private JButton cancelButton;

    public TransferMoneyView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame(){
        setSize(280, 220);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Transfer money");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        fromAccountLabel = new JLabel("From account id");
        toAccountLabel = new JLabel("To account id");
        moneyAmountLabel = new JLabel("Money");

        fromAccountField = new JTextField();
        toAccountField = new JTextField();
        moneyAmountField = new JTextField();

        transferMoneyButton = new JButton("Transfer");
        cancelButton = new JButton("Cancel");
    }

    private void setBounds() {
        fromAccountLabel.setBounds(30, 10, 120, 20);
        toAccountLabel.setBounds(30, 40, 120, 20);
        moneyAmountLabel.setBounds(30, 70, 120, 20);

        fromAccountField.setBounds(150, 10, 100, 20);
        toAccountField.setBounds(150, 40, 100, 20);
        moneyAmountField.setBounds(150, 70, 100, 20);

        transferMoneyButton.setBounds(30, 110, 90, 30);
        cancelButton.setBounds(150, 110, 90, 30);
    }

    private void addComponentsToContainer() {
        container.add(fromAccountLabel);
        container.add(toAccountLabel);
        container.add(moneyAmountLabel);
        container.add(fromAccountField);
        container.add(toAccountField);
        container.add(moneyAmountField);
        container.add(transferMoneyButton);
        container.add(cancelButton);
    }

    public void setTransferMoneyButtonActionListener(ActionListener actionListener) {
        this.transferMoneyButton.addActionListener(actionListener);
    }

    public void setCancelButtonActionListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }
}

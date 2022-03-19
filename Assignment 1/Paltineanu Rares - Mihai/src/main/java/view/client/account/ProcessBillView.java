package view.client.account;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class ProcessBillView extends JFrame {
    private Container container;

    private JLabel accountIdLabel;
    private JLabel moneyLabel;

    private JTextField accountIdField;
    private JTextField moneyField;

    private JButton processBillButton;
    private JButton cancelButton;

    public ProcessBillView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(300, 300);
        setResizable(false);
        setVisible(true);
        setTitle("Add information");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        accountIdLabel = new JLabel("Account id");
        moneyLabel = new JLabel("Money");

        accountIdField = new JTextField();
        moneyField = new JTextField();

        processBillButton = new JButton("Process bill");
        cancelButton = new JButton("Cancel");
    }

    private void setBounds() {
        accountIdLabel.setBounds(30, 10, 100, 20);
        moneyLabel.setBounds(30, 40, 100, 20);

        accountIdField.setBounds(130, 10, 110, 20);
        moneyField.setBounds(130, 40, 110, 20);

        processBillButton.setBounds(30, 100, 90, 30);
        cancelButton.setBounds(130, 100, 90, 30);
    }

    private void addComponentsToContainer() {
        container.add(accountIdLabel);
        container.add(moneyLabel);
        container.add(accountIdField);
        container.add(moneyField);
        container.add(processBillButton);
        container.add(cancelButton);
    }

    public void setProcessBillButtonListener(ActionListener actionListener) {
        this.processBillButton.addActionListener(actionListener);
    }

    public void setCancelAddInformationListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }
}

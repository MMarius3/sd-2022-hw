package view.client.account;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class ActionAccountView extends JFrame {

    protected Container container;

    private JLabel numberLabel;
    private JLabel typeLabel;
    private JLabel moneyLabel;
    private JLabel idLabel;

    private JTextField numberField;
    private JTextField typeField;
    private JTextField moneyField;
    private JTextField idField;

    private JButton actionButton;
    private JButton cancelButton;

    public ActionAccountView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(280, 280);
        setResizable(false);
        setVisible(true);
        setTitle("Add account");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        idLabel = new JLabel("Client id");
        numberLabel = new JLabel("Card number");
        typeLabel = new JLabel("Type");
        moneyLabel = new JLabel("Money");

        idField = new JTextField();
        numberField = new JTextField();
        typeField = new JTextField();
        moneyField = new JTextField();

        actionButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void setBounds() {
        idLabel.setBounds(30, 10, 100, 20);
        numberLabel.setBounds(30, 40, 100, 20);
        typeLabel.setBounds(30, 70, 100, 20);
        moneyLabel.setBounds(30, 100, 100, 20);

        idField.setBounds(130, 10, 110, 20);
        numberField.setBounds(130, 40, 110, 20);
        typeField.setBounds(130, 70, 110, 20);
        moneyField.setBounds(130, 100, 110, 20);

        actionButton.setBounds(30, 200, 90, 30);
        cancelButton.setBounds(150, 200, 90, 30);
    }

    private void addComponentsToContainer() {
        container.add(idLabel);
        container.add(numberLabel);
        container.add(typeLabel);
        container.add(moneyLabel);
        container.add(numberField);
        container.add(typeField);
        container.add(moneyField);
        container.add(idField);
        container.add(actionButton);
        container.add(cancelButton);
    }

    public void setActionButtonListener(ActionListener actionListener) {
        this.actionButton.addActionListener(actionListener);
    }

    public void setCancelAddInformationListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

}

package view.client.information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddInformationView extends JFrame {
    private Container container;

    private JLabel nameLabel;
    private JLabel cnpLabel;
    private JLabel addressLabel;

    private JTextField nameField;
    private JTextField cnpField;
    private JTextField addressField;

    private JButton addInformationButton;
    private JButton cancelButton;

    public AddInformationView() {
        initializeFields();
        setLocationAndBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(280, 280);
        setResizable(false);
        setVisible(true);
        setTitle("Add information");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        nameLabel = new JLabel("Name");
        cnpLabel = new JLabel("CNP");
        addressLabel = new JLabel("Address");

        nameField = new JTextField();
        cnpField = new JTextField();
        addressField = new JTextField();

        addInformationButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void setLocationAndBounds() {
        nameLabel.setBounds(30, 30, 100, 20);
        cnpLabel.setBounds(30, 60, 100, 20);
        addressLabel.setBounds(30, 90, 100, 20);

        nameField.setBounds(140, 30, 100, 20);
        cnpField.setBounds(140, 60, 100, 20);
        addressField.setBounds(140, 90, 100, 20);

        addInformationButton.setBounds(140, 150, 90, 30);
        cancelButton.setBounds(30, 150, 90, 30);

    }

    private void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(cnpLabel);
        container.add(addressLabel);
        container.add(nameField);
        container.add(cnpField);
        container.add(addressField);
        container.add(addInformationButton);
        container.add(cancelButton);
    }

    public void setAddInformationListener(ActionListener actionListener) {
        this.addInformationButton.addActionListener(actionListener);
    }

    public void setCancelAddInformationListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

    public String getName() {
        return this.nameField.getText();
    }

    public String getAddress() {
        return this.addressField.getText();
    }

    public String getCNP() {
        return this.cnpField.getText();
    }
}

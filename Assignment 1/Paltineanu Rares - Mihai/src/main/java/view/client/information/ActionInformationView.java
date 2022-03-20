package view.client.information;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
@Setter
public class ActionInformationView extends JFrame {
    protected Container container;

    protected JLabel nameLabel;
    protected JLabel cnpLabel;
    protected JLabel addressLabel;

    protected JTextField nameField;
    protected JTextField cnpField;
    protected JTextField addressField;

    protected JButton addInformationButton;
    protected JButton cancelButton;

    public ActionInformationView() {
        initializeFields();
        setBounds();
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

    private void setBounds() {
        nameLabel.setBounds(30, 70, 100, 20);
        cnpLabel.setBounds(30, 100, 100, 20);
        addressLabel.setBounds(30, 130, 100, 20);

        nameField.setBounds(130, 70, 110, 20);
        cnpField.setBounds(130, 100, 110, 20);
        addressField.setBounds(130, 130, 110, 20);

        addInformationButton.setBounds(30, 190, 90, 30);
        cancelButton.setBounds(130, 190, 90, 30);
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

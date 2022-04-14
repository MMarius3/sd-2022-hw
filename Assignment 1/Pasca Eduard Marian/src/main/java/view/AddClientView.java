package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AddClientView extends JFrame{
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel idCardNumberLabel;
    private JTextField icnTextField;
    private JLabel persNumCodeLabel;
    private JTextField pncTextField;
    private JLabel addressLabel;
    private JTextField addressTextField;
    private JButton addClientButton;

    public AddClientView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(nameLabel);
        add(nameTextField);
        add(idCardNumberLabel);
        add(icnTextField);
        add(persNumCodeLabel);
        add(pncTextField);
        add(addressLabel);
        add(addressTextField);
        add(addClientButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        nameLabel = new JLabel("Name:");
        idCardNumberLabel = new JLabel("Identification Card Number:");
        persNumCodeLabel = new JLabel("Personal Numeric Code:");
        addressLabel = new JLabel("Address:");
        nameTextField = new JTextField();
        icnTextField = new JTextField();
        pncTextField = new JTextField();
        addressTextField = new JTextField();
        addClientButton = new JButton("Add client");
    }

    public String getNameTextField(){ return nameTextField.getText(); }

    public int getIcnTextField(){ return Integer.valueOf(icnTextField.getText()); }

    public int getPncTextField(){ return Integer.valueOf(pncTextField.getText()); }

    public String getAddressTextField(){ return addressTextField.getText(); }

    public void addClientButtonListener(ActionListener addClientBtnListener){
        addClientButton.addActionListener(addClientBtnListener);
    }
}

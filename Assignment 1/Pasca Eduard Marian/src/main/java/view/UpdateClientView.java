package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UpdateClientView extends JFrame{
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel idCardNumberLabel;
    private JTextField icnTextField;
    private JLabel persNumCodeLabel;
    private JTextField pncTextField;
    private JLabel addressLabel;
    private JTextField addressTextField;
    private JButton updateClientButton;

    public UpdateClientView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(idLabel);
        add(idTextField);
        add(nameLabel);
        add(nameTextField);
        add(idCardNumberLabel);
        add(icnTextField);
        add(persNumCodeLabel);
        add(pncTextField);
        add(addressLabel);
        add(addressTextField);
        add(updateClientButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        nameLabel = new JLabel("Name:");
        idCardNumberLabel = new JLabel("Identification Card Number:");
        persNumCodeLabel = new JLabel("Personal Numeric Code:");
        addressLabel = new JLabel("Address:");
        nameTextField = new JTextField();
        icnTextField = new JTextField();
        pncTextField = new JTextField();
        addressTextField = new JTextField();
        updateClientButton = new JButton("Update client");
    }

    public Long getId(){ return Long.valueOf(idTextField.getText()); }

    public String getNameTextField(){ return nameTextField.getText(); }

    public int getIcnTextField(){ return Integer.valueOf(icnTextField.getText()); }

    public int getPncTextField(){ return Integer.valueOf(pncTextField.getText()); }

    public String getAddressTextField(){ return addressTextField.getText(); }

    public void updateClientButtonListener(ActionListener updateClientBtnListener){
        updateClientButton.addActionListener(updateClientBtnListener);
    }
}

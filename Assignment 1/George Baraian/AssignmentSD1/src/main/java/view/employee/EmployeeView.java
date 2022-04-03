package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JTextField clientId;
    private JTextField clientName;
    private JTextField clientAddress;
    private JTextField clientPersonalNumericalCode;
    private JButton createClientButton;

    public EmployeeView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(clientId);
        add(clientName);
        add(clientAddress);
        add(clientPersonalNumericalCode);
        add(createClientButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        clientId = new JTextField();
        clientName = new JTextField();
        clientAddress = new JTextField();
        clientPersonalNumericalCode = new JTextField();
        createClientButton = new JButton("Create a client");
    }

    public String getId(){
        return clientId.getText();
    }

    public String getName(){
        return clientName.getText();
    }

    public String getAddress(){
        return clientAddress.getText();
    }

    public String getPersonalNumericalCode(){
        return clientPersonalNumericalCode.getText();
    }

    public void setCreateClientButtonListener(ActionListener createClientAccountButtonListener){
        createClientButton.addActionListener(createClientAccountButtonListener);
    }

    public void setVisible(){
        this.setVisible(true);
    }
}

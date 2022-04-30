package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JButton addClient;
    private JButton searchClient;
    private JButton updateClient;
    private JButton accountOperations;
    private JTextField clientPersonalCode;
    private JTextField clientNumericalCode;
    private JTextField clientIdCardNumber;
    private JTextField clientAddress;
    private JTextField clientCreatedAt;
    private JTextField clientName;
    private JLabel clientCnp;
    private JLabel findClientCnp;
    private JLabel idCardnNumber;
    private JLabel address;
    private JLabel name;

    public EmployeeView() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        addClient = new JButton("Add a new Client");
        searchClient = new JButton("Search client by numerical code");
        clientNumericalCode = new JTextField("");

        updateClient = new JButton("Update Client Data");

        clientPersonalCode = new JTextField();
        clientIdCardNumber = new JTextField();
        clientAddress = new JTextField();
        clientCreatedAt = new JTextField();
        clientName = new JTextField();
        findClientCnp= new JLabel("Numerical code (CNP)");
        clientCnp = new JLabel("Numerical code (CNP)");
        idCardnNumber=new JLabel("ID-Card number");
        address=new JLabel("Address");
        name=new JLabel("Customer name");

        accountOperations=new JButton("Account operations");

    }

    public void addNewClientBtnListener(ActionListener btnListener) {
        addClient.addActionListener(btnListener);
    }

    public void searchClientBtnListener(ActionListener btnListener) {
        searchClient.addActionListener(btnListener);
    }

    public void updateClientBtnListener(ActionListener btnListener) {
        updateClient.addActionListener(btnListener);
    }

    public void accountOperationsBtnListener(ActionListener btnListener){accountOperations.addActionListener(btnListener);}

    private void addComponents() {
        add(addClient);
        add(findClientCnp);
        add(clientNumericalCode);
        add(searchClient);
        add(name);
        add(clientName);
        add(clientCnp);
        add(clientPersonalCode);
        add(idCardnNumber);
        add(clientIdCardNumber);
        add(address);
        add(clientAddress);
        add(new JLabel("Created at"));
        add(clientCreatedAt);
        add(accountOperations);
        add(updateClient);

    }

    public void setClientFields(String personalCode, String idCardNumber, String address, String created_at, String name) {

        clientPersonalCode.setText(personalCode);
        clientIdCardNumber.setText(idCardNumber);
        clientAddress.setText(address);
        clientCreatedAt.setText(created_at);
        clientName.setText(name);
    }

    public String getSearchedNumericalCode() {
        return clientNumericalCode.getText();
    }

    public String getClientIdCardNumber() {
        return clientIdCardNumber.getText();
    }
    public String getClientPersonalCode() {
        return clientPersonalCode.getText();
    }

    public String getClientAddress() {
        return clientAddress.getText();
    }
    public String getClientCreatedAt() {
        return clientCreatedAt.getText();
    }
    public String getClientName() {
        return clientName.getText();
    }


}

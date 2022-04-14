package view;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class NewClientView extends JFrame {

    private JTextField cardNumber;
    private JTextField numericalCode;
    private JTextField address;
    private JTextField name;
    private JButton saveClient;


    public NewClientView() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(new JLabel("ID-Card number"));
        add(cardNumber);
        add(new JLabel("Numerical code (CNP)"));
        add(numericalCode);
        add(new JLabel("Billing address"));
        add(address);
        add(new JLabel("Name"));
        add(name);
        add(saveClient);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        name = new JTextField();
        cardNumber = new JTextField();
        address = new JTextField();
        numericalCode = new JTextField();
        saveClient = new JButton("Save client");

    }
    public void addNewClientBtnListener(ActionListener btnListener) {
        saveClient.addActionListener(btnListener);
    }

    public String getCardNumber() {
        return cardNumber.getText();
    }


    public String getNumericalCode() {
        return numericalCode.getText();
    }


    public String getAddress() {
        return address.getText();
    }


    public String getClientName() {
        return name.getText();
    }

}

package view.employee;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeAddClientView extends JFrame {

    private JTextField tfName;
    private JTextField tfIdCardNr;
    private JTextField tfPNC;
    private JTextField tfAddress;
    private JTextField tfEmail;
    private JButton btnAddClient;

    public EmployeeAddClientView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfName);
        add(tfIdCardNr);
        add(tfPNC);
        add(tfAddress);
        add(tfEmail);
        add(btnAddClient);
    }

    private void initializeFields() {
        tfName = new JTextField("name");
        tfIdCardNr = new JTextField("id card nr");
        tfPNC = new JTextField("PNC");
        tfAddress = new JTextField("address");
        tfEmail = new JTextField("email");
        btnAddClient = new JButton("Add Client");
    }

    public String getName() {
        return tfName.getText();
    }

    public String getIdCardNr() {
        return tfIdCardNr.getText();
    }

    public String getPNC() {
        return tfPNC.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getEmail() {
        return tfEmail.getText();
    }

    public void addClientBtnListener(ActionListener addClientBtnListener) {
        btnAddClient.addActionListener(addClientBtnListener);
    }

}

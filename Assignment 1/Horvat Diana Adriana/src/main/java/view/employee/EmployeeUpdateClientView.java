package view.employee;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeUpdateClientView extends JFrame {

    private JTextField tfPNC;
    private JTextField tfName;
    private JTextField tfIdCardNr;
    private JTextField tfAddress;
    private JTextField tfEmail;
    private JButton btnUpdateClient;
    private JButton btnSearch;

    public EmployeeUpdateClientView() {
        setSize(400, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfPNC);
        add(btnSearch);
        add(tfName);
        add(tfIdCardNr);
        add(tfAddress);
        add(tfEmail);
        add(btnUpdateClient);
    }

    private void initializeFields() {
        tfName = new JTextField("name");
        tfIdCardNr = new JTextField("id card nr");
        tfAddress = new JTextField("address");
        tfEmail = new JTextField("email");
        tfPNC = new JTextField("PNC");
        btnSearch = new JButton("Search client");
        btnUpdateClient = new JButton("Update Client");
    }

    public void setClientData(String name, Long idCardNr, String address, String email){
        tfName.setText(name);
        tfIdCardNr.setText(idCardNr.toString());
        tfAddress.setText(address);
        tfEmail.setText(email);
    }

    public void addEditClientBtnListener (ActionListener editClientBtnListener) {
        btnUpdateClient.addActionListener(editClientBtnListener);
    }

    public void addSearchClientBtnListener (ActionListener searchClientBtnListener) {
        btnSearch.addActionListener(searchClientBtnListener);
    }

    public String getTfPNC() {
        return tfPNC.getText();
    }

    public void setTfPNC(JTextField tfPNC) {
        this.tfPNC = tfPNC;
    }

    public String getTfName() {
        return tfName.getText();
    }

    public void setTfName(JTextField tfName) {
        this.tfName = tfName;
    }

    public String getTfIdCardNr() {
        return tfIdCardNr.getText();
    }

    public void setTfIdCardNr(JTextField tfIdCardNr) {
        this.tfIdCardNr = tfIdCardNr;
    }

    public String getTfAddress() {
        return tfAddress.getText();
    }

    public void setTfAddress(JTextField tfAddress) {
        this.tfAddress = tfAddress;
    }

    public String getTfEmail() {
        return tfEmail.getText();
    }

    public void setTfEmail(JTextField tfEmail) {
        this.tfEmail = tfEmail;
    }
}

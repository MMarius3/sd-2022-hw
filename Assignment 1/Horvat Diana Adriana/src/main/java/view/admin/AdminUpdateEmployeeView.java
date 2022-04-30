package view.admin;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminUpdateEmployeeView extends JFrame {

    private JTextField tfOldUsername;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;

    public AdminUpdateEmployeeView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfOldUsername);
        add(btnSearch);
        add(tfUsername);
        add(tfPassword);
        add(btnUpdate);
        add(btnDelete);
    }

    private void initializeFields() {
        tfOldUsername = new JTextField("username");
        tfUsername = new JTextField("username");
        tfPassword = new JTextField("password");
        btnSearch = new JButton("Search employee");
        btnUpdate = new JButton("Update employee");
        btnDelete = new JButton("Delete employee");
    }

    public void searchEmployeeBtnListener(ActionListener searchEmployeeBtnListener) {
        btnSearch.addActionListener(searchEmployeeBtnListener);
    }

    public void updateEmployeeBtnListener(ActionListener updateEmployeeBtnListener) {
        btnUpdate.addActionListener(updateEmployeeBtnListener);
    }

    public void deleteEmployeeBtnListener(ActionListener deleteEmployeeBtnListener) {
        btnDelete.addActionListener(deleteEmployeeBtnListener);
    }


    public String getTfOldUsername() {
        return tfOldUsername.getText();
    }

    public String getTfUsername() {
        return tfUsername.getText();
    }

    public void setTfUsername(String tfUsername) {
        this.tfUsername.setText(tfUsername);
    }

    public String getTfPassword() {
        return tfPassword.getText();
    }

    public void setTfPassword(String tfPassword) {
        this.tfPassword.setText(tfPassword);
    }
}

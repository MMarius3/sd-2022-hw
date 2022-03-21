package view.admin;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminAddEmployeeView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnAddEmployee;

    public AdminAddEmployeeView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnAddEmployee);
    }

    private void initializeFields() {
        tfUsername = new JTextField("username");
        tfPassword = new JTextField("password");
        btnAddEmployee = new JButton("Add Employee");
    }

    public void addEmployeeBtnListener(ActionListener addEmployeeBtnListener) {
        btnAddEmployee.addActionListener(addEmployeeBtnListener);
    }

    public String getTfUsername() {
        return tfUsername.getText();
    }

    public String getTfPassword() {
        return tfPassword.getText();
    }
}

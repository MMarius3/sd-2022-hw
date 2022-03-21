package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    private JTextField employeeId;
    private JTextField username;
    private JTextField password;
    private JButton deleteEmployee;
    private JButton updateEmployee;
    private JButton createEmployee;
    private JButton searchEmployee;

    public AdminView() {
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        employeeId = new JTextField();
        username = new JTextField();
        password = new JTextField();

        deleteEmployee=new JButton("Delete this employee acount");
        updateEmployee=new JButton("Update employee information");
        createEmployee=new JButton("Create new employee acount");
        searchEmployee=new JButton("Search employee by id");

    }
    public void addComponents(){
        add(createEmployee);
        add(searchEmployee);
        add(new JLabel("Employee id"));
        add(employeeId);
        add(new JLabel("Employee username"));
        add(username);
        add(new JLabel("Employee password"));
        add(password);
        add(updateEmployee);
        add(deleteEmployee);
    }
    public void createEmployeeBtnListener(ActionListener btnListener) {
        createEmployee.addActionListener(btnListener);
    }

    public void updateEmployeeBtnListener(ActionListener btnListener) {
        updateEmployee.addActionListener(btnListener);
    }

    public void searchEmployeeBtnListener(ActionListener btnListener) {
        searchEmployee.addActionListener(btnListener);
    }

    public void deleteEmployeeBtnListener(ActionListener btnListener) {
        deleteEmployee.addActionListener(btnListener);
    }
    public Long getEmployeeId(){
        return Long.parseLong(employeeId.getText());
    }
    public String getEmployeeUsername(){
        return username.getText();
    }
    public String getEmployeePassword(){
        return password.getText();
    }
    public void setEmployeeData(String username,String password,Long employeeId) {
      this.username.setText(username);
      this.password.setText(password);
      this.employeeId.setText(String.valueOf(employeeId));
    }
}

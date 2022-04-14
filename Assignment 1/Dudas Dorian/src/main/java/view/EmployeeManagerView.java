package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeManagerView extends JFrame {
    private JButton btnBack;

    private JTextField tfUsername;
    private JTextField tfPassword;

    private JButton btnShowEmployees;

    private JButton btnAddEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;

    private JTable employeeTable;
    private JPanel panel;

    private JTextField tfStartDate, tfEndDate;
    private JButton btnGenerateReport;

    public EmployeeManagerView(){
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(panel);

        add(new JLabel("Username"));
        add(tfUsername);
        add(new JLabel("Password"));
        add(tfPassword);

        add(btnShowEmployees);
        add(btnAddEmployee);
        add(btnUpdateEmployee);
        add(btnDeleteEmployee);

        add(new JLabel("Use the dd/MM/yyyy format for the date fields"));
        add(new JLabel("Start Date"));
        add(tfStartDate);
        add(new JLabel("End Date"));
        add(tfEndDate);

        add(btnGenerateReport);

        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void initializeFields(){
        String[] columns = {"ID", "Username", "Password"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(model);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(employeeTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(employeeTable);

        tfUsername = new JTextField();
        tfPassword = new JTextField();

        btnShowEmployees = new JButton("Show Employees");
        btnAddEmployee = new JButton("Add Employee");
        btnUpdateEmployee = new JButton("Update Employee");
        btnDeleteEmployee = new JButton("Delete Employee");

        tfStartDate = new JTextField();
        tfEndDate = new JTextField();

        btnGenerateReport = new JButton("Generate Report");

        btnBack = new JButton("Back to Login");
    }

    public void addViewEmployeesButtonListener(ActionListener actionListener) {
        btnShowEmployees.addActionListener(actionListener);
    }

    public void addCreateEmployeesButtonListener(ActionListener actionListener) {
        btnAddEmployee.addActionListener(actionListener);
    }

    public void addUpdateEmployeesButtonListener(ActionListener actionListener) {
        btnUpdateEmployee.addActionListener(actionListener);
    }

    public void addDeleteEmployeesButtonListener(ActionListener actionListener) {
        btnDeleteEmployee.addActionListener(actionListener);
    }

    public void addBackButtonListener(ActionListener actionListener) {
        btnBack.addActionListener(actionListener);
    }

    public void addGenerateReportListener(ActionListener actionListener){
        btnGenerateReport.addActionListener(actionListener);
    }

    public JTextField getTfUsername() {
        return tfUsername;
    }

    public JTextField getTfPassword() {
        return tfPassword;
    }

    public JTable getEmployeeTable() {
        return employeeTable;
    }

    public JTextField getTfStartDate() {
        return tfStartDate;
    }

    public JTextField getTfEndDate() {
        return tfEndDate;
    }
}

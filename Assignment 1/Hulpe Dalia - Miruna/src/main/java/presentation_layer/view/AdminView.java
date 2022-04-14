package presentation_layer.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private JButton btnViewEmployees;
    private JButton btnUpdateEmployees;
    private JButton btnAddEmployees;
    private JButton btnDeleteEmployees;
    private JButton generateReports;

    private JLabel lUsername1Employee;
    private JLabel lPassword1Employee;
    private JLabel lId_Series1Employee;
    private JLabel lId_Number1Employee;
    private JLabel lPnc1Employee;
    private JLabel lAddress1Employee;

    private JTextField tfUsername1Employee;
    private JTextField tfPassword1Employee;
    private JTextField tfId_Series1Employee;
    private JTextField tfId_Number1Employee;
    private JTextField tfPnc1Employee;
    private JTextField tfAddress1Employee;

    private JLabel lUsername2Employee;
    private JLabel lPassword2Employee;
    private JLabel lId_Series2Employee;
    private JLabel lId_Number2Employee;
    private JLabel lPnc2Employee;
    private JLabel lAddress2Employee;

    private JTextField tfUsername2Employee;
    private JTextField tfPassword2Employee;
    private JTextField tfId_Series2Employee;
    private JTextField tfId_Number2Employee;
    private JTextField tfPnc2Employee;
    private JTextField tfAddress2Employee;

    private JTable allEmployeeTable;
    private JTable reports;

    public AdminView() {
        setSize(1200, 900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(btnViewEmployees);
        add(btnUpdateEmployees);
        add(btnAddEmployees);
        add(btnDeleteEmployees);
        add(generateReports);

        add(lUsername1Employee);
        add(tfUsername1Employee);
        add(lPassword1Employee);
        add(tfPassword1Employee);
        add(lId_Series1Employee);
        add(tfId_Series1Employee);
        add(lId_Number1Employee);
        add(tfId_Number1Employee);
        add(lPnc1Employee);
        add(tfPnc1Employee);
        add(lAddress1Employee);
        add(tfAddress1Employee);

        add(lUsername2Employee);
        add(tfUsername2Employee);
        add(lPassword2Employee);
        add(tfPassword2Employee);
        add(lId_Series2Employee);
        add(tfId_Series2Employee);
        add(lId_Number2Employee);
        add(tfId_Number2Employee);
        add(lPnc2Employee);
        add(tfPnc2Employee);
        add(lAddress2Employee);
        add(tfAddress2Employee);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        setLayout(null);

        btnViewEmployees = new JButton("View Employees");
        btnUpdateEmployees = new JButton("Update Employees");
        btnAddEmployees = new JButton("Add Employees");
        btnDeleteEmployees = new JButton("Delete Employees");
        generateReports = new JButton("Generate Reports");

        btnViewEmployees.setBounds(10, 10, 150, 20);
        btnUpdateEmployees.setBounds(160, 10, 150, 20);
        btnAddEmployees.setBounds(310, 10, 150, 20);
        btnDeleteEmployees.setBounds(460, 10, 150, 20);
        generateReports.setBounds(610, 10, 150, 20);

        lUsername1Employee = new JLabel("Username employee 1");
        lPassword1Employee = new JLabel("Password employee 1");
        lId_Series1Employee = new JLabel("Series employee 1");
        lId_Number1Employee = new JLabel("Number employee 1");
        lPnc1Employee = new JLabel("Pnc employee 1");
        lAddress1Employee = new JLabel("Address employee 1");
        tfUsername1Employee = new JTextField();
        tfPassword1Employee = new JTextField();
        tfId_Series1Employee = new JTextField();
        tfId_Number1Employee = new JTextField();
        tfPnc1Employee = new JTextField();
        tfAddress1Employee = new JTextField();

        lUsername1Employee.setBounds(10, 30, 200, 10);
        lPassword1Employee.setBounds(10, 70, 200, 10);
        lId_Series1Employee.setBounds(10, 110, 200, 10);
        lId_Number1Employee.setBounds(10, 150, 200, 10);
        lPnc1Employee.setBounds(10, 190, 200, 10);
        lAddress1Employee.setBounds(10, 230, 200, 10);
        tfUsername1Employee.setBounds(10, 50, 150, 20);
        tfPassword1Employee.setBounds(10, 90, 150, 20);
        tfId_Series1Employee.setBounds(10, 130, 150, 20);
        tfId_Number1Employee.setBounds(10, 170, 150, 20);
        tfPnc1Employee.setBounds(10, 210, 150, 20);
        tfAddress1Employee.setBounds(10, 250, 150, 20);

        lUsername2Employee = new JLabel("Username employee 2");
        lPassword2Employee = new JLabel("Password employee 2");
        lId_Series2Employee = new JLabel("Series employee 2");
        lId_Number2Employee = new JLabel("Number employee 2");
        lPnc2Employee = new JLabel("Pnc employee 2");
        lAddress2Employee = new JLabel("Address employee 2");
        tfUsername2Employee = new JTextField();
        tfPassword2Employee = new JTextField();
        tfId_Series2Employee = new JTextField();
        tfId_Number2Employee = new JTextField();
        tfPnc2Employee = new JTextField();
        tfAddress2Employee = new JTextField();

        lUsername2Employee.setBounds(250, 30, 200, 10);
        lPassword2Employee.setBounds(250, 70, 200, 10);
        lId_Series2Employee.setBounds(250, 110, 200, 10);
        lId_Number2Employee.setBounds(250, 150, 200, 10);
        lPnc2Employee.setBounds(250, 190, 200, 10);
        lAddress2Employee.setBounds(250, 230, 200, 10);
        tfUsername2Employee.setBounds(250, 50, 150, 20);
        tfPassword2Employee.setBounds(250, 90, 150, 20);
        tfId_Series2Employee.setBounds(250, 130, 150, 20);
        tfId_Number2Employee.setBounds(250, 170, 150, 20);
        tfPnc2Employee.setBounds(250, 210, 150, 20);
        tfAddress2Employee.setBounds(250, 250, 150, 20);
    }

    public void addViewEmployeeButtonListener(ActionListener addViewEmployeeButtonListener) {
        btnViewEmployees.addActionListener(addViewEmployeeButtonListener);
    }

    public void addViewEmployeeTable (String[][] data, String[] columnNames)
    {
        allEmployeeTable = new JTable(data,columnNames);
        allEmployeeTable.setBounds( 10, 400, 1000, 100);
        add(allEmployeeTable);
    }

    public void addGenerateReportsButtonListener(ActionListener addGenerateReportsButtonListener) {
        generateReports.addActionListener(addGenerateReportsButtonListener);
    }

    public void addGenerateReportsTable (String[][] data, String[] columnNames)
    {
        reports = new JTable(data,columnNames);
        reports.setBounds( 10, 600, 1000, 100);
        add(reports);
    }

    public void addNewEmployeeButtonListener(ActionListener addNewEmployeeButtonListener) {
        btnAddEmployees.addActionListener(addNewEmployeeButtonListener);
    }

    public void addUpdateEmployeeButtonListener(ActionListener addUpdateEmployeeButtonListener) {
        btnUpdateEmployees.addActionListener(addUpdateEmployeeButtonListener);
    }

    public void addDeleteEmployeeButtonListener(ActionListener addDeleteEmployeeButtonListener) {
        btnDeleteEmployees.addActionListener(addDeleteEmployeeButtonListener);
    }

    public String getTfUsername1Employee() {
        return tfUsername1Employee.getText();
    }

    public String getTfPassword1Employee() {
        return tfPassword1Employee.getText();
    }

    public String getTfId_Series1Employee() {
        return tfId_Series1Employee.getText();
    }

    public String getTfId_Number1Employee() {
        return tfId_Number1Employee.getText();
    }

    public String getTfPnc1Employee() {
        return tfPnc1Employee.getText();
    }

    public String getTfAddress1Employee() {
        return tfAddress1Employee.getText();
    }

    public String getTfUsername2Employee() {
        return tfUsername2Employee.getText();
    }

    public String getTfPassword2Employee() {
        return tfPassword2Employee.getText();
    }

    public String getTfId_Series2Employee() {
        return tfId_Series2Employee.getText();
    }

    public String getTfId_Number2Employee() {
        return tfId_Number2Employee.getText();
    }

    public String getTfPnc2Employee() {
        return tfPnc2Employee.getText();
    }

    public String getTfAddress2Employee() {
        return tfAddress2Employee.getText();
    }
}

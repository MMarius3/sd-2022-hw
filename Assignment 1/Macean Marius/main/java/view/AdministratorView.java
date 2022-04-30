package view;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {

    private JLabel labelEmployeeId;
    private JLabel labelEmployeeName;
    private JTextField employeeId;
    private JTextField employeeName;
    private JButton btnAddEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnViewEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnGenerateReport;

    public AdministratorView() {
        setSize(700, 700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(labelEmployeeId);
        add(employeeId);
        add(labelEmployeeName);
        add(employeeName);
        add(btnAddEmployee);
        add(btnUpdateEmployee);
        add(btnViewEmployee);
        add(btnDeleteEmployee);
        add(btnGenerateReport);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        labelEmployeeId = new JLabel("Employee ID:");
        labelEmployeeName = new JLabel("Employee Name:");
        employeeId = new JTextField();
        employeeName = new JTextField();
        btnAddEmployee = new JButton("addEmployee");
        btnUpdateEmployee = new JButton("updateEmployee");
        btnViewEmployee = new JButton("viewEmployee");
        btnDeleteEmployee = new JButton("addEmployee");
        btnGenerateReport = new JButton("generateReport");
    }

    public String getEmployeeId() {
        return employeeId.getText();
    }

    public String getEmployeeName() {
        return employeeName.getText();
    }

    public void addAddEmployeeButtonListener(ActionListener addEmployeeListener) {
        btnAddEmployee.addActionListener(addEmployeeListener);
    }

    public void addUpdateEmployeeButtonListener(ActionListener updateEmployeeButtonListener) {
        btnUpdateEmployee.addActionListener(updateEmployeeButtonListener);
    }

    public void addViewEmployeeButtonListener(ActionListener viewEmployeeButtonListener) {
        btnViewEmployee.addActionListener(viewEmployeeButtonListener);
    }

    public void addDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener) {
        btnDeleteEmployee.addActionListener(deleteEmployeeButtonListener);
    }

    public void addGenerateReportButtonListener(ActionListener generateReportButtonListener) {
        btnGenerateReport.addActionListener(generateReportButtonListener);
    }
}

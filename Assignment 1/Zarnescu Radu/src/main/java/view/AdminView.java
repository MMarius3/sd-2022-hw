package view;

import controller.AdminController;
import model.Client;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    List<User> users;

    String[] colNames = {"ID", "Username", "Password"};
    JTable table;
    DefaultTableModel tableModel = new DefaultTableModel();
    JScrollPane pane;
    Object[] row = new Object[3];

    JTextField usernameField;
    JPasswordField passwordField;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JButton addButton;
    JButton updateButton;
    JButton deleteButton;
    JTextField dateFromField;
    JTextField dateToField;
    JLabel reportLabel;
    JButton generateReportButton;
    JLabel fromLabel;
    JLabel toLabel;
    JTextField employeeIDField;
    JLabel employeeIdLabel;

    public AdminView() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tableModel.setColumnIdentifiers(colNames);
        table.setModel(tableModel);
        pane.setBounds(0, 30, 800, 150);
        add(pane);

        usernameLabel.setBounds(1, 395, 70, 20);
        add(usernameLabel);
        usernameField.setBounds(70, 395, 150, 20);
        add(usernameField);
        passwordLabel.setBounds(1, 420, 70, 20);
        add(passwordLabel);
        passwordField.setBounds(70, 420, 150, 20);
        add(passwordField);

        addButton.setBounds(1, 450, 150, 30);
        add(addButton);
        updateButton.setBounds(1, 360, 120, 30);
        add(updateButton);
        deleteButton.setBounds(121, 360, 120, 30);
        add(deleteButton);

        reportLabel.setBounds(300, 360, 300, 20);
        add(reportLabel);
        fromLabel.setBounds(300, 385, 150, 20);
        add(fromLabel);
        dateFromField.setBounds(450, 385, 100, 20);
        add(dateFromField);
        toLabel.setBounds(300, 410, 150, 20);
        add(toLabel);
        dateToField.setBounds(450, 410, 100, 20);
        add(dateToField);
        employeeIdLabel.setBounds(300, 435, 150, 20);
        add(employeeIdLabel);
        employeeIDField.setBounds(450, 435, 100, 20);
        add(employeeIDField);
        generateReportButton.setBounds(300, 460, 100, 30);
        add(generateReportButton);

    }

    private void initializeFields() {
        this.table = new JTable();
        this.pane = new JScrollPane(table);
        this.usernameField = new JTextField();
        this.usernameLabel = new JLabel("Username:");
        this.passwordField = new JPasswordField();
        this.passwordLabel = new JLabel("Password:");
        this.addButton = new JButton("Add Employee");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");
        this.dateFromField = new JTextField();
        this.dateToField = new JTextField();
        this.reportLabel = new JLabel("Generate Employee Actions Report");
        this.generateReportButton = new JButton("Generate");
        this.fromLabel = new JLabel("From(YYYY-MM-DD):");
        this.toLabel = new JLabel("To(YYYY-MM-DD):");
        this.employeeIDField = new JTextField();
        this.employeeIdLabel = new JLabel("Employee ID:");
    }

    public String getSelectionUsername() {
        int row = table.getSelectedRow();
        return (String) table.getModel().getValueAt(row, 1);
    }

    public String getSelectionPassword() {
        int row = table.getSelectedRow();
        return (String) table.getModel().getValueAt(row, 2);
    }

    public Long getSelectionID() {
        int row = table.getSelectedRow();
        return (Long) table.getModel().getValueAt(row, 0);
    }

    public void populateTable() {
        tableModel.setRowCount(0);
        for (User u : users) {
            row[0] = u.getId();
            row[1] = u.getUsername();
            row[2] = u.getPassword();

            tableModel.addRow(row);
        }
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public String getFromDate() {
        return dateFromField.getText();
    }

    public String getToDate() {
        return dateToField.getText();
    }

    public String getEmployeeID() {
        return employeeIDField.getText();
    }

    public void addAddButtonActionListener(ActionListener actionListener) {
        this.addButton.addActionListener(actionListener);
    }

    public void setUsers(List<User> employees) {
        this.users = employees;
    }

    public void addDeleteActionListener(ActionListener actionListener) {
        this.deleteButton.addActionListener(actionListener);
    }

    public void addUpdateActionListener(ActionListener actionListener) {
        this.updateButton.addActionListener(actionListener);
    }

    public void addGenerateButtonActionListener(ActionListener actionListener) {
        this.generateReportButton.addActionListener(actionListener);
    }
}

package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.PasswordAuthentication;
import java.sql.Date;
import java.util.ArrayList;

public class AdministratorView extends JFrame {
    private JComboBox<User> employeeComboBox;

    private JLabel adminLabel;
    private JLabel dummy, dummy1;
    private JLabel employeeLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JTextField passwordField;

    private JButton addButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton generateReportsButton;

    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextField startDateField;
    private JTextField endDateField;

    public AdministratorView(){
        setSize(600,700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(0,2));

        add(adminLabel);
        add(dummy);
        add(employeeLabel);
        add(employeeComboBox);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);
        add(startDateLabel);
        add(startDateField);
        add(endDateLabel);
        add(endDateField);
        add(generateReportsButton);
        add(dummy1);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        adminLabel = new JLabel("Administrator Area");
        dummy = new JLabel("");
        dummy1 = new JLabel("");
        employeeLabel = new JLabel("Select Employee: ");
        usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        addButton = new JButton("Add");
        viewButton = new JButton("View");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        generateReportsButton = new JButton("Generate Reports");
        startDateLabel = new JLabel("Start Date:");
        endDateLabel = new JLabel("End Date:");
        startDateField = new JTextField();
        endDateField = new JTextField();
        
        employeeComboBox = new JComboBox();
    }
    
    public void addAddEmployeeButtonListener(ActionListener addEmployeeButtonListener){
        addButton.addActionListener(addEmployeeButtonListener);
    }

    public void addViewEmployeeButtonListener(ActionListener viewEmployeeButtonListener){
        viewButton.addActionListener(viewEmployeeButtonListener);
    }

    public void addDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener){
        deleteButton.addActionListener(deleteEmployeeButtonListener);
    }

    public void addUpdateEmployeeButtonListener(ActionListener updateEmployeeButtonListener){
        updateButton.addActionListener(updateEmployeeButtonListener);
    }

    public void addGenerateReportsEmployeeButtonListener(ActionListener generateReportsEmployeeButtonListener){
        generateReportsButton.addActionListener(generateReportsEmployeeButtonListener);
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }

    public void setUsername(String username){
        usernameField.setText(username);
    }

    public void setPassword(String password){
        passwordField.setText(password);
    }

    public String getStartDate(){
        return startDateField.getText();
    }

    public String getEndDate(){
        return endDateField.getText();
    }

    public User getSelectedEmployee(){
        return (User) employeeComboBox.getSelectedItem();
    }

    public void setEmployeeComboBox(ArrayList<User> users){
        employeeComboBox.removeAllItems();
        for(User u: users){
            employeeComboBox.addItem(u);
        }

        employeeComboBox.setVisible(true);
    }
}

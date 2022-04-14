package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfDate1;
    private JTextField tfDate2;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbDate1;
    private JLabel lbDate2;

    private JButton btnAddUser;
    private JButton btnUpdateUser;
    private JButton btnViewUser;
    private JButton btnDeleteUser;
    private JButton btnGenerateReports;
    private JButton btnLogout;

    private JTable tblEmployees;
    private JScrollPane scrollPaneEmployees;

    private JTable tblActivities;
    private JScrollPane scrollPaneActivities;

    public AdminView() throws HeadlessException{
        this.setTitle("Admin Page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 50, 1000, 700);
        this.getContentPane().setLayout(null);

        initializeFields();

        this.setVisible(false);
    }

    private void initializeFields(){
        scrollPaneEmployees = new JScrollPane(tblEmployees);
        scrollPaneEmployees.setBounds(440,240,460,150);
        getContentPane().add(scrollPaneEmployees);

        scrollPaneActivities = new JScrollPane(tblActivities);
        scrollPaneActivities.setBounds(440,400,460,150);
        getContentPane().add(scrollPaneActivities);

        lbUsername = new JLabel("Username: ");
        lbUsername.setBounds(50,60,150,30);
        getContentPane().add(lbUsername);
        tfUsername = new JTextField();
        tfUsername.setBounds(200,60,150,30);
        getContentPane().add(tfUsername);

        lbPassword = new JLabel("Password: ");
        lbPassword.setBounds(50,100,150,30);
        getContentPane().add(lbPassword);
        tfPassword = new JTextField();
        tfPassword.setBounds(200,100,150,30);
        getContentPane().add(tfPassword);

        btnAddUser = new JButton("Add employee");
        btnAddUser.setBounds(50,140,150,30);
        getContentPane().add(btnAddUser);

        btnUpdateUser = new JButton("Edit employee");
        btnUpdateUser.setBounds(210,140,150,30);
        getContentPane().add(btnUpdateUser);

        btnViewUser = new JButton("View employees");
        btnViewUser.setBounds(370,140,150,30);
        getContentPane().add(btnViewUser);

        btnDeleteUser = new JButton("Delete employee");
        btnDeleteUser.setBounds(530,140,150,30);
        getContentPane().add(btnDeleteUser);

        lbDate1 = new JLabel("From: ");
        lbDate1.setBounds(50,200,150,30);
        getContentPane().add(lbDate1);
        tfDate1 = new JTextField();
        tfDate1.setBounds(200,200,150,30);
        getContentPane().add(tfDate1);

        lbDate2 = new JLabel("To: ");
        lbDate2.setBounds(50,240,150,30);
        getContentPane().add(lbDate2);
        tfDate2 = new JTextField();
        tfDate2.setBounds(200,240,150,30);
        getContentPane().add(tfDate2);

        btnGenerateReports = new JButton("Generate report");
        btnGenerateReports.setBounds(50,280,150,30);
        getContentPane().add(btnGenerateReports);

        btnLogout = new JButton("Log Out");
        btnLogout.setBounds(20,20,100,30);
        getContentPane().add(btnLogout);
    }

    public String getUsername(){
        return tfUsername.getText();
    }

    public String getPassword(){
        return tfPassword.getText();
    }

    public String getDate1(){
        return tfDate1.getText();
    }

    public String getDate2(){
        return tfDate2.getText();
    }

    public void setAddUserButtonListener (ActionListener addUserButtonListener){
        btnAddUser.addActionListener(addUserButtonListener);
    }

    public void setUpdateUserButtonListener (ActionListener updateUserButtonListener){
        btnUpdateUser.addActionListener(updateUserButtonListener);
    }

    public void setViewUserButtonListener (ActionListener viewUserButtonListener){
        btnViewUser.addActionListener(viewUserButtonListener);
    }

    public void setDeleteUserButtonListener (ActionListener deleteUserButtonListener){
        btnDeleteUser.addActionListener(deleteUserButtonListener);
    }

    public void setGenerateReportsButtonListener (ActionListener generateReportsButtonListener){
        btnGenerateReports.addActionListener(generateReportsButtonListener);
    }

    public void setLogoutButtonListener (ActionListener logoutButtonListener){
        btnLogout.addActionListener(logoutButtonListener);
    }

    public Long getSelectedRowFromEmployees() {
        return Long.parseLong(tblEmployees.getValueAt(tblEmployees.getSelectedRow(), 0).toString());
    }

    public void loadEmployeesTable(JTable tbl) {
        this.tblEmployees = tbl;
        scrollPaneEmployees.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void loadActivitiesTable(JTable tbl) {
        this.tblActivities = tbl;
        scrollPaneActivities.setViewportView(tbl);
        revalidate();
        repaint();
    }
}

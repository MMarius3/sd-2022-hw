package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    private JTextField tfUsername;private JTextField tfUsername2;
    private JTextField tfPassword;private JTextField tfPassword2;
    private JTextField tfIdUser;private JTextField tfIdUser2;
    private JTextField tfStartReportPeriod;private JTextField tfStartReportPeriod2;
    private  JTextField tfEndReportPeriod;private  JTextField tfEndReportPeriod2;
    private JButton btnUpdateUser;
    private JButton btnViewUser;
    private JButton btnDeleteUser;
    private JButton btnCreateUser;
    private JButton btnReports;
    public AdminView(){
        setSize(1000,720);
        setLocationRelativeTo(null);
        initializeFields();

        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfIdUser2);add(tfIdUser);
        add(tfUsername2);add(tfUsername);
        add(tfPassword2);add(tfPassword);
        add(btnCreateUser);
        add(btnDeleteUser);
        add(btnViewUser);
        add(btnUpdateUser);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields(){
        tfUsername2 = new JTextField("Employee Name");
        tfUsername  = new JTextField();
        tfPassword2 = new JTextField("Employee Password");
        tfPassword  = new JTextField();
        tfIdUser2 = new JTextField("Employee ID:");
        tfIdUser = new JTextField();
        btnCreateUser = new JButton("Create Employee");
        btnViewUser = new JButton("View Employee");
        btnDeleteUser = new JButton("Delete Employee");
        btnUpdateUser = new JButton("Update Employee");
        btnReports = new JButton("Generate Report");
    }
    public String getEmpId(){return tfIdUser.getText();};
    public String getEmpUsername(){return tfUsername.getText();};
    public String getEmpPassword(){return tfPassword.getText();};
    public void setEmpPassword(String password){tfPassword.setText(password);}
    public void setEmpUsername(String username){tfUsername.setText(username);}
    public void setEmpid(String id){tfUsername.setText(id);};
    public void addUpdateEmployeeButonListener(ActionListener updateButonListener){
        btnUpdateUser.addActionListener(updateButonListener);
    }
    public void addDeleteEmployeeButonListener(ActionListener deleteButonListener){
        btnDeleteUser.addActionListener(deleteButonListener);
    }
    public void addCreateEmployeeButonListener(ActionListener createButonListener){
        btnCreateUser.addActionListener(createButonListener);
    }
    public void addViewEmployeeButonListener(ActionListener viewButonListener){
        btnViewUser.addActionListener(viewButonListener);
    }
}
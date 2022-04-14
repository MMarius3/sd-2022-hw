package view;

import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame{
    String[] columnNames = {"Id","Username","Password"};
    Object[][] initData = {{" "," "," "}};
    private DefaultTableModel model = new DefaultTableModel();
    private JTable employeeTable = new JTable();
    private JScrollPane employeeeScrollPane;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnDelete;
    private JTextField username;
    private JTextField password;
    private JButton showReport;
    private JTextField dateTF;

    public AdminView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(employeeeScrollPane);
        add(btnAdd);
        add(btnUpdate);
        add(btnView);
        add(btnDelete);
        add(username);
        add(password);
        add(showReport);
        add(dateTF);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields() {
        model.setDataVector(initData,columnNames);
        this.employeeTable.setModel(model);
        this.employeeeScrollPane = new JScrollPane(employeeTable);
        btnAdd = new JButton("Create employee");
        btnUpdate = new JButton("Update employee");
        btnView = new JButton("View employees");
        btnDelete = new JButton("Delete employee");
        showReport = new JButton("Show reports");
        username = new JTextField();
        password = new JTextField();
        dateTF = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setAddButtonListener(ActionListener addButtonListener){

        btnAdd.addActionListener(addButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener){
        btnView.addActionListener(viewButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener){
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setDeleteButtonListener(ActionListener accountsButtonListener){
        btnDelete.addActionListener(accountsButtonListener);
    }

    public void setShowButtonListener(ActionListener showButtonListener){
        showReport.addActionListener(showButtonListener);
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getDate(){
        return dateTF.getText();
    }

    public void resetTextFields(){
        username.setText("");
        password.setText("");
        dateTF.setText("");
    }
    public void viewAccounts(ArrayList<User> users){
        for(User user : users){
            model.addRow(new Object[]{user.getId(),user.getUsername(),user.getPassword()});
        }
        employeeTable.setModel(model);
        employeeTable.setVisible(true);
    }
    public void resetTable(){
        int rows = model.getRowCount();
        for(int i = rows -1;i>=0;i--){
            model.removeRow(i);
        }
    }
    public long getIdForSelectedAccount() {
        if(employeeTable.getSelectedRow()==-1 || employeeTable.getSelectedColumn()==-1){
            return -1;
        }
        return (long)employeeTable.getValueAt(employeeTable.getSelectedRow(),0);
    }
    public String getUsernamerForSelectedAccount(){
        if(employeeTable.getSelectedRow()==-1 || employeeTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) employeeTable.getValueAt(employeeTable.getSelectedRow(),1);
    }
    public String getPasswordForSelectedAccount(){
        if(employeeTable.getSelectedRow()==-1 || employeeTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) employeeTable.getValueAt(employeeTable.getSelectedRow(),2);
    }

    public ArrayList<String> getInfoForSelectClient(){
        ArrayList<String> info = new ArrayList<>();
        if(getUsername().isEmpty()){
            info.add(String.valueOf(getUsernamerForSelectedAccount()));
        }else {
            info.add(String.valueOf(getUsername()));
        }

        if(getPassword().isEmpty()){
            info.add(String.valueOf(getPasswordForSelectedAccount()));
        }else {
            info.add(String.valueOf(getPassword()));
        }
        return info;
    }
    public void setVisible(){
        this.setVisible(true);
    }
}

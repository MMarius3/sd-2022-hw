package view;

import model.Action;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminView extends JFrame {

    private JButton createEmployee;
    private JButton viewEmployees;
    private JButton updateEmployee;
    private JButton deleteEmployee;

    private JLabel employeeName;
    private JTextField tfEmployeeName;
    private JLabel employeePassword;
    private JTextField tfEmployeePassword;

    private JButton generateReport;
    private JLabel year1;
    private JLabel year2;
    private JTextField tfYear1;
    private JTextField tfYear2;
    private JLabel month1;
    private JLabel month2;
    private JTextField tfMonth1;
    private JTextField tfMonth2;
    private JLabel day1;
    private JLabel day2;
    private JTextField tfDay1;
    private JTextField tfDay2;

    private JTable allEmployees;
    private JTable allActions;

    public AdminView(){
        setSize(800,800);
        setLocationRelativeTo(null);

        initializeFields();
        add(createEmployee);
        add(updateEmployee);
        add(viewEmployees);
        add(deleteEmployee);

        add(employeeName);
        add(tfEmployeeName);
        add(employeePassword);
        add(tfEmployeePassword);

        add(generateReport);
        add(year1);
        add(year2);
        add(tfYear1);
        add(tfYear2);
        add(month1);
        add(tfMonth1);
        add(day1);
        add(tfDay1);
        add(month2);
        add(tfMonth2);
        add(day2);
        add(tfDay2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        setLayout(null);
        createEmployee = new JButton("Add new employee");
        createEmployee.setBounds(0,0,200,30);
        viewEmployees = new JButton("View Employees");
        viewEmployees.setBounds(0,30,200,30);
        updateEmployee = new JButton("Update Employee");
        updateEmployee.setBounds(0,60,200,30);
        deleteEmployee = new JButton("Delete employee");
        deleteEmployee.setBounds(0,90,200,30);

        employeeName = new JLabel("Employee username");
        employeeName.setBounds(210,0,150,20);
        tfEmployeeName = new JTextField();
        tfEmployeeName.setBounds(360,0,100,20);
        employeePassword = new JLabel("Employee password");
        employeePassword.setBounds(210,20,150,20);
        tfEmployeePassword = new JTextField();
        tfEmployeePassword.setBounds(360,20,100,20);

        generateReport = new JButton("Generate Report");
        generateReport.setBounds(210,50,150,30);
        year1 = new JLabel("Year 1");
        year1.setBounds(210,90,50,20);
        tfYear1 = new JTextField();
        tfYear1.setBounds(260,90,50,20);
        year2 = new JLabel("Year 2");
        year2.setBounds(210,110,50,20);
        tfYear2 = new JTextField();
        tfYear2.setBounds(260,110,50,20);
        month1 = new JLabel("Month 1");
        month1.setBounds(320,90,50,20);
        tfMonth1 = new JTextField();
        tfMonth1.setBounds(370,90,50,20);
        day1 = new JLabel("Day 1");
        day1.setBounds(430,90,50,20);
        tfDay1 = new JTextField();
        tfDay1.setBounds(480,90,50,20);
        month2 = new JLabel("Month 2");
        month2.setBounds(320,110,50,20);
        tfMonth2 = new JTextField();
        tfMonth2.setBounds(370,110,50,20);
        day2 = new JLabel("Day 2");
        day2.setBounds(430,110,50,20);
        tfDay2 = new JTextField();
        tfDay2.setBounds(480,110,50,20);


        allEmployees = new JTable();
        allActions = new JTable();

    }

    public String getEmployeeName(){return tfEmployeeName.getText();}
    public String getEmployeePassword(){return tfEmployeePassword.getText();}
    public int getEmployeeTableSelection(){return allEmployees.getSelectedRow();}
    public Object getEmployeeIdFromTable(int row){return allEmployees.getValueAt(row,0);}
    public int getYear1(){return Integer.parseInt(tfYear1.getText());}
    public int getMonth1(){return Integer.parseInt(tfMonth1.getText());}
    public int getDay1(){return Integer.parseInt(tfDay1.getText());}
    public int getYear2(){return Integer.parseInt(tfYear2.getText());}
    public int getMonth2(){return Integer.parseInt(tfMonth2.getText());}
    public int getDay2(){return Integer.parseInt(tfDay2.getText());}

    public void setViewEmployeesTable(List<User> users){
        remove(allEmployees);
        String columns[]={"id","username","password"};
        DefaultTableModel tableModel = new DefaultTableModel(columns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(columns);
        for(User user:users){
            Object data[] = {user.getId(), user.getUsername(),user.getPassword()};
            tableModel.addRow(data);
        }
        allEmployees = new JTable(tableModel);
        add(allEmployees);
        allEmployees.setBounds(100,300,410,200);
        allEmployees.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        allEmployees.getColumnModel().getColumn(1).setPreferredWidth(170);
        allEmployees.setVisible(true);
    }

    public void setViewActionsTable(List<Action> actions){
        remove(allActions);
        String columns[]={"id","action_name","date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(columns);
        for(Action action:actions){
            Object data[] = {action.getId(), action.getRightName(),action.getActionDate()};
            tableModel.addRow(data);
        }
        allActions = new JTable(tableModel);
        add(allActions);
        allActions.setBounds(100,520,410,200);
        allActions.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        allActions.getColumnModel().getColumn(1).setPreferredWidth(170);
        allActions.setVisible(true);
    }

    public void addNewEmployeeButtonListener(ActionListener addNewEmployeeButtonListener){
        createEmployee.addActionListener(addNewEmployeeButtonListener);
    }

    public void viewEmployeesButtonListener(ActionListener viewEmployeesButtonListener){
        viewEmployees.addActionListener(viewEmployeesButtonListener);
    }

    public void updateEmployeeButtonListener(ActionListener updateEmployeeButtonListener){
        updateEmployee.addActionListener(updateEmployeeButtonListener);
    }

    public void deleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener){
        deleteEmployee.addActionListener(deleteEmployeeButtonListener);
    }

    public void generateReportButtonListener(ActionListener generateReportButtonListener){
        generateReport.addActionListener(generateReportButtonListener);
    }
}

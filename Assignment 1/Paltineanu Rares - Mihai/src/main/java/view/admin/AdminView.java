package view.admin;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private Container container;

    private JScrollPane jScrollPane;
    private JTable employeesTable;

    private JLabel userIdLabel;
    private JTextField userIdField;

    private JLabel fromLabel;

    private JLabel fromYearLabel;
    private JLabel fromMonthLabel;
    private JLabel fromDayLabel;

    private JTextField fromYearField;
    private JTextField fromMonthField;
    private JTextField fromDayField;

    private JLabel toLabel;

    private JLabel toYearLabel;
    private JLabel toMonthLabel;
    private JLabel toDayLabel;

    private JTextField toYearField;
    private JTextField toMonthField;
    private JTextField toDayField;

    private JButton viewEmployeesButton;
    private JButton updateEmployeeButton;
    private JButton deleteEmployeeButton;
    private JButton addEmployeeButton;

    private JButton generateReportButton;

    public AdminView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(500, 650);
        setResizable(false);
        setVisible(false);
        setTitle("Admin view");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        employeesTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        employeesTable.getTableHeader().setReorderingAllowed(false);

        jScrollPane = new JScrollPane(employeesTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        userIdLabel = new JLabel("User id");
        userIdField = new JTextField();

        fromLabel = new JLabel("From");

        fromYearLabel = new JLabel("Year");
        fromMonthLabel = new JLabel("Month");
        fromDayLabel = new JLabel("Day");

        fromYearField = new JTextField();
        fromMonthField = new JTextField();
        fromDayField = new JTextField();

        toLabel = new JLabel("To");

        toYearLabel = new JLabel("Year");
        toMonthLabel = new JLabel("Month");
        toDayLabel = new JLabel("Day");

        toYearField = new JTextField();
        toMonthField = new JTextField();
        toDayField = new JTextField();

        viewEmployeesButton = new JButton("View");
        updateEmployeeButton = new JButton("Update");
        deleteEmployeeButton = new JButton("Delete");
        addEmployeeButton = new JButton("Add");

        generateReportButton = new JButton("Generate report");
    }

    private void setBounds() {
        jScrollPane.setBounds(0, 0, 400, 200);

        fromLabel.setBounds(230,250, 80, 20);

        fromYearLabel.setBounds(160, 290, 30, 20);
        fromMonthLabel.setBounds(160, 330, 50, 20);
        fromDayLabel.setBounds(160, 370, 30, 20);

        fromYearField.setBounds(210, 290, 60, 30);
        fromMonthField.setBounds(210, 330, 60, 30);
        fromDayField.setBounds(210, 370, 60, 30);

        toLabel.setBounds(370,250, 80, 20);

        toYearLabel.setBounds(300, 290, 30, 20);
        toMonthLabel.setBounds(300, 330, 50, 20);
        toDayLabel.setBounds(300, 370, 30, 20);

        toYearField.setBounds(350, 290, 60, 30);
        toMonthField.setBounds(350, 330, 60, 30);
        toDayField.setBounds(350, 370, 60, 30);

        userIdLabel.setBounds(160, 410, 70, 20);
        userIdField.setBounds(210, 410, 40, 30);

        addEmployeeButton.setBounds(30, 250, 120, 30);
        updateEmployeeButton.setBounds(30, 300, 120, 30);
        deleteEmployeeButton.setBounds(30, 350, 120, 30);
        viewEmployeesButton.setBounds(30, 400, 120, 30);
        generateReportButton.setBounds(200, 450, 200, 30);
    }

    private void addComponentsToContainer() {
        container.add(jScrollPane);

        container.add(fromLabel);

        container.add(fromYearLabel);
        container.add(fromMonthLabel);
        container.add(fromDayLabel);

        container.add(fromYearField);
        container.add(fromMonthField);
        container.add(fromDayField);

        container.add(toLabel);

        container.add(toYearLabel);
        container.add(toMonthLabel);
        container.add(toDayLabel);

        container.add(toYearField);
        container.add(toMonthField);
        container.add(toDayField);

        container.add(userIdLabel);
        container.add(userIdField);

        container.add(addEmployeeButton);
        container.add(updateEmployeeButton);
        container.add(deleteEmployeeButton);
        container.add(viewEmployeesButton);
        container.add(generateReportButton);
    }

    public void setViewEmployeesButtonListener(ActionListener actionListener) {
        this.viewEmployeesButton.addActionListener(actionListener);
    }

    public void setAddEmployeeButtonListener(ActionListener actionListener) {
        this.addEmployeeButton.addActionListener(actionListener);
    }

    public void setDeleteEmployeeButtonListener(ActionListener actionListener) {
        this.deleteEmployeeButton.addActionListener(actionListener);
    }

    public void setUpdateEmployeeButtonListener(ActionListener actionListener) {
        this.updateEmployeeButton.addActionListener(actionListener);
    }

    public void setGenerateReportButtonListener(ActionListener actionListener) {
        this.generateReportButton.addActionListener(actionListener);
    }

    public int[] getEmployeeTableSelectedRows() {
        return this.employeesTable.getSelectedRows();
    }

    public DefaultTableModel getEmployeeTableDefaultTableModel() {
        return (DefaultTableModel) this.employeesTable.getModel();
    }

    public void clickViewEmployeesButton() {
        viewEmployeesButton.doClick();
    }

    public String getValueFromEmployeeTableCell(int row, int column) {
        return (String) employeesTable.getValueAt(row, column);
    }

    public String getUserIdField() {
        return userIdField.getText();
    }

    public String getFromYearField() {
        return fromYearField.getText();
    }

    public String getFromMonthField() {
        return fromMonthField.getText();
    }

    public String getFromDayField() {
        return fromDayField.getText();
    }

    public String getToYearField() {
        return toYearField.getText();
    }

    public String getToMonthField() {
        return toMonthField.getText();
    }

    public String getToDayField() {
        return toDayField.getText();
    }
}

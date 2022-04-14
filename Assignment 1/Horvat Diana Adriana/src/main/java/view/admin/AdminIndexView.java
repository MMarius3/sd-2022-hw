package view.admin;

import model.Account;
import model.Activity;
import model.User;
import view.RowFilterUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminIndexView extends JFrame {
    private JButton btnAddEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnReport;
    private JTextField employee_id;

    public AdminIndexView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(btnAddEmployee);
        add(btnUpdateEmployee);
        add(employee_id);
        add(btnReport);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnAddEmployee = new JButton("Add employee");
        btnUpdateEmployee = new JButton("View, update and delete employee");
        employee_id = new JTextField("Employee id");
        btnReport = new JButton("Generate report");
    }

    public void addEmployeeViewBtnListener(ActionListener addEmployeeViewBtnListener) {
        btnAddEmployee.addActionListener(addEmployeeViewBtnListener);
    }

    public void updateEmployeeViewBtnListener(ActionListener updateEmployeeViewBtnListener) {
        btnUpdateEmployee.addActionListener(updateEmployeeViewBtnListener);
    }

    public void generateReportBtnListener(ActionListener generateReportBtnListener) {
        btnReport.addActionListener(generateReportBtnListener);
    }

    public String getEmployee_id() {
        return employee_id.getText();
    }

    public void displayActivities(List<Activity> activities){
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel(activities);
        JTable table = new JTable(tableModel);

        JTextField filterField = RowFilterUtil.createRowFilter(table);
        JPanel jp = new JPanel();
        jp.add(filterField);
        frame.add(jp, BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("List of activities");
        frame.setSize(new Dimension(350, 300));
        return frame;
    }

    private TableModel createTableModel(List<Activity> activities) {
        Vector<String> columns = new Vector<>(Arrays.asList("Activity", "Date and time"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (int i = 0; i < activities.size(); i++) {
            Vector<Object> v = new Vector<>();
            Activity activity = activities.get(i);
            v.add(activity.getName());
            v.add(activity.getCreated_at());
            rows.add(v);
        }

        DefaultTableModel dtm = new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Integer.class : String.class;
            }

        };
        return dtm;
    }

}

package view.admin;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class AdminView extends JFrame {
    private Container container;

    private JScrollPane jScrollPane;
    private JTable employeesTable;

    private JButton viewEmployeesButton;
    private JButton updateEmployeeButton;
    private JButton deleteEmployeeButton;
    private JButton addEmployeeButton;

    public AdminView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(400, 600);
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

        viewEmployeesButton = new JButton("View");
        updateEmployeeButton = new JButton("Update");
        deleteEmployeeButton = new JButton("Delete");
        addEmployeeButton = new JButton("Add");
    }

    private void setBounds() {
        jScrollPane.setBounds(0, 0, 400, 200);

        addEmployeeButton.setBounds(30, 250, 120, 30);
        updateEmployeeButton.setBounds(30, 300, 120, 30);
        deleteEmployeeButton.setBounds(30, 350, 120, 30);
        viewEmployeesButton.setBounds(30, 400, 120, 30);
    }

    private void addComponentsToContainer() {
        container.add(jScrollPane);
        container.add(addEmployeeButton);
        container.add(updateEmployeeButton);
        container.add(deleteEmployeeButton);
        container.add(viewEmployeesButton);
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
}

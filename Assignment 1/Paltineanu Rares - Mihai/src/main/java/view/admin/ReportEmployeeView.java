package view.admin;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportEmployeeView extends JFrame {

    private Container container;
    private JScrollPane jScrollPane;
    private JTable reportsTable;

    public ReportEmployeeView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }


    private void initializeFrame() {
        setSize(300, 300);
        setResizable(false);
        setVisible(true);
        setTitle("Report");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        reportsTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        reportsTable.getTableHeader().setReorderingAllowed(false);

        jScrollPane = new JScrollPane(reportsTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        DefaultTableModel defaultTableModel = (DefaultTableModel) reportsTable.getModel();
        defaultTableModel.setColumnIdentifiers(new String[]{"Id", "User id", "Action", "Date"});
    }

    private void setBounds() {
        jScrollPane.setBounds(0, 0, 280, 200);
    }

    private void addComponentsToContainer() {
        container.add(jScrollPane);
    }

    public DefaultTableModel getReportsTableDefaultTableModel() {
        return (DefaultTableModel) reportsTable.getModel();
    }
}

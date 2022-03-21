package view.employee;

import model.Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DisplayAccountView {
    private List<Account> accounts;


    public void initializeTable(List<Account> accounts) {
        this.accounts = accounts;
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel();
        JTable table = new JTable(tableModel);

        JPanel jp = new JPanel();
        frame.add(jp, BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("View Accounts");
        frame.setSize(new Dimension(500, 500));
        return frame;
    }

    private TableModel createTableModel() {
        Vector<String> columns = new Vector<>(Arrays.asList("Type", "Amount", "Date of Creation"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (Account value : accounts) {
            Vector<Object> v = new Vector<>();
            v.add(value.getType());
            v.add(value.getAmount());
            v.add(value.getDateOfCreation());
            rows.add(v);
        }

        return new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Integer.class : String.class;
            }

        };
    }
}

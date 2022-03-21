package view.admin;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DisplayUserView extends JFrame {
    private List<User> users;

    public void initializeTable(List<User> users){
        this.users = users;
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel();
        JTable table = new JTable(tableModel);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("View Users");
        frame.setSize(new Dimension(500, 500));
        return frame;
    }

    private TableModel createTableModel() {
        Vector<String> columns = new Vector<>(Arrays.asList("Username", "Password"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (User value : users) {
            Vector<Object> v = new Vector<>();
            v.add(value.getUsername());
            v.add(value.getPassword());
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

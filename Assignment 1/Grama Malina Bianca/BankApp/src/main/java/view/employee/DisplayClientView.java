package view.employee;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class DisplayClientView extends JFrame {

    private List<Client> clients;

    public DisplayClientView() {
    }

    public void fetchClients(List<Client> clients) {
        this.clients = clients;
    }

    public void initializeTable(List<Client> clients){
        this.clients = clients;
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel();
        JTable table = new JTable(tableModel);

        // JTextField filterField = RowFilterUtil.createRowFilter(table);
        JPanel jp = new JPanel();
        // jp.add(filterField);
        frame.add(jp, BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("View Clients");
        frame.setSize(new Dimension(500, 500));
        return frame;
    }

    private TableModel createTableModel() {
        Vector<String> columns = new Vector<>(Arrays.asList("Name", "IC Number", "CNP", "Address"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (Client value : clients) {
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getIdCardNo());
            v.add(value.getCNP());
            v.add(value.getAddress());
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

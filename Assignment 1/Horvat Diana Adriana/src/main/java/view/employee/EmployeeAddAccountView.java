package view.employee;
import model.Client;
import view.RowFilterUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeAddAccountView extends JFrame {

    private JButton btnAddAccount;
    private JTextField tfClient_id;
    private JTextField tfType;
    private JTextField tfAmount;

    public EmployeeAddAccountView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfClient_id);
        add(tfType);
        add(tfAmount);
        add(btnAddAccount);
    }

    private void initializeFields() {
        btnAddAccount = new JButton("Add Account");
        tfClient_id = new JTextField("Client_id");
        tfType = new JTextField("Type");
        tfAmount = new JTextField("Amount");
    }

    public String getTfClient_id() {
        return tfClient_id.getText();
    }

    public String getTfType() {
        return tfType.getText();
    }

    public String getTfAmount() {
        return tfAmount.getText();
    }

    public void addAccountBtnListener(ActionListener addAccountBtnListener) {
        btnAddAccount.addActionListener(addAccountBtnListener);
    }

    public void displayClients(List<Client> clients){
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel(clients);
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
        JFrame frame = new JFrame("List of clients");
        frame.setSize(new Dimension(600, 450));
        return frame;
    }

    private TableModel createTableModel(List<Client> clients) {
        Vector<String> columns = new Vector<>(Arrays.asList("Id","Name", "ID card nr", "PNG", "Address", "Email"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (int i = 0; i < clients.size(); i++) {
            Vector<Object> v = new Vector<>();
            Client client = clients.get(i);
            v.add(client.getId());
            v.add(client.getName());
            v.add(client.getIdCardNr());
            v.add(client.getPNC());
            v.add(client.getAddress());
            v.add(client.getEmail());
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

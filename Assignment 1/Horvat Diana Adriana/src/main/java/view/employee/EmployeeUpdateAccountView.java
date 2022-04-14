package view.employee;

import model.Account;
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

public class EmployeeUpdateAccountView extends JFrame {

    private JTextField tfAccountId;
    private JTextField tfType;
    private JTextField tfAmount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnSearch;

    public EmployeeUpdateAccountView(){
        setSize(400, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfAccountId);
        add(btnSearch);
        add(tfType);
        add(tfAmount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
    }

    private void initializeFields() {
        tfAccountId = new JTextField("Account id");
        tfType = new JTextField("type");
        tfAmount = new JTextField("amount");
        btnSearch = new JButton("Search account");
        btnUpdateAccount = new JButton("Update account");
        btnDeleteAccount = new JButton("Delete account");
    }

    public void setAccountData(String type, Long amount){
        tfType.setText(type);
        tfAmount.setText(amount.toString());
    }

    public void searchAccountBtnListener(ActionListener searchAccountBtnListener) {
        btnSearch.addActionListener(searchAccountBtnListener);
    }

    public void updateAccountBtnListener(ActionListener updateAccountBtnListener) {
        btnUpdateAccount.addActionListener(updateAccountBtnListener);
    }

    public void deleteAccountBtnListener(ActionListener deleteAccountBtnListener) {
        btnDeleteAccount.addActionListener(deleteAccountBtnListener);
    }

    public void displayAccounts(List<Account> accounts){
        JFrame frame = createFrame();
        TableModel tableModel = createTableModel(accounts);
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
        JFrame frame = new JFrame("List of accounts");
        frame.setSize(new Dimension(350, 300));
        return frame;
    }

    private TableModel createTableModel(List<Account> accounts) {
        Vector<String> columns = new Vector<>(Arrays.asList("Id","Client Id", "Type", "Amount", "Date of creation"));
        Vector<Vector<Object>> rows = new Vector<>();

        for (int i = 0; i < accounts.size(); i++) {
            Vector<Object> v = new Vector<>();
            Account account = accounts.get(i);
            v.add(account.getId());
            v.add(account.getClient_id());
            v.add(account.getType());
            v.add(account.getAmount());
            v.add(account.getCreated_at().toLocalDateTime());
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


    public String getTfType() {
        return tfType.getText();
    }

    public void setTfType(String tfType) {
        this.tfType.setText(tfType);
    }

    public String getTfAmount() {
        return tfAmount.getText();
    }

    public void setTfAmount(Long tfAmount) {
        this.tfAmount.setText(tfAmount.toString());
    }

    public String getTfAccountId() {
        return tfAccountId.getText();
    }
}

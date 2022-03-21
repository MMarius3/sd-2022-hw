package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.View.Y_AXIS;

public class BillView extends JFrame {
    private JLabel lblClientIdentifier;
    private JLabel lblAccount;
    private JLabel lblSum;;
    private JTable tblClients;
    private JTable tblClientsAccounts;
    private JTextField tfSum;
    private JButton btnPay;
    private JScrollPane scrollPaneClients;
    private JScrollPane scrollPaneAccounts;
    private JButton btnViewClientAccounts;
    private JButton btnBack;

    public BillView() throws HeadlessException{
        this.setTitle("Bill page");
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientIdentifier);
        add(scrollPaneClients);
        add(btnViewClientAccounts);
        add(lblAccount);
        add(scrollPaneAccounts);
        add(lblSum);
        add(tfSum);
        add(btnPay);
        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblAccount = new JLabel("Choose the account");
        lblClientIdentifier = new JLabel("Choose the client");
        lblSum = new JLabel("Enter sum to pay");
        tfSum = new JTextField();
        tblClients = new JTable();
        tblClientsAccounts = new JTable();
        scrollPaneAccounts = new JScrollPane(tblClientsAccounts);
        scrollPaneClients = new JScrollPane(tblClients);
        btnViewClientAccounts = new JButton("View Client Accounts");
        btnPay = new JButton("Pay Bill");
        btnBack = new JButton("Back");
    }

    public String getSum() {
        return tfSum.getText();
    }

    public java.util.List<Object> getSelectedClient() {
        java.util.List<Object> selection = new ArrayList<>();
        int row = tblClients.getSelectedRow();
        int columnCount = tblClients.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            selection.add(tblClients.getValueAt(row, i));
        }
        return selection;
    }

    public java.util.List<Object> getSelectedAccount() {
        List<Object> selection = new ArrayList<>();
        int row = tblClientsAccounts.getSelectedRow();
        int columnCount = tblClientsAccounts.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            selection.add(tblClientsAccounts.getValueAt(row, i));
        }
        return selection;
    }

    public void setPayButtonListener(ActionListener payButtonListener) {
        btnPay.addActionListener(payButtonListener);
    }

    public void setViewClientAccountsButtonListener(ActionListener viewClientAccountsButtonListener) {
        btnViewClientAccounts.addActionListener(viewClientAccountsButtonListener);
    }

    public void setBackButtonListener (ActionListener backButtonListener){
        btnBack.addActionListener(backButtonListener);
    }

    public void loadClientsTable(JTable tbl) {
        this.tblClients = tbl;
        scrollPaneClients.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void loadAccountsTable(JTable tbl) {
        this.tblClientsAccounts = tbl;
        scrollPaneAccounts.setViewportView(tbl);
        revalidate();
        repaint();
    }
}

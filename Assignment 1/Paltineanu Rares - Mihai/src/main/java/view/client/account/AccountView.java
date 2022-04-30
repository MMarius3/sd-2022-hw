package view.client.account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AccountView extends JFrame {

    private Container container;

    private JScrollPane jScrollPane;
    private JTable accountTable;

    private JButton btnAddClientAccount;
    private JButton btnUpdateClientAccount;
    private JButton btnDeleteClientAccount;
    private JButton btnViewClientAccount;

    private JButton btnTransferMoneyButton;
    private JButton processBill;

    public AccountView() {
        initializeFields();
        initializeFrame();
        setBounds();
        addComponentsToContainer();
    }

    private void initializeFields() {
        container = getContentPane();

        accountTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jScrollPane = new JScrollPane(accountTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        btnAddClientAccount = new JButton("Add account");
        btnUpdateClientAccount = new JButton("Update account");
        btnDeleteClientAccount = new JButton("Delete account");
        btnViewClientAccount = new JButton("View account");

        btnTransferMoneyButton = new JButton("Transfer money");
        processBill = new JButton("Process bill");
    }

    private void setBounds() {
        jScrollPane.setBounds(0, 0, 650, 200);

        btnAddClientAccount.setBounds(50, 250, 150, 30);
        btnUpdateClientAccount.setBounds(50, 300, 150, 30);
        btnDeleteClientAccount.setBounds(50, 350, 150, 30);
        btnViewClientAccount.setBounds(50, 400, 150, 30);

        btnTransferMoneyButton.setBounds(250, 250,150, 30);
        processBill.setBounds(250, 280, 150, 30);
    }

    private void addComponentsToContainer() {
        container.add(btnAddClientAccount);
        container.add(btnUpdateClientAccount);
        container.add(btnDeleteClientAccount);
        container.add(btnViewClientAccount);
        container.add(btnTransferMoneyButton);
        container.add(processBill);
        container.add(jScrollPane);
    }

    private void initializeFrame() {
        setSize(660, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(false);
        setTitle("Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setAddClientAccountListener(ActionListener actionListener) {
        this.btnAddClientAccount.addActionListener(actionListener);
    }

    public void setUpdateClientAccountListener(ActionListener actionListener) {
        this.btnUpdateClientAccount.addActionListener(actionListener);
    }

    public void setDeleteClientAccountListener(ActionListener actionListener) {
        this.btnDeleteClientAccount.addActionListener(actionListener);
    }

    public void setViewClientAccountListener(ActionListener actionListener) {
        this.btnViewClientAccount.addActionListener(actionListener);
    }

    public void setTransferAccountListener(ActionListener actionListener) {
        this.btnTransferMoneyButton.addActionListener(actionListener);
    }

    public void setProcessBillListener(ActionListener actionListener) {
        this.processBill.addActionListener(actionListener);
    }

    public void clickViewButton() {
        btnViewClientAccount.doClick();
    }

    public String getValueFromAccountTableCell(int row, int column) {
        return (String) accountTable.getValueAt(row, column);
    }

    public DefaultTableModel getAccountTableDefaultTableModel() {
        return (DefaultTableModel) this.accountTable.getModel();
    }

    public int[] getAccountTableSelectedRows() {
        return this.accountTable.getSelectedRows();
    }

    public void setClientsTableReordering(boolean flag) {
        accountTable.getTableHeader().setReorderingAllowed(flag);
    }
}

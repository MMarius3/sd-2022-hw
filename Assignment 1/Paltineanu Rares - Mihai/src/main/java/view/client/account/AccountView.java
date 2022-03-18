package view.client.account;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class AccountView extends JFrame {

    private Container container;

    private JScrollPane jScrollPane;
    private JTable accountInformationTable;

    private JButton btnAddClientAccount;
    private JButton btnUpdateClientAccount;
    private JButton btnDeleteClientAccount;
    private JButton btnViewClientAccount;

    private JButton btnTransferMoneyButton;

    public AccountView() {
        initializeFields();
        initializeFrame();
        setLocationAndBounds();
        addComponentsToContainer();
    }

    private void initializeFields() {
        container = getContentPane();

        accountInformationTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jScrollPane = new JScrollPane(accountInformationTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        btnAddClientAccount = new JButton("Add account");
        btnUpdateClientAccount = new JButton("Update account");
        btnDeleteClientAccount = new JButton("Delete account");
        btnViewClientAccount = new JButton("View account");

        btnTransferMoneyButton = new JButton("Transfer money");
    }

    private void setLocationAndBounds() {
        jScrollPane.setBounds(0, 0, 650, 200);

        btnAddClientAccount.setBounds(50, 250, 150, 30);
        btnUpdateClientAccount.setBounds(50, 300, 150, 30);
        btnDeleteClientAccount.setBounds(50, 350, 150, 30);
        btnViewClientAccount.setBounds(50, 400, 150, 30);

        btnTransferMoneyButton.setBounds(250, 250,150, 30);
    }

    private void addComponentsToContainer() {
        container.add(btnAddClientAccount);
        container.add(btnUpdateClientAccount);
        container.add(btnDeleteClientAccount);
        container.add(btnViewClientAccount);
        container.add(btnTransferMoneyButton);
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

}

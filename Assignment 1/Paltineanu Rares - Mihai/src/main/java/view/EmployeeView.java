package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {

    private Container container;
    private JScrollPane jScrollPane;

    private JTable clientsIntormationTable;

    private JButton btnAddClientInformation;
    private JButton btnUpdateClientInformation;
    private JButton btnViewClientInformation;

    private JButton btnAddClientAccount;
    private JButton btnUpdateClientAccount;
    private JButton btnDeleteClientAccount;
    private JButton btnViewClientAccount;

    private JButton btnTransferMoneyButton;

    public EmployeeView() {
        initializeFields();
        setLocationAndBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        clientsIntormationTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jScrollPane = new JScrollPane(clientsIntormationTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        btnAddClientInformation = new JButton("Add information");
        btnUpdateClientInformation = new JButton("Update information");
        btnViewClientInformation = new JButton("View information");

        btnAddClientAccount = new JButton("Add account");
        btnUpdateClientAccount = new JButton("Update account");
        btnDeleteClientAccount = new JButton("Delete account");
        btnViewClientAccount = new JButton("View account");

        btnTransferMoneyButton = new JButton("Transfer money");
    }

    private void setLocationAndBounds() {

        jScrollPane.setBounds(50, 100, 400, 200);
        btnAddClientInformation.setBounds(50, 350, 150, 30);
        btnUpdateClientInformation.setBounds(50, 400, 150, 30);
        btnViewClientInformation.setBounds(50, 450, 150, 30);

        btnAddClientAccount.setBounds(400, 350, 150, 30);
        btnUpdateClientAccount.setBounds(400, 400, 150, 30);
        btnDeleteClientAccount.setBounds(400, 450, 150, 30);
        btnViewClientAccount.setBounds(400, 500, 150, 30);

        btnTransferMoneyButton.setBounds(400, 550,150, 30);
    }

    private void addComponentsToContainer() {
        container.add(btnAddClientInformation);
        container.add(btnUpdateClientInformation);
        container.add(btnViewClientInformation);
        container.add(btnAddClientAccount);
        container.add(btnUpdateClientAccount);
        container.add(btnDeleteClientAccount);
        container.add(btnViewClientAccount);
        container.add(btnTransferMoneyButton);
        container.add(jScrollPane);
    }

    private void initializeFrame() {
        setSize(650, 650);
        setResizable(false);
        setVisible(false);
        setTitle("Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setAddClientInformationListener(ActionListener actionListener) {
        this.btnAddClientInformation.addActionListener(actionListener);
    }

    public void setUpdateClientInformationListener(ActionListener actionListener) {
        this.btnUpdateClientInformation.addActionListener(actionListener);
    }

    public void setViewClientInformationListener(ActionListener actionListener) {
        this.btnViewClientInformation.addActionListener(actionListener);
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

    public JTable getClientsIntormationTable() {
        return clientsIntormationTable;
    }
}

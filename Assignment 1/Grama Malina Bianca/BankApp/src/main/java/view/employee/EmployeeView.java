package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JButton addClientBtn;
    private JButton updateClientBtn;
    private JButton viewClientBtn;
    private JButton addAccountBtn;
    private JButton updateAccountBtn;
    private JButton deleteAccountBtn;
    private JButton viewAccountBtn;
    private JButton transferMoney;
    private JButton processBills;

    public EmployeeView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(addClientBtn);
        add(updateClientBtn);
        add(viewClientBtn);
        add(addAccountBtn);
        add(updateAccountBtn);
        add(deleteAccountBtn);
        add(viewAccountBtn);
        add(transferMoney);
        add(processBills);
    }

    private void initializeFields() {
        addClientBtn = new JButton("Create Client");
        updateClientBtn = new JButton("Update Client");
        viewClientBtn = new JButton("View Clients");
        addAccountBtn = new JButton("Create Client Account");
        updateAccountBtn = new JButton("Update Client Account");
        deleteAccountBtn = new JButton("Delete Client Account");
        viewAccountBtn = new JButton("View Client Accounts");
        transferMoney = new JButton("Transfer Money");
        processBills = new JButton("Process Bills");
    }

    public void setAddClientBtnListener(ActionListener addClientBtnListener) {
        addClientBtn.addActionListener(addClientBtnListener);
    }

    public void setUpdateClientBtnListener(ActionListener updateClientBtnListener) {
        updateClientBtn.addActionListener(updateClientBtnListener);
    }

    public void setViewClientBtnListener(ActionListener viewClientBtnListener) {
        viewClientBtn.addActionListener(viewClientBtnListener);
    }

    public void setAddAccountBtnListener(ActionListener addAccountBtnListener) {
        addAccountBtn.addActionListener(addAccountBtnListener);
    }

    public void setUpdateAccountBtnListener(ActionListener updateAccountBtnListener) {
        updateAccountBtn.addActionListener(updateAccountBtnListener);
    }

    public void setDeleteAccountBtnListener(ActionListener deleteAccountBtnListener) {
        deleteAccountBtn.addActionListener(deleteAccountBtnListener);
    }

    public void setViewAccountBtnListener(ActionListener viewAccountBtnListener) {
        viewAccountBtn.addActionListener(viewAccountBtnListener);
    }

    public void setTransferMoneyListener(ActionListener transferMoneyListener) {
        transferMoney.addActionListener(transferMoneyListener);
    }

    public void setProcessBillsListener(ActionListener processBillsListener) {
        processBills.addActionListener(processBillsListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}

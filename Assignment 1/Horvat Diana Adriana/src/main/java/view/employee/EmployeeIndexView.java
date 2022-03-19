package view.employee;

import controller.EmployeeController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeIndexView extends JFrame {

    private JButton btnAddClient;
    private JButton btnUpdateClient;
//    private JButton btnViewClient;
    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccount;
    private JButton btnTransferMoney;
    private JButton btnProcessBills;

    public EmployeeIndexView(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(btnAddClient);
        add(btnUpdateClient);
//        add(btnViewClient);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
        add(btnViewAccount);
        add(btnTransferMoney);
        add(btnProcessBills);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnAddClient = new JButton("Add Client");
        btnUpdateClient = new JButton("Update and View Client");
//        btnViewClient = new JButton("View Client");
        btnCreateAccount = new JButton("Create account");
        btnUpdateAccount = new JButton("Edit account");
        btnDeleteAccount = new JButton("Delete account");
        btnViewAccount = new JButton("View Account");
        btnTransferMoney = new JButton("Transfer money");
        btnProcessBills = new JButton("Process bills");

    }

    public void addClientViewBtnListener(ActionListener clientViewBtnListener) {
        btnAddClient.addActionListener(clientViewBtnListener);
    }

    public void addUpdateClientBtnListener(ActionListener updateClientBtnListener) {
        btnUpdateClient.addActionListener(updateClientBtnListener);
    }

//    public void addBtnViewClientListener(ActionListener viewClientBtnListener) {
//        btnViewClient.addActionListener(viewClientBtnListener);
//    }

    public void addBtnCreateAccountListener(ActionListener createAccountBtnListener) {
        btnCreateAccount.addActionListener(createAccountBtnListener);
    }

    public void addBtnUpdateAccountListener(ActionListener updateAccountBtnListener) {
        btnUpdateAccount.addActionListener(updateAccountBtnListener);
    }

    public void addBtnDeleteAccountListener(ActionListener deleteAccountBtnListener) {
        btnDeleteAccount.addActionListener(deleteAccountBtnListener);
    }

    public void addBtnViewAccountListener(ActionListener viewAccountBtnListener) {
        btnViewAccount.addActionListener(viewAccountBtnListener);
    }

    public void addBtnTransferMoneyListener(ActionListener transferMoneyBtnListener) {
        btnTransferMoney.addActionListener(transferMoneyBtnListener);
    }

    public void addBtnProcessBillsListener(ActionListener processBillsBtnListener) {
        btnProcessBills.addActionListener(processBillsBtnListener);
    }
}

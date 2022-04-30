package view;

import model.Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame{
    String[] columnNames = {"Id","ID Number","Type","Balance","Date of creation"};
    Object[][] initData = {{" "," "," "," "," "}};
    private DefaultTableModel model = new DefaultTableModel();
    private JTable accountsTable = new JTable();
    private JScrollPane accountsScrollPane;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnDelete;
    private JTextField idNumber;
    private JTextField typeAcc;
    private JTextField balance;
    private JTextField dateOfCreation;
    private JTextField infoTF;
    private JButton btnTransfer;
    private JTextField fromAccount;
    private JTextField toAccount;
    private JTextField sumToTransfer;
    private JButton btnBills;
    private JTextField payments;

    public AccountView() throws HeadlessException{
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(infoTF);
        add(accountsScrollPane);
        add(btnAdd);
        add(btnUpdate);
        add(btnView);
        add(btnDelete);
        add(idNumber);
        add(typeAcc);
        add(balance);
        add(btnTransfer);
        add(fromAccount);
        add(toAccount);
        add(sumToTransfer);
        add(btnBills);
        add(payments);
        //add(dateOfCreation);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields() {
        model.setDataVector(initData,columnNames);
        this.accountsTable.setModel(model);
        this.accountsScrollPane = new JScrollPane(accountsTable);
        infoTF = new JTextField();
        infoTF.setEditable(false);
        btnAdd = new JButton("Create account");
        btnUpdate = new JButton("Update account");
        btnView = new JButton("View accounts");
        btnDelete = new JButton("Delete account");
        btnTransfer = new JButton("Transfer money");
        idNumber = new JTextField();
        typeAcc = new JTextField();
        balance = new JTextField();
        fromAccount = new JTextField();
        toAccount = new JTextField();
        sumToTransfer = new JTextField();
        btnBills = new JButton("Pay bill");
        payments = new JTextField();
        //tfIdCardNumber = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void setAddButtonListener(ActionListener addButtonListener){

        btnAdd.addActionListener(addButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener){
        btnView.addActionListener(viewButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener){
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setDeleteButtonListener(ActionListener accountsButtonListener){
        btnDelete.addActionListener(accountsButtonListener);
    }

    public void setTransferButtonListener(ActionListener accountsButtonListener){
        btnTransfer.addActionListener(accountsButtonListener);
    }

    public void setPayButtonListener(ActionListener accountsButtonListener){
        btnBills.addActionListener(accountsButtonListener);
    }

    public String getIdNumber() {
        return idNumber.getText();
    }

    public String getTypeAcc() {
        return typeAcc.getText();
    }

    public String getBalance() {
        return balance.getText();
    }

    public Long getFromAccountId(){
        if(fromAccount.getText().isEmpty()){
            return (long) -1;
        }
        return Long.parseLong(fromAccount.getText());
    }
    public Long getToAccountId(){
        if(toAccount.getText().isEmpty()){
            return (long) -1;
        }
        return Long.parseLong(toAccount.getText());
    }

    public int getSumToTransfer(){return  Integer.parseInt(sumToTransfer.getText());}

    public int getSumToPay(){return Integer.parseInt(payments.getText());}

    public void resetTextFields(){
        idNumber.setText("");
        typeAcc.setText("");
        balance.setText("");
        sumToTransfer.setText("");
        fromAccount.setText("");
        toAccount.setText("");
        payments.setText("");
    }
    public void viewAccounts(ArrayList<Account> accounts){
        for(Account acc : accounts){
            model.addRow(new Object[]{acc.getId(),acc.getIdNumber(),acc.getTypeAccount(),
                    acc.getAmountOfMoney(),acc.getCreationDate()});
        }
        accountsTable.setModel(model);
        accountsTable.setVisible(true);
    }
    public void resetTable(){
        int rows = model.getRowCount();
        for(int i = rows -1;i>=0;i--){
            model.removeRow(i);
        }
    }
    public long getIdForSelectedAccount() {
        if(accountsTable.getSelectedRow()==-1 || accountsTable.getSelectedColumn()==-1){
            return -1;
        }
        return (long)accountsTable.getValueAt(accountsTable.getSelectedRow(),0);
    }
    public long getIdNumberForSelectedAccount(){
        if(accountsTable.getSelectedRow()==-1 || accountsTable.getSelectedColumn()==-1){
            return -1;
        }
        return (long) accountsTable.getValueAt(accountsTable.getSelectedRow(),1);
    }
    public String getTypeForSelectedAccount(){
        if(accountsTable.getSelectedRow()==-1 || accountsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) accountsTable.getValueAt(accountsTable.getSelectedRow(),2);
    }
    public int getBalanceForSelectedAccount(){
        if(accountsTable.getSelectedRow()==-1 || accountsTable.getSelectedColumn()==-1){
            return -1;
        }
        return (int) accountsTable.getValueAt(accountsTable.getSelectedRow(),3);
    }
    public String getDateForSelectedAccount(){
        if(accountsTable.getSelectedRow()==-1 || accountsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) accountsTable.getValueAt(accountsTable.getSelectedRow(),4);
    }
    public ArrayList<String> getInfoForSelectClient(){
        ArrayList<String> info = new ArrayList<>();
        if(getIdNumber().isEmpty()){
            info.add(String.valueOf(getIdNumberForSelectedAccount()));
        }else {
            info.add(String.valueOf(getIdNumber()));
        }

        if(getTypeAcc().isEmpty()){
            info.add(String.valueOf(getTypeForSelectedAccount()));
        }else {
            info.add(String.valueOf(getTypeAcc()));
        }

        if(getBalance().isEmpty()){
            info.add(String.valueOf(getBalanceForSelectedAccount()));
        }else {
            info.add(String.valueOf(getBalance()));
        }
        info.add(getDateForSelectedAccount());
        return info;
    }
    public boolean isSelected(){
        if(accountsTable.getSelectedRow() ==-1 || accountsTable.getSelectedColumn() == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public void setTF(String s){
        infoTF.setText(s + "'s accounts:");
    }
}

package view;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame{

    String[] columnNames = {"Id","Full name","Address","PNC","Id Card Number"};
    Object[][] initData = {{" "," "," "," "," "}};
    private DefaultTableModel model = new DefaultTableModel();
    private JTable clientsTable = new JTable();
    private JScrollPane clientsScrollPane;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnAccount;
    private JTextField tfFullName;
    private JTextField tfAddress;
    private JTextField tfPNC;
    private JTextField tfIdCardNumber;
    private String username;

    public EmployeeView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(clientsScrollPane);
        add(btnAdd);
        add(btnUpdate);
        add(btnView);
        add(btnAccount);
        add(tfFullName);
        add(tfAddress);
        add(tfPNC);
        add(tfIdCardNumber);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields() {
        model.setDataVector(initData,columnNames);
        this.clientsTable.setModel(model);
        this.clientsScrollPane = new JScrollPane(clientsTable);
        btnAdd = new JButton("Add client");
        btnUpdate = new JButton("Update client");
        btnView = new JButton("View clients");
        btnAccount = new JButton("Accounts");
        tfFullName = new JTextField();
        tfAddress = new JTextField();
        tfPNC = new JTextField();
        tfIdCardNumber = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public String getFullName() {
        return tfFullName.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getPNC() {
        return tfPNC.getText();
    }

    public String getIdCardNumber() {
        return tfIdCardNumber.getText();
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

    public void setAccountsButtonListener(ActionListener accountsButtonListener){
        btnAccount.addActionListener(accountsButtonListener);
    }
    public void resetTextFields(){
        tfIdCardNumber.setText("");
        tfPNC.setText("");
        tfAddress.setText("");
        tfFullName.setText("");
    }

    public void viewClients(ArrayList<Client> clientList){
        for(Client client : clientList){
            model.addRow(new Object[]{client.getId(),client.getName(),client.getAddress(),client.getPnc(),client.getIdCardNumber()});
        }
        clientsTable.setModel(model);
        clientsTable.setVisible(true);
    }
    public void resetTable(){
        int rows = model.getRowCount();
        for(int i = rows -1;i>=0;i--){
            model.removeRow(i);
        }
    }

    public long getIdForSelectedClient() {
        if(clientsTable.getSelectedRow()==-1 || clientsTable.getSelectedColumn()==-1){
            return -1;
        }
        return (long)clientsTable.getValueAt(clientsTable.getSelectedRow(),0);
    }
    public String getNameForSelectedClient(){
        if(clientsTable.getSelectedRow()==-1 || clientsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) clientsTable.getValueAt(clientsTable.getSelectedRow(),1);
    }
    public String getAddressForSelectedClient(){
        if(clientsTable.getSelectedRow()==-1 || clientsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) clientsTable.getValueAt(clientsTable.getSelectedRow(),2);
    }
    public String getPncForSelectedClient(){
        if(clientsTable.getSelectedRow()==-1 || clientsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) clientsTable.getValueAt(clientsTable.getSelectedRow(),3);
    }
    public String getIdCardNrForSelectedClient(){
        if(clientsTable.getSelectedRow()==-1 || clientsTable.getSelectedColumn()==-1){
            return null;
        }
        return (String) clientsTable.getValueAt(clientsTable.getSelectedRow(),4);
    }
    public ArrayList<String> getInfoForSelectClient(){
        ArrayList<String> info = new ArrayList<>();
        if(getFullName().isEmpty()){
            info.add(getNameForSelectedClient());
        }else {
           info.add(getFullName());
        }
        if(getAddress().isEmpty()){
            info.add(getAddressForSelectedClient());
        }else {
            info.add(getAddress());
        }
        if(getPNC().isEmpty()){
            info.add(getPncForSelectedClient());
        }else {
           info.add(getPNC());
        }
        if(getIdCardNumber().isEmpty()){
            info.add(getIdCardNrForSelectedClient());
        }else {
            info.add(getIdCardNumber());
        }
        return info;
    }
    public boolean isSelected(){
        if(clientsTable.getSelectedRow() ==-1 || clientsTable.getSelectedColumn() == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public void setVisible() {
        this.setVisible(true);
    }
}

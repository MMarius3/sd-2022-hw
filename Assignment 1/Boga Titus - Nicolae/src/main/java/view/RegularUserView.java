package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class RegularUserView  extends JFrame {

    private JList<String> jlClientList;
    //private JTextField tfLoggedUser;

    private JTextField tfClientName;
    private JTextField tfClientCnp;
    private JTextField tfClientAddress;
    private JLabel jlName;
    private JLabel jlCnp;
    private JLabel jlAddress;
    private JButton btnUpdate;
    private JButton btnShow;
    private JButton btnAddClient;
    private JList<String> jlAccounts;
    private JLabel jlType;
    private JLabel jlAmmount;
    private JLabel jlDate;
    private JTextField tfType;
    private JTextField tfAmmount;
    private JTextField tfDate;
    private JButton btnShowAccount;
    private JButton btnUpdateAccount;
    private JButton btnAddNewAccount;
    private JButton btnDeleteAccount;
    private JButton btnAccountToTransferFrom;
    private JButton btnAccountToTransferTo;
    private JLabel jlAccountToTransferFrom;
    private JLabel jlAccountToTransferTo;
    private JTextField jtAmountToTransfer;
    private JButton btnTransfer;


    public RegularUserView() throws HeadlessException {
        setSize(300, 900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(jlClientList);
        add(jlName);
        add(tfClientName);
        add(jlCnp);
        add(tfClientCnp);
        add(jlAddress);
        add(tfClientAddress);
        add(btnShow);
        add(btnUpdate);
        add(btnAddClient);
        add(jlAccounts);
        add(jlType);
        add(tfType);
        add(jlAmmount);
        add(tfAmmount);
        add(jlDate);
        add(tfDate);
        add(btnShowAccount);
        add(btnUpdateAccount);
        add(btnAddNewAccount);
        add(btnDeleteAccount);
        add(btnAccountToTransferFrom);
        add(jlAccountToTransferFrom);
        add(btnAccountToTransferTo);
        add(jlAccountToTransferTo);
        add(jtAmountToTransfer);
        add(btnTransfer);

        //   add(tfClientInformation);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {

        jtAmountToTransfer = new JTextField();
        btnTransfer = new JButton("Transfer");

        btnAccountToTransferFrom = new JButton("Select Account to Transfer From");
        jlAccountToTransferFrom = new JLabel();
        btnAccountToTransferTo = new JButton("Select Account to transfer To");
        jlAccountToTransferTo = new JLabel();

        jlType= new JLabel("Type:");
        tfType = new JTextField();
        jlAccounts = new JList<>();

        tfAmmount = new JTextField("Ammount");
        tfDate = new JTextField("Date");

        jlName = new JLabel("Name:");
        jlCnp = new JLabel("CNP:");
        jlAddress = new JLabel("Address:");
        jlClientList = new JList<String>();
        btnUpdate = new JButton("Update Selected Client");
        btnShow = new JButton("Show Selected Client");
        btnAddClient = new JButton("Add New Client");
      //  tfLoggedUser = new JTextField();
      //  tfLoggedUser.setEditable(false);
        tfClientName = new JTextField("Name");
        tfClientCnp = new JTextField("Cnp");

        tfClientAddress = new JTextField("Address");
        jlAmmount = new JLabel("Ammount");
        jlDate = new JLabel("Date of Creation");

        btnShowAccount = new JButton("Show Account");
        btnUpdateAccount= new JButton("Update Selected Account");
        btnAddNewAccount= new JButton("Add new Account");
        btnDeleteAccount= new JButton("Delete Selected Account");

    }

    public void setUpdateButtonListener(ActionListener loginButtonListener) {
        btnUpdate.addActionListener(loginButtonListener);
    }

    public void setShowButtonListener(ActionListener registerButtonListener) {
        btnShow.addActionListener(registerButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        btnAddClient.addActionListener(addButtonListener);
    }

    public void setShowAccountButtonListener(ActionListener loginButtonListener) {
        btnShowAccount.addActionListener(loginButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener loginButtonListener) {
        btnUpdateAccount.addActionListener(loginButtonListener);
    }

    public void setAddAccountButtonListener(ActionListener loginButtonListener) {
        btnAddNewAccount.addActionListener(loginButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener loginButtonListener) {
        btnDeleteAccount.addActionListener(loginButtonListener);
    }

    public void setFromButtonListener(ActionListener loginButtonListener) {
        btnAccountToTransferFrom.addActionListener(loginButtonListener);
    }

    public void setTotButtonListener(ActionListener loginButtonListener) {
        btnAccountToTransferTo.addActionListener(loginButtonListener);
    }

    public void setTransfertButtonListener(ActionListener loginButtonListener) {
        btnTransfer.addActionListener(loginButtonListener);
    }


    public void setClientList(DefaultListModel<String> defaultListModel)
    {

        jlClientList.setModel(defaultListModel);

    }

    public long getSelectedId()
    {
        return Integer.parseInt(jlClientList.getSelectedValue().replaceAll("[^0-9]", ""));
    }

    public long getSelectedAccount()
    {
        return Integer.parseInt(jlAccounts.getSelectedValue());

    }
    public void setFrom(String from)
    {
        jlAccountToTransferFrom.setText(from);
    }

    public void setTo(String to)
    {
        jlAccountToTransferTo.setText(to);
    }

    public void setClientName(String name)
    {
        tfClientName.setText(name);
    }

    public void setClientCnp(String cnp)
    {
        tfClientCnp.setText(cnp);
    }

    public void setTfClientAddress(String address)
    {
        tfClientAddress.setText(address);
    }

    public String getClientName()
    {
        return tfClientName.getText();
    }

    public String getClientCnp()
    {
        return tfClientCnp.getText();
    }

    public String getClientAddress()
    {
        return tfClientAddress.getText();
    }


    public void setAccountList( DefaultListModel<String> defaultListModel)
    {
        jlAccounts.setModel(defaultListModel);
    }

    public void setVisibility(boolean visibility) {
        this.setVisible(visibility);
    }

    public void setAccountType(String type)
    {
        this.tfType.setText(type);
    }

    public void setAccountAmount(Long amount)
    {
        this.tfAmmount.setText(amount.toString());
    }

    public void setDate(Date date)
    {
        this.tfDate.setText(date.toString());
    }

    public String getAccountType()
    {
        return tfType.getText();
    }

    public Long getAccountAmount()
    {
        return Long.valueOf(tfAmmount.getText());
    }

    public Date getDate() throws ParseException {
        return  new SimpleDateFormat("yyyy-MM-dd").parse(tfDate.getText());
    }

    public Long getTransferAmount()
    {
        return Long.valueOf(jtAmountToTransfer.getText());
    }
}

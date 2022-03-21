package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class UserView extends JFrame {

    private JTextField tfInfoName;
    private JTextField tfInfoCardId;
    private JTextField tfInfoCNP;
    private JTextField tfInfoAddress;
    private JButton btnInfoView;
    private JButton btnInfoUpdate;
    private JLabel labelInfo;


    private JTextField tfAccountId;
    private JTextField tfAccountType;
    private JTextField tfAccountAmmount;
    private JButton btnAccountCreate;
    private JButton btnAccountUpdate;
    private JButton btnAccountDelete;
    private JButton btnAccountView;
    private JButton btnAccount;
    private JLabel labelAccount;

    private JTextField tfName1;
    private JTextField tfName2;
    private JTextField tfBill;
    private JButton btnPayBill;
    private JButton btnTransfer;
    private JLabel labelOperations;

    public UserView() throws HeadlessException {
        setSize(1200, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfInfoName);
        add(tfInfoCardId);
        add(tfInfoCNP);
        add(tfInfoAddress);
        add(btnInfoView);
        add(btnInfoUpdate);
        add(labelInfo);

        add(tfAccountId);
        add(tfAccountType);
        add(tfAccountAmmount);
        add(btnAccountCreate);
        add(btnAccountUpdate);
        add(btnAccountDelete);
        add(btnAccountView);
        add(labelAccount);

        add(tfName1);
        add(tfName2);
        add(tfBill);
        add(btnPayBill);
        add(btnTransfer);
        add(labelOperations);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfInfoName = new JTextField("Name");
        tfInfoCardId = new JTextField("CardID");
        tfInfoCNP = new JTextField("CNP");
        tfInfoAddress = new JTextField("Address");
        tfAccountId = new JTextField("ID");
        tfAccountType = new JTextField("Type");
        tfAccountAmmount = new JTextField("Ammount");
        tfName1 = new JTextField("Name1");
        tfName2 = new JTextField("Name2");
        tfBill = new JTextField("Bill");

        btnInfoView = new JButton("View information");
        btnInfoUpdate = new JButton("Update information");
        btnAccountCreate = new JButton("Create account");
        btnAccountUpdate = new JButton("Update account");
        btnAccountDelete = new JButton("Delete account");
        btnAccountView = new JButton("View account");
        btnPayBill = new JButton("Pay bill");
        btnTransfer = new JButton("Transfer money");

        labelInfo = new JLabel("\n");
        labelAccount = new JLabel("\n");
        labelOperations = new JLabel("\n");
    }

    public String getTfInfoName() {
        return tfInfoName.getText();
    }

    public String getTfInfoCardId() {
        return tfInfoCardId.getText();
    }

    public String getTfInfoCNP() {
        return tfInfoCNP.getText();
    }

    public String getTfInfoAddress() {
        return tfInfoAddress.getText();
    }

    public String getTfAccountId() {
        return tfAccountId.getText();
    }

    public String getTfAccountType() {
        return tfAccountType.getText();
    }

    public String getTfAccountAmmount() {
        return tfAccountAmmount.getText();
    }

    public JLabel getLabelInfo() {
        return labelInfo;
    }

    public JLabel getLabelAccount() {
        return labelAccount;
    }

    public String getTfName1() {
        return tfName1.getText();
    }

    public String getTfName2() {
        return tfName2.getText();
    }

    public String getTfBill() {
        return tfBill.getText();
    }

    public JLabel getLabelOperations() {
        return labelOperations;
    }

    public void setBtnInfoViewListener(ActionListener listener){
        btnInfoView.addActionListener(listener);
    }

    public void setBtnInfoUpdateListener(ActionListener listener){
        btnInfoUpdate.addActionListener(listener);
    }

    public void setBtnAccountCreateListener(ActionListener listener){
        btnAccountCreate.addActionListener(listener);
    }

    public void setBtnAccountUpdateListener(ActionListener listener){
        btnAccountUpdate.addActionListener(listener);
    }

    public void setBtnAccountDeleteListener(ActionListener listener){
        btnAccountDelete.addActionListener(listener);
    }

    public void setBtnAccountViewListener(ActionListener listener){
        btnAccountView.addActionListener(listener);
    }

    public void setBtnPayBillListener(ActionListener listener){btnPayBill.addActionListener(listener);}

    public void setBtnTransferListener(ActionListener listener){btnTransfer.addActionListener(listener);}

    public void setVisible() {
        this.setVisible(true);
    }
}

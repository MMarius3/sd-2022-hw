package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {
    private JLabel j1,j2,j3,j4,j5,j6;
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfIdcardnumber;
    private JTextField tfCnp;
    private JTextField tfAddress;
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnView;

    private JLabel ja1,ja2,ja3,ja4,ja5,ja6,ja7;
    private JTextField tfaId;
    private JTextField tfaIdnumber;
    private JTextField tfaType;
    private JTextField tfaMoney;
    private JTextField tfaDateOfCreation;
    private JTextField tfaClientID;
    private JButton btnaCreate;
    private JButton btnaUpdate;
    private JButton btnaDelete;
    private JButton btnaView;

    public EmployeeView() {
        setSize(900, 700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(j6);
        add(j1);
        add(tfId);
        add(j2);
        add(tfName);
        add(j3);
        add(tfIdcardnumber);
        add(j4);
        add(tfCnp);
        add(j5);
        add(tfAddress);
        add(btnCreate);
        add(btnDelete);
        add(btnUpdate);
        add(btnView);

        add(ja7);
        add(ja1);
        add(tfaId);
        add(ja2);
        add(tfaIdnumber);
        add(ja3);
        add(tfaType);
        add(ja4);
        add(tfaMoney);
        add(ja5);
        add(tfaDateOfCreation);
        add(ja6);
        add(tfaClientID);
        add(btnaCreate);
        add(btnaDelete);
        add(btnaUpdate);
        add(btnaView);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfId = new JTextField();
        tfName = new JTextField();
        tfIdcardnumber = new JTextField();
        tfCnp = new JTextField();
        tfAddress = new JTextField();
        tfName = new JTextField();
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");
        j1= new JLabel("id");
        j2= new JLabel("name");
        j3= new JLabel("id Card Number");
        j4= new JLabel("cnp");
        j5= new JLabel("address");
        j6= new JLabel("FOR CLIENTS:");
        tfaId = new JTextField();
        tfaIdnumber = new JTextField();
        tfaType = new JTextField();
        tfaMoney = new JTextField();
        tfaDateOfCreation = new JTextField();
        tfaClientID = new JTextField();
        btnaCreate = new JButton("Create A");
        btnaUpdate = new JButton("Update A");
        btnaDelete = new JButton("Delete A");
        btnaView = new JButton("View A");
        ja1= new JLabel("id");
        ja2= new JLabel("idnumber");
        ja3= new JLabel("type");
        ja4= new JLabel("money");
        ja5= new JLabel("dateOfCreation");
        ja6= new JLabel("clientID");
        ja7= new JLabel("FOR ACCOUNTS:");
    }

    public String getName() {
        return tfName.getText();
    }
    public String getIdcardnumber() {
        return tfIdcardnumber.getText();
    }
    public String getCnp() {
        return tfCnp.getText();
    }
    public String getAddress() {
        return tfAddress.getText();
    }

    public void addCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void addUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void addViewButtonListener(ActionListener viewButtonListener) {
        btnView.addActionListener(viewButtonListener);
    }
    public void addDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }
    public void setName(String name)
    {
        tfName.setText(name);
    }
    public void setIdcardnumber(Long idcardnumber)
    {
        tfIdcardnumber.setText(Long.toString(idcardnumber));
    }
    public void setCnp(Long cnp)
    {
        tfCnp.setText(Long.toString(cnp));
    }
    public void setAddress(String address)
    {
        tfAddress.setText(address);
    }
    public void setId(int id)
    {
        tfId.setText(Integer.toString(id));
    }

    public String getaIdnumber() {
        return tfaIdnumber.getText();
    }
    public String getaType() {
        return tfaType.getText();
    }
    public String getaMoney() {
        return tfaMoney.getText();
    }
    public String getaDateOfCreation() {
        return tfaDateOfCreation.getText();
    }
    public String getaClientID() {
        return tfaClientID.getText();
    }

    public void addACreateButtonListener(ActionListener AcreateButtonListener) {
        btnaCreate.addActionListener(AcreateButtonListener);
    }

    public void addAUpdateButtonListener(ActionListener AupdateButtonListener) {
        btnaUpdate.addActionListener(AupdateButtonListener);
    }

    public void addAViewButtonListener(ActionListener AviewButtonListener) {
        btnaView.addActionListener(AviewButtonListener);
    }
    public void addADeleteButtonListener(ActionListener AdeleteButtonListener) {
        btnaDelete.addActionListener(AdeleteButtonListener);
    }

    public void setAIdnumber(Long idnumber)
    {
        tfaIdnumber.setText(Long.toString(idnumber));
    }
    public void setAType(String type)
    {
        tfaType.setText(type);
    }
    public void setAMoney(Long money)
    {
        tfaMoney.setText(Long.toString(money));
    }
    public void setAdateOfCreation(String date)
    {
        tfaDateOfCreation.setText(date);
    }
    public void setAClientId(int id)
    {
        tfaMoney.setText(Integer.toString(id));
    }



}

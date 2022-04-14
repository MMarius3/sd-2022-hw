package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame{

    private JTextField tfClientname;
    private JTextField tfClientname2;
    private JTextField tfClientid;
    private JTextField tfClientid2;
    private JTextField tfClientidcard;
    private JTextField tfClientidcard2;
    private JTextField tfClientcnp;
    private JTextField tfClientcnp2;
    private JTextField tfClientaddress2;
    private JTextField tfClientaddress;
    private JButton btnaddClient;
    private JButton btnupdateClient;
    private JButton btnviewClient;
    private JButton btnaddAcc;
    private JButton btnupdateAcc;
    private JButton btnviewAcc;
    private JButton btndeleteAcc;
    private JButton btnBills;
    private JTextField tfAccountid;
    private JTextField tfAccountid2;
    private JTextField tfAccounttype;
    private JTextField tfAccounttype2;
    private JTextField tfAccountbalance;
    private JTextField tfAccountbalance2;
    private  JTextField tfAccountclientid;
    private  JTextField tfAccountclientid2;
    private  JTextField tfAccountcreation2;
    private  JTextField tfAccountcreation;
    public EmployeeView(){
        setSize(1000,720);
        setLocationRelativeTo(null);
        initializeFields();

        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfClientid);
        add(tfClientname2);add(tfClientname);
        add(tfClientcnp2);add(tfClientcnp);
        add(tfClientidcard2);add(tfClientidcard);
        add(tfClientaddress2);add(tfClientaddress);
        add(btnaddClient);
        add(btnupdateClient);
        add(btnviewClient);
        add(tfAccountid);
        add(tfAccountcreation2);add(tfAccountcreation);
        add(tfAccountclientid2);add(tfAccountclientid);
        add(tfAccounttype2);add(tfAccounttype);
        add(tfAccountbalance2);add(tfAccountbalance);
        add(btnviewAcc);add(btndeleteAcc);
        add(btnupdateAcc);add(btnaddAcc);
        add(btnBills);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields() {
        tfClientname = new JTextField();
        tfClientname2 = new JTextField("Client Name");
        //tfClientname2.setText("Clent Name");
        tfClientaddress = new JTextField();
        tfClientaddress2 = new JTextField();
        tfClientaddress2.setText("Client address");
        tfClientid = new JTextField();
        tfClientid2 = new JTextField();
        tfClientid2.setText("Client id");
        tfClientidcard = new JTextField();
        tfClientidcard2 = new JTextField();
        tfClientidcard2.setText("Identity card number:");
        tfClientcnp2 = new JTextField();
        tfClientcnp2.setText("CNP");
        tfClientcnp = new JTextField();
        btnupdateClient = new JButton("Update Client");
        btnaddClient = new JButton("Add Client");
        btnviewClient = new JButton("View Client");
        btnBills = new JButton("Bills and transfers");
        tfAccountbalance = new JTextField();
        tfAccountid = new JTextField();
        tfAccountcreation = new JTextField();
        tfAccounttype = new JTextField();
        tfAccountclientid = new JTextField();
        tfAccountbalance2 = new JTextField();
        tfAccountbalance2.setText("Account balance");
        tfAccountid2 = new JTextField();
        tfAccountid2.setText("Account id");
        tfAccountcreation2 = new JTextField();
        tfAccountcreation2.setText("Account creation date");
        tfAccounttype2 = new JTextField();
        tfAccounttype2.setText("Account type");
        tfAccountclientid2 = new JTextField();
        tfAccountclientid2.setText("Client id of account");
        btndeleteAcc = new JButton("Delete account");
        btnviewAcc = new JButton("view Account");
        btnupdateAcc = new JButton("update Account");
        btnaddAcc = new JButton("add Account");
    }
    public String getAccId(){return tfAccountid.getText();};
    public void setAccId(String id){tfAccountid.setText(id);}
    public String getAccBalance(){return tfAccountbalance.getText();}
    public void setAccBalance(String balance){tfAccountbalance.setText(balance);}
    public String getAccType(){return tfAccounttype.getText();};
    public void setAccType(String type){tfAccounttype.setText(type);};
    public String getAccDate(){return tfAccountcreation.getText();}
    public void setAccDate(String date){tfAccountcreation.setText(date);}
    public String getAccClientID(){return tfAccountclientid.getText();};
    public void setAccClientID(String id){tfAccountclientid.setText(id);}

    public String getId(){
        return tfClientid.getText();
    }
    public String getName(){
        return tfClientname.getText();
    }
    public String getCnp(){
        return tfClientcnp.getText();
    }
    public String getidCard(){
        return tfClientidcard.getText();
    }
    public String getAddress(){
        return tfClientaddress.getText();
    }
    public void setId(Long id){tfClientid.setText(String.valueOf(id));}
    public void setName(String name){ tfClientname.setText(name);}
    public void setidCard(String idCard){tfClientidcard.setText(idCard);}
    public void setAddress(String address){tfClientaddress.setText(address);}
    public void addAddButtonListener(ActionListener addButtonListener) {
        btnaddClient.addActionListener(addButtonListener);
    }
    public void addUpdateButtonListener(ActionListener updateButtonListener) {
        btnupdateClient.addActionListener(updateButtonListener);
    }
    public void addViewButtonListener(ActionListener viewButtonListener){
        btnviewClient.addActionListener(viewButtonListener);
    }
    public void addAddButtonListenerAcc(ActionListener addButtonListener) {
        btnaddAcc.addActionListener(addButtonListener);
    }
    public void addUpdateButtonListenerAcc(ActionListener updateButtonListener) {
        btnupdateAcc.addActionListener(updateButtonListener);
    }
    public void addViewButtonListenerAcc(ActionListener viewButtonListener){
        btnviewAcc.addActionListener(viewButtonListener);
    }
    public void addDeleteButtonListenerAcc(ActionListener deleteButtonListener){
        btndeleteAcc.addActionListener(deleteButtonListener);
    }
    public void addButonBillsListener(ActionListener changeViewListener){
        btnBills.addActionListener(changeViewListener);
    }
}

package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class UpdateClientView extends JFrame{
    private JTextField id;
    private JButton searchBtn;
    private JTextField name;
    private JTextField card_no;
    private JTextField cnp;
    private JTextField address;
    private JButton updateBtn;

    public UpdateClientView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(searchBtn);
        add(name);
        add(card_no);
        add(cnp);
        add(address);
        add(updateBtn);
    }

    private void initializeFields() {
        id = new JTextField("Type the id of the client to be updated.");
        name = new JTextField();
        card_no = new JTextField();
        cnp = new JTextField();
        address = new JTextField();
        searchBtn = new JButton("Search");
        updateBtn = new JButton("Update Client");
    }

    public String getId() {return id.getText();}

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setCard_no(String card_no) {
        this.card_no.setText(card_no);
    }

    public void setCnp(String cnp) {
        this.cnp.setText(cnp);
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public String getName() {
        return name.getText();
    }

    public String getCardNo() {
        return card_no.getText();
    }

    public String getCnp() {
        return cnp.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        updateBtn.addActionListener(updateClientButtonListener);
    }

    public void setSearchClientButtonListener(ActionListener searchClientButtonListener) {
        searchBtn.addActionListener(searchClientButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

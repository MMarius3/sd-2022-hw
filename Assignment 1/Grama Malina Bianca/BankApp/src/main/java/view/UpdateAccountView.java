package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class UpdateAccountView extends JFrame {
    private JTextField id;
    private JButton searchBtn;
    private JTextField type;
    private JTextField amount;
    private JButton updateBtn;

    public UpdateAccountView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(searchBtn);
        add(type);
        add(amount);
        add(updateBtn);
    }

    private void initializeFields() {
        id = new JTextField("Type the id of the client to be updated.");
        type = new JTextField("Type of Account");
        amount = new JTextField("Amount of $ in Account");
        searchBtn = new JButton("Search");
        updateBtn = new JButton("Update Client Account");
    }

    public String getId() {return id.getText();}

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setAmount(String amount) {
        this.amount.setText(amount);
    }

    public String getAccType() {
        return type.getText();
    }

    public String getAccAmount() {
        return amount.getText();
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        updateBtn.addActionListener(updateAccountButtonListener);
    }

    public void setSearchAccountButtonListener(ActionListener searchAccountButtonListener) {
        searchBtn.addActionListener(searchAccountButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

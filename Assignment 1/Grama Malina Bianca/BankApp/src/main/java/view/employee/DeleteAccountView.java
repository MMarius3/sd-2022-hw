package view.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class DeleteAccountView extends JFrame {
    private JTextField id;
    private JButton searchBtn;
    private JTextField type;
    private JTextField amount;
    private JButton deleteBtn;

    public DeleteAccountView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(searchBtn);
        add(type);
        add(amount);
        add(deleteBtn);
    }

    private void initializeFields() {
        id = new JTextField("Type the id of the account to be deleted.");
        type = new JTextField("Type of Account");
        type.setEditable(false);
        amount = new JTextField("Amount of $ in Account");
        amount.setEditable(false);
        searchBtn = new JButton("Search");
        deleteBtn = new JButton("Delete Client Account");
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

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        deleteBtn.addActionListener(deleteAccountButtonListener);
    }

    public void setSearchAccountButtonListener(ActionListener searchAccountButtonListener) {
        searchBtn.addActionListener(searchAccountButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

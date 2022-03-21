package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AddUserView extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton addBtn;

    public AddUserView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(username);
        add(password);
        add(addBtn);
    }

    private void initializeFields() {
        username = new JTextField("username");
        password = new JTextField("password");
        addBtn = new JButton("Add User");
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void setAddUserBtnListener(ActionListener addUserBtnListener) {
        addBtn.addActionListener(addUserBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

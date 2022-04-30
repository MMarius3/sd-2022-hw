package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EditUserView extends JFrame{
    private JTextField id;
    private JButton search;
    private JTextField username;
    private JTextField password;
    private JButton edit;

    public EditUserView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(search);
        add(username);
        add(password);
        add(edit);
    }

    private void initializeFields () {
        id = new JTextField("ID of the user to edit.");
        search = new JButton("Search");
        username = new JTextField("username");
        password = new JTextField("password");
        edit = new JButton("Edit User");
    }

    public String getId() {
        return id.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public void setSearchUserBtnListener(ActionListener searchUserBtnListener) {
        search.addActionListener(searchUserBtnListener);
    }

    public void setUpdateUserBtnListener(ActionListener updateUserBtnListener) {
        edit.addActionListener(updateUserBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}

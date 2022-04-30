package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class DeleteUserView extends JFrame{
    private JTextField id;
    private JButton search;
    private JTextField username;
    private JTextField password;
    private JButton delete;

    public DeleteUserView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(id);
        add(search);
        add(username);
        add(password);
        add(delete);
    }

    private void initializeFields () {
        id = new JTextField("ID of the user to delete.");
        search = new JButton("Search");
        username = new JTextField("username");
        username.setEditable(false);
        password = new JTextField("password");
        password.setEditable(false);
        delete = new JButton("Delete User");
    }

    public String getId() {
        return id.getText();
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
        delete.addActionListener(updateUserBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

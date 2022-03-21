package view.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame{
    private JButton addUserBtn;
    private JButton editUserBtn;
    private JButton deleteUserBtn;
    private JButton viewUserBtn;

    public AdminView() throws HeadlessException {
        setSize(500, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(addUserBtn);
        add(editUserBtn);
        add(deleteUserBtn);
        add(viewUserBtn);
    }

    private void initializeFields() {
        addUserBtn = new JButton("Add User");
        editUserBtn = new JButton("Update User");
        deleteUserBtn = new JButton("Delete User");
        viewUserBtn = new JButton("View Users");
    }

    public void setAddUserBtnListener(ActionListener addUserBtnListener) {
        addUserBtn.addActionListener(addUserBtnListener);
    }

    public void setEditUserBtnListener(ActionListener editUserBtnListener) {
        editUserBtn.addActionListener(editUserBtnListener);
    }

    public void setDeleteUserBtnListener(ActionListener deleteUserBtnListener) {
        deleteUserBtn.addActionListener(deleteUserBtnListener);
    }

    public void setViewUserBtnListener(ActionListener viewUserBtnListener) {
        viewUserBtn.addActionListener(viewUserBtnListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}

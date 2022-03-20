package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame{
    private JTextField tfId;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnCreate;
    private JButton btnRead;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JLabel label;

    public AdminView() throws HeadlessException {
        setSize(900, 600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfId);
        add(tfUsername);
        add(tfPassword);
        add(btnCreate);
        add(btnRead);
        add(btnUpdate);
        add(btnDelete);
        add(label);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfId = new JTextField();
        tfUsername = new JTextField();
        tfPassword = new JTextField();

        btnCreate = new JButton("Create user");
        btnRead = new JButton("Read user");
        btnUpdate = new JButton("Update user");
        btnDelete = new JButton("Delete user");

        label = new JLabel("\n");
    }

    public JTextField getTfId() {
        return tfId;
    }

    public JTextField getTfUsername() {
        return tfUsername;
    }

    public JTextField getTfPassword() {
        return tfPassword;
    }

    public void setBtnCreateListener(ActionListener listener){
        btnCreate.addActionListener(listener);
    }

    public void setBtnReadListener(ActionListener listener){
        btnRead.addActionListener(listener);
    }

    public void setBtnUpdateListener(ActionListener listener){
        btnUpdate.addActionListener(listener);
    }

    public void setBtnDeleteListener(ActionListener listener){
        btnDelete.addActionListener(listener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}

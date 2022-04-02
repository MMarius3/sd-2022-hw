package view;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

    private JLabel labelUsername;
    private JLabel labelPassword;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(labelUsername);
        add(tfUsername);
        add(labelPassword);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        labelUsername = new JLabel("Username (Email):");
        labelPassword = new JLabel("Password:");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void addLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void addRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }
}
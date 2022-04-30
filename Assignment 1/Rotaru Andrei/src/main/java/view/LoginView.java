package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() throws HeadlessException {
        this.setTitle("Login Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbUsername);
        add(tfUsername);
        add(lbPassword);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        lbUsername = new JLabel("Username");
        lbPassword = new JLabel("Password");
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void clearTextFields(){
        tfUsername.setText("");
        tfPassword.setText("");
    }
}

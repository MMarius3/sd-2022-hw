package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

public class LoginViewNew extends JFrame {
    private JPanel loginForm;
    private JTextField username;
    private JTextField password;
    private JButton loginButton;
    private JButton registerButton;


    public LoginViewNew(String title) {
        super(title);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginForm);
        setLocationRelativeTo(null);
        setSize(600, 400);
//        this.pack();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        loginButton.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        registerButton.addActionListener(registerButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }


    private void createUIComponents() {
    }
}

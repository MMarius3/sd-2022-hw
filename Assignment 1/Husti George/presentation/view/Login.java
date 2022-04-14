package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login extends JFrame {

    private Container container;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton signUpButton;

    public Login() {
        initUIElements();
        container.setLayout(null);
        setUIElementsLocationAndSize();
        addUIElementsToContainer();
        initFrame();
    }

    private void initFrame() {
        this.setTitle("Login");
        this.setBounds(10, 10, 1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    private void initUIElements() {
        container = getContentPane();
        signInButton = new JButton("Sign In");
        usernameLabel = new JLabel("username");
        passwordField = new JPasswordField();
        signUpButton = new JButton("Sign Up");
        passwordLabel = new JLabel("password");
        usernameTextField = new JTextField();
    }

    private void setUIElementsLocationAndSize() {
        usernameLabel.setBounds(150, 250, 200, 50);
        passwordField.setBounds(250, 320, 250, 50);
        usernameTextField.setBounds(250, 250, 250, 50);
        passwordLabel.setBounds(150, 320, 200, 50);
        signUpButton.setBounds(450, 450, 200, 50);
        signInButton.setBounds(150, 450, 200, 50);
    }

    private void addUIElementsToContainer() {
        container.add(passwordLabel);
        container.add(signInButton);
        container.add(usernameTextField);
        container.add(usernameLabel);
        container.add(passwordField);
        container.add(signUpButton);
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return Arrays.toString(passwordField.getPassword());
    }

    public void setAlert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setActionListenerToSignInButton(ActionListener actionListener) {
        signInButton.addActionListener(actionListener);
    }

    public void setActionListenerToSignUpButton(ActionListener actionListener) {
        signUpButton.addActionListener(actionListener);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    public static void main(String[] a) {
        Login frame = new Login();
        frame.setVisible(true);
    }
}
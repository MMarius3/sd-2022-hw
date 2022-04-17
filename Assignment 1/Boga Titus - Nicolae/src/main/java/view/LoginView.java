package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

  private JTextField tfUsername;
  private JTextField tfPassword;
  private JButton btnLogin;
  private JButton btnRegister;

  public LoginView() throws HeadlessException {
    setSize(300, 300);
    setLocationRelativeTo(null);
    initializeFields();
    setLayout(new BoxLayout(getContentPane(), Y_AXIS));
    add(tfUsername);
    add(tfPassword);
    add(btnLogin);
    add(btnRegister);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void initializeFields() {
   // tfClientInformation = new JTextField();
    tfUsername = new JTextField("a@gmail.com");
    tfPassword = new JTextField("Qwerty!25351&6");
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



}

package view.login;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginView extends JFrame {
  private JPanel mainPanel;
  private JTextField emailTextField;
  private JTextField passwordTextField;
  private JButton loginButton;
  private JButton registerButton;

  public LoginView() {
    setContentPane(mainPanel);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  public void setLoginButtonListener(ActionListener loginButtonListener){
    loginButton.addActionListener(loginButtonListener);
  }

  public void setRegisterButtonListener(ActionListener registerButtonListener){
    registerButton.addActionListener(registerButtonListener);
  }

  public String getEmail() {
    return emailTextField.getText();
  }

  public String getPassword() {
    return passwordTextField.getText();
  }

  public void clearFields() {
    emailTextField.setText("");
    passwordTextField.setText("");
  }

  public void showMessage(String message, int type) {
    showMessageDialog(this, message, "Error", type);
  }

}

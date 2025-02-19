package controller;

import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {

  private final LoginView loginView;
  private final AuthenticationService authenticationService;
  private final UserValidator userValidator;
  private final EmployeeView employeeView;

  public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator, EmployeeView employeeView) {
    this.loginView = loginView;
    this.authenticationService = authenticationService;
    this.userValidator = userValidator;
    this.employeeView =employeeView;

    this.loginView.addLoginButtonListener(new LoginButtonListener());
    this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    this.loginView.setVisible(true);
  }

  private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      if(authenticationService.login(username, password)==null){
        JOptionPane.showMessageDialog(loginView.getContentPane(),"Invalid credentials");
      }
      else {employeeView.setVisible(true);
      loginView.setVisible(false);}
    }
  }

  private class RegisterButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      userValidator.validate(username, password);
      final List<String> errors = userValidator.getErrors();
      if (errors.isEmpty()) {
        authenticationService.register(username, password);
      } else {
        JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
      }
    }
  }
}

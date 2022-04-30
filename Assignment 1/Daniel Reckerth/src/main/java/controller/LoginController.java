package controller;

import database.enums.RoleType;
import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;
import view.admin.AdminView;
import view.employee.EmployeeView;
import view.login.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
  private final LoginView loginView;
  private final AdminView adminView;
  private final EmployeeView employeeView;
  private final AuthenticationService authenticationService;
  private final RightsRolesRepository rightsRolesRepository;
  private User loggedInUser = null;

  public LoginController(LoginView loginView, AdminView adminView, EmployeeView employeeController, AuthenticationService authenticationService, RightsRolesRepository rightsRolesRepository) {
    this.loginView = loginView;
    this.adminView = adminView;
    this.employeeView = employeeController;
    this.authenticationService = authenticationService;
    this.rightsRolesRepository = rightsRolesRepository;
    loginView.setLoginButtonListener(new LoginButtonListener());
    loginView.setRegisterButtonListener(new RegisterButtonListener());
  }

  private class LoginButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getEmail();
      String password = loginView.getPassword();

      Notification<User> loginNotification = authenticationService.login(username, password);
      if(loginNotification.hasErrors()){
        loginView.showMessage(loginNotification.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
      } else {
        loginView.showMessage("Login successful", JOptionPane.INFORMATION_MESSAGE);
        loggedInUser = loginNotification.getResult();
        loginView.setVisible(false);

        if(loggedInUser.getRoles().get(0).getRole() == RoleType.ADMINISTRATOR){
          adminView.setVisible(true);
        } else {
          employeeView.setVisible(true);
        }
      }
    }
  }

  private class RegisterButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getEmail();
      String password = loginView.getPassword();

      Notification<Boolean> registerNotification = authenticationService.register(username, password);
      if(registerNotification.hasErrors()){
        loginView.showMessage(registerNotification.getFormattedErrors(), JOptionPane.ERROR_MESSAGE);
      } else {
        if(!registerNotification.getResult()) {
          loginView.showMessage("Registration failed", JOptionPane.WARNING_MESSAGE);
        } else {
          loginView.showMessage("Registration successful", JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }

  public User getLoggedInUser() {
    return loggedInUser;
  }
}

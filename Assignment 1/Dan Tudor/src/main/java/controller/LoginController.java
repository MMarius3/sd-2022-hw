package controller;

import database.DBConnectionFactory;
import model.User;
import model.validation.Notification;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import view.AdminView;
import view.LoginView;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginController {
  private final LoginView loginView;
  private final AuthenticationService authenticationService;
  private final ClientService clientService;
  private final UserService userService;

  public LoginController(LoginView loginView, AuthenticationService authenticationService, ClientService clientService, UserService userService) {
    this.loginView = loginView;
    this.authenticationService = authenticationService;
    this.clientService = clientService;
    this.userService = userService;
    loginView.setLoginButtonListener(new LoginButtonListener());
    loginView.setRegisterButtonListener(new RegisterButtonListener());
  }

  private class LoginButtonListener implements ActionListener {
    private final UserView userView = new UserView();
    private final UserController userController = new UserController(userView,clientService);
    private final AdminView adminView = new AdminView();
    private final AdminController adminController = new AdminController(adminView,userService);
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      Notification<User> loginNotification = authenticationService.login(username, password);

      if (loginNotification.hasErrors()) {
        JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
      } else {
        JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
        if(username.compareTo("admin@gmail.com") != 0){

          userView.setVisible();
        }
        else{
          adminView.setVisible();
        }
      }

    }
  }

  private class RegisterButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      Notification<Boolean> registerNotification = authenticationService.register(username, password);

      if (registerNotification.hasErrors()) {
        JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
      } else {
        if (!registerNotification.getResult()) {
          JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again " +
              "later.");
        } else {
          JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
        }
      }
    }
  }
}

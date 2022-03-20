package controller;

import database.Constants;
import launcher.ComponentFactory;
import model.User;
import helpers.validation.Notification;
import service.logger.LoggerService;
import service.user.AuthenticationService;
import view.LoginViewNew;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
  private final LoginViewNew loginView;
  private final AuthenticationService authenticationService;
  private final LoggerService loggerService;
  private final ComponentFactory factory;
  private User loggedUser;

  public LoginController(LoginViewNew loginView, AuthenticationService authenticationService,
                         LoggerService loggerService, ComponentFactory factory) {
    this.loginView = loginView;
    this.authenticationService = authenticationService;
    this.loggerService = loggerService;
    this.factory = factory;
    loginView.setLoginButtonListener(new LoginButtonListener());
    loginView.setRegisterButtonListener(new RegisterButtonListener());
  }

  private class LoginButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      Notification<User> loginNotification = authenticationService.login(username, password);

      if (loginNotification.hasErrors()) {
        JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
      } else {
//        JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
        loggedUser = loginNotification.getResult();
        int roleIndex = 0;
        if(loggedUser.getRoles().size() > 1) {
          String[] buttons = new String[loggedUser.getRoles().size()];
          for (int i = 0; i < loggedUser.getRoles().size(); i++) {
            buttons[i] = loggedUser.getRoles().get(i).getRole();
          }



          roleIndex = JOptionPane.showOptionDialog(loginView.getContentPane(),
                  "Which role would you prefer?",
                  "Choose role",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  buttons,
                  buttons[0]);
        }
        System.out.println("Logged in " + loggedUser.toString() + " as " + loggedUser.getRoles().get(roleIndex));
        loginView.setVisible(false);
        switch (loggedUser.getRoles().get(roleIndex).getRole()) {
          case Constants.Roles
                  .ADMINISTRATOR:
            factory.getAdminView().setVisible(true);
            factory.getAdminViewController().setLoggedUser(loggedUser);
            break;
          case Constants.Roles.EMPLOYEE:
            factory.getUserView().setVisible(true);
            factory.getUserViewController().setLoggedUser(loggedUser);
            break;
          case Constants.Roles.CUSTOMER:
          default:
            JOptionPane.showMessageDialog(loginView.getContentPane(), "View not configured for " +
                    "this type of user");
        }



//        factory.getUserView().setVisible(true);
//        factory.getUserViewController().setLoggedUser(loggedUser);

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
          loggerService.addUserLog(username);
        }
      }
    }
  }


}

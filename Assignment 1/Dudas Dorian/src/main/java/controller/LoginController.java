package controller;

import database.Constants;
import model.Role;
import model.User;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginController {

  private final LoginView loginView;
  private final AuthenticationService authenticationService;
//  private final UserValidator userValidator;
  private final AdminController adminController;
  private final EmployeeController employeeController;


  public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminController adminController, EmployeeController employeeController) {
    this.loginView = loginView;
    this.authenticationService = authenticationService;
    this.adminController = adminController;
    this.adminController.setLoginController(this);
    this.employeeController = employeeController;
    this.employeeController.setLoginController(this);
//    this.userValidator = userValidator;

    this.loginView.addLoginButtonListener(new LoginButtonListener());
//    this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    this.loginView.setVisible(true);
  }

  private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      User user = authenticationService.login(username, password);

      if(user == null){
        JOptionPane.showMessageDialog(loginView.getContentPane(), "User does not exist");
      }
      else{
        boolean isAdmin = user.getRoles().stream()
                .map(Role::getRole)
                .anyMatch(x -> x.equals(Constants.Roles.ADMINISTRATOR));
        if(isAdmin){
          // go to admin view
          adminController.getAdminView().setVisible(true);
          loginView.setVisible(false);
        }
        else{
          // go to employee view
          employeeController.setLoggedInUser(Optional.of(user));
          employeeController.getEmployeeView().setVisible(true);
          loginView.setVisible(false);
        }
      }
    }
  }

  public LoginView getLoginView() {
    return loginView;
  }

  //  private class RegisterButtonListener implements ActionListener {
//    @Override
//    public void actionPerformed(ActionEvent e) {
//      String username = loginView.getUsername();
//      String password = loginView.getPassword();
//
//      userValidator.validate(username, password);
//      final List<String> errors = userValidator.getErrors();
//      if (errors.isEmpty()) {
//        authenticationService.register(username, password);
//      } else {
//        JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
//      }
//    }
//  }
}

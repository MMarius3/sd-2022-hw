package presentation_layer.controllers;

import bussiness_layer.service.authentication.AuthenticationService;
import bussiness_layer.models.User;
import presentation_layer.view.AdminView;
import presentation_layer.view.EmployeeView;
import presentation_layer.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

  private final LoginView loginView;
  private final EmployeeView employeeView;
  private final AdminView adminView;
  private final AuthenticationService authenticationService;
//  private final UserValidator userValidator;


  public LoginController(LoginView loginView, AuthenticationService authenticationService, EmployeeView employeeView, AdminView adminView) {
    this.loginView = loginView;
    this.employeeView = employeeView;
    this.authenticationService = authenticationService;
    this.adminView = adminView;
//    this.userValidator = userValidator;

    this.loginView.addLoginButtonListener(new LoginButtonListener());
//    this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    this.loginView.setVisible(true);
    this.employeeView.setVisible(false);
    this.adminView.setVisible(false);
  }

  private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      User currentUser = authenticationService.login(username, password);
      if (currentUser != null)
      {
        loginView.setVisible(false);
        employeeView.setVisible(true);
        adminView.setVisible(false);

        if (currentUser.getRoles().get(0).getRole().equals("employee"))
        {
          employeeView.setVisible(true);
          adminView.setVisible(false);
        }
       else
        {
          adminView.setVisible(true);
          employeeView.setVisible(false);
        }
      }
    }
  }
}

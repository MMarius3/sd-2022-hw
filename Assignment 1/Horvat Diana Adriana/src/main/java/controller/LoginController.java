package controller;

import model.Sentinel;
import model.User;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import respository.account.AccountRepository;
import respository.account.AccountRepositoryMySQL;
import respository.activity.ActivityRepository;
import respository.activity.ActivityRepositoryMySQL;
import respository.client.ClientRepository;
import respository.client.ClientRepositoryMySQL;
import respository.security.RolesRepository;
import respository.security.RolesRepositoryMySQL;
import respository.user.UserRepository;
import respository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.LoginView;
import view.admin.AdminAddEmployeeView;
import view.admin.AdminIndexView;
import view.admin.AdminUpdateEmployeeView;
import view.employee.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final Connection connection;

    private final EmployeeIndexView employeeIndexView;
    private final AdminIndexView adminIndexView;

    public LoginController(AdminIndexView adminIndexView, EmployeeIndexView employeeIndexView, LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator, Connection connection) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.connection = connection;
        this.employeeIndexView = employeeIndexView;
        this.adminIndexView = adminIndexView;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authenticationService.login(username, password);
            if(user == null){
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Incorrect credentials");
            }else{
                System.out.println(user.getId());
                if(user.getRoles().get(0).getRole().equals("employee")){
                    Sentinel sentinel = new Sentinel(user);
                    authenticationService.setSentinel(sentinel);
                    employeeIndexView.setVisible(true);

                } else{
                    adminIndexView.setVisible(true);
                }
            }
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
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Registered successfully");
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
}

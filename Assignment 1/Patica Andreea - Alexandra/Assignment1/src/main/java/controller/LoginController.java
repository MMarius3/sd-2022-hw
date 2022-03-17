package controller;

import javafx.event.EventHandler;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.ErrorView;
import view.LoginView;

import javax.swing.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.List;

public class LoginController {

    private  LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator) {
        //this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        //this.loginView.addLoginButtonListener(new LoginButtonListener());
        //this.loginView.initializeFields();
        //this.loginView.addRegisterButtonListener(handleRegisterListener());
        //this.loginView.setController(this);
        //this.loginView.launchGUI();

        //this.loginView.initializeButtonAction();
        //this.loginView.addRegisterButtonListener(handleRegisterListener());

    }

   /*private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            authenticationService.login(username, password);
        }
    }*/

    public EventHandler<ActionEvent> handleLoginListener(){
        return e -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            authenticationService.login(username, password);
        };
    }

    public void setLoginView(LoginView loginView){
        this.loginView = loginView;
    }

    public EventHandler<ActionEvent>  handleRegisterListener() {

        return e -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                new ErrorView().display( userValidator.getFormattedErrors());
                //JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        };
    }
}

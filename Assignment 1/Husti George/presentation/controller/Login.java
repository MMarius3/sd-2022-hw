package presentation.controller;

import service.login.SignIn;
import service.login.SignUp;
import service.login.exception.InvalidUsernameOrPasswordException;

import java.awt.event.ActionListener;

public class Login {

    private presentation.view.Login loginView;

    public Login() {
        loginView = new presentation.view.Login();
        loginView.setActionListenerToSignInButton(new SignInButtonListener());
        loginView.setActionListenerToSignUpButton(new SignUpButtonListener());
        loginView.setVisible(true);
    }

    private class SignInButtonListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            service.login.Login signInService =
                    new SignIn()
                            .setUsername(loginView.getUsername())
                            .setPassword(loginView.getPassword());

            try {
                signInService.login();
                loginView.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class SignUpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            service.login.Login signUpService =
                    new SignUp()
                            .setUsername(loginView.getUsername())
                            .setPassword(loginView.getPassword());

            if (!signUpService.validateSize()) {
                loginView.setAlert("Username and password must be at least 5 characters long");
            }

            try {
                signUpService.login();
                loginView.setVisible(false);
            } catch (InvalidUsernameOrPasswordException ex) {
                loginView.setAlert(ex.getMessage());
            }
        }
    }
}

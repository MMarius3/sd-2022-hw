package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static database.Constants.Roles.EMPLOYEE;

public class LogInController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextArea showErrors;

    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final RightsRolesRepository rightsRolesRepository;

    public LogInController(AuthenticationService authenticationService, UserValidator userValidator, RightsRolesRepository rightsRolesRepository) {
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleLogInButton(ActionEvent event) throws IOException {
        if(!username.getText().isEmpty()) {
            if(!password.getText().isEmpty()) {
                String usernameString = username.getText();
                String passwordString = password.getText();

                User user = authenticationService.login(usernameString, passwordString);
                List<Role> role = rightsRolesRepository.findRolesForUser(user.getId());
                if (role.get(0).getRole().equals(EMPLOYEE)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Employee.fxml"));
                    EmployeeController controller = new EmployeeController(user.getUsername());
                    loader.setController(controller);
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Employee");
                    stage.show();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Administrator");
                    stage.show();
                }
            }
            else{
                showErrors.setText("Introduce password!");
            }
        }
        else{
            showErrors.setText("Introduce mail!");
        }
    }

    @FXML
    private void handleRegisterButton(){
        if(!username.getText().isEmpty()) {
            if(!password.getText().isEmpty()) {
                String usernameString = username.getText();
                String passwordString = password.getText();

                userValidator.validate(usernameString, passwordString);
                final List<String> errors = userValidator.getErrors();
                if (errors.isEmpty()) {
                    authenticationService.register(usernameString, passwordString);
                    showErrors.setText("Successfully!\n Please log in!");
                } else {
                    showErrors.setText(userValidator.getFormattedErrors());
                }
            }
            else{
                showErrors.setText("Introduce password!");
            }
        }
        else{
            showErrors.setText("Introduce mail!");
        }
    }
}

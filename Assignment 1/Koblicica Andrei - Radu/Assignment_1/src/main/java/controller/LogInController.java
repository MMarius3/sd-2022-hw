package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.TransactionValidator;
import model.validator.UserValidator;
import service.account.AccountService;
import service.client.ClientService;
import service.user.AuthenticationService;
import service.user.UserService;

import java.io.IOException;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class LogInController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button logInButton;

    @FXML
    private Button signUpButton;

    private UserService userService;
    private AuthenticationService authenticationService;
    private ClientService clientService;
    private AccountService accountService;

    private AccountValidator accountValidator;
    private ClientValidator clientValidator;
    private UserValidator userValidator;
    private TransactionValidator transactionValidator;

    public void initializeScene(AuthenticationService authenticationService, UserService userService, ClientService clientService, AccountService accountService, UserValidator userValidator, ClientValidator clientValidator, AccountValidator accountValidator, TransactionValidator transactionValidator) {
        this.authenticationService=authenticationService;
        this.userService=userService;
        this.clientService=clientService;
        this.accountService=accountService;
        this.clientValidator=clientValidator;
        this.userValidator=userValidator;
        this.accountValidator=accountValidator;
        this.transactionValidator=transactionValidator;
    }


    @FXML
    private void pressLogIn(ActionEvent event) throws IOException {
        String username=usernameField.getText();
        String password=passwordField.getText();
        User user= authenticationService.login(username, password);
        if(user!=null){
            System.out.println(user.getId());
            String role= authenticationService.getUserType(user.getId());
            if(!role.equals(EMPLOYEE)&&!role.equals(ADMINISTRATOR)){
                loadAlertBox("Invalid user!");
                return;
            }
            FXMLLoader loader;
            Parent root;
            if(role.equals(EMPLOYEE)){
                loader=new FXMLLoader(getClass().getResource("/EmployeeMenu.fxml"));
                root = loader.load();
                EmployeeMenuController employeeMenuController=loader.getController();
                employeeMenuController.initializeScene(user,clientService,accountService,userService,clientValidator,accountValidator,transactionValidator);
            }else{
                loader=new FXMLLoader(getClass().getResource("/AdminMenu.fxml"));
                root = loader.load();
                AdminMenuController adminMenuController=loader.getController();
                adminMenuController.initializeScene(userService,authenticationService, userValidator);
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else{
            loadAlertBox("Incorrect username or password!");
        }



    }

    @FXML
    private void pressSignUp(ActionEvent event) throws IOException {
        String username=usernameField.getText();
        String password=passwordField.getText();
        userValidator.validate(username, password, true);
        final List<String> errors = userValidator.getErrors();
        if (errors.isEmpty()) {
            User user = authenticationService.register(username, password);
            FXMLLoader  loader=new FXMLLoader(getClass().getResource("/EmployeeMenu.fxml"));
            Parent root = loader.load();
            EmployeeMenuController employeeMenuController=loader.getController();
            employeeMenuController.initializeScene(user,clientService,accountService,userService,clientValidator,accountValidator,transactionValidator);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            loadAlertBox(userValidator.getFormattedErrors());
        }
    }

    private void loadAlertBox(String errors){
        Stage stage=new Stage();
        stage.setTitle("Error");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AlertBox.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertBoxController alertBoxController=loader.getController();
        alertBoxController.initializeLabel(errors);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }



}
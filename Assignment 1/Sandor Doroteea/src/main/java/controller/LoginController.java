package controller;

import database.JDBConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button loginB;

    @FXML
    private Button exitB;

    @FXML
    private Button registerB;

    private String username;
    private String password;

    ConnectionStuff con = new ConnectionStuff();



    public void exitButtonOnAction(javafx.event.ActionEvent event) {
        Stage stage = (Stage) exitB.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(javafx.event.ActionEvent event){
        username = usernameTF.getText();
        password = passwordTF.getText();
        if(con.getAuthenticationService().login(username, password)!=null) {
            if (username.equals("admin@yahoo.com")) {
                switchScenesAdmin();
            } else {
                switchScenesEmployee();
                con.setCurrentUserUsername(username);
            }
        }
        else{
            dialogue("Wrong credidentials!");
        }

    }

    private void switchScenesAdmin(){
        try {
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            primaryStage.setTitle("Admin View");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();;
            e.getCause();
        }
    }

    private void switchScenesEmployee(){
        try {
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));
            primaryStage.setTitle("Employee View");
            primaryStage.setScene(new Scene(root, 800, 500));
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();;
            e.getCause();
        }
    }

    public void registerButtonOnAction(javafx.event.ActionEvent event){
        username = usernameTF.getText();
        password = passwordTF.getText();
        con.getUserValidator().validate(username, password);
        final List<String> errors = con.getUserValidator().getErrors();
        if (errors.isEmpty()) {
            con.getAuthenticationService().register(username, password);

        } else {
            dialogue(con.getUserValidator().getFormattedErrors());
        }

    }

    private void dialogue(String information){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

}
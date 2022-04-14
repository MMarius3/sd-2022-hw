package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {

    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button loginButton;
    private Button registerButton;
    private TextField username;
    private TextField password;
    private Text login;
    private Text usernameText;
    private Text passwordText;

    public LoginView() {
        mainPage = new GridPane();
        scene = new Scene(mainPage, 400, 600);
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        username = new TextField();
        password = new TextField();
        login = new Text("Login");
        usernameText = new Text("Username");
        passwordText = new Text("Password");
        setUpView();
    }

    void setUpView() {
        mainPage.prefHeight(400.0);
        mainPage.prefWidth(600.0);
        loginButton.setLayoutX(107);
        loginButton.setLayoutY(280);
        registerButton.setLayoutX(424);
        registerButton.setLayoutY(280);
        username.setLayoutX(86);
        username.setLayoutY(137);
        password.setLayoutX(137);
        password.setLayoutY(137);
        login.setLayoutX(241);
        login.setLayoutY(52);
        mainPage.add(login, 3, 0);
        mainPage.add(usernameText,1,4);
        mainPage.add(passwordText,1,6);
        mainPage.add(username, 3, 4);
        mainPage.add(password, 3, 6);
        mainPage.add(loginButton, 1, 8);
        mainPage.add(registerButton, 1, 10);
    }

    public void setScene(Scene scene){
        this.primaryStage.setScene(scene);
    }

    public Scene getScene(){
        return scene;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void clearFields(){
        username.clear();
        password.clear();
    }
}

package View;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
public class LoginView extends Pane {

    private Button login;
    private Button register;
    private TextField username;
    private TextField password;

    public LoginView(){
        setPrefSize(300,300);
        initializeFields();
        username.setPrefSize(100,20);
        username.setLayoutX(20);
        username.setLayoutY(20);
        password.setPrefSize(100,20);
        password.setLayoutX(20);
        password.setLayoutY(100);
        getChildren().add(username);
        getChildren().add(password);
        login.setLayoutX(20);
        login.setLayoutY(200);
        getChildren().add(login);
        register.setLayoutX(20);
        register.setLayoutY(240);
        getChildren().add(register);
    }

    public Button getRegister() {
        return register;
    }

    public Button getLogin() {
        return login;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    private void initializeFields(){
        login = new Button("Login");
        username = new TextField();
        password = new TextField();
        username.setPromptText("username");
        password.setPromptText("password");
        register = new Button("Register");
    }
}

package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class LoginViewFX extends Pane {
    private TextField username;
    private TextField password;
    private Button login;
    private Button register;

    public LoginViewFX(){
        initFields();
    }

    private void initFields(){
        username = new TextField();
        username.setPromptText("username");
        username.setLayoutX(60);
        username.setLayoutY(50);

        password = new TextField();
        password.setPromptText("password");
        password.setLayoutX(60);
        password.setLayoutY(100);

        login = new Button("Login");
        login.setLayoutX(105);
        login.setLayoutY(150);
        login.setFont(Font.font("Times New Roman",14));
        login.setStyle("-fx-base: cyan;");

        register = new Button("register");
        register.setLayoutX(105);
        register.setLayoutY(200);
        register.setFont(Font.font("Times New Roman",14));
        register.setStyle("-fx-base: cyan;");

        this.getChildren().addAll(username,password,login,register);
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public Button getLogin() {
        return login;
    }

    public void setLogin(Button login) {
        this.login = login;
    }

    public Button getRegister() {
        return register;
    }

    public void setRegister(Button register) {
        this.register = register;
    }
}

package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView {

    private TextField tfUsername; // = new TextField();
    private TextField tfPassword;// = new TextField();
    private Button btnLogin;// = new Button("Login");
    private Button btnRegister;// = new Button("Register");
    private GridPane gridMainScene;
    private static LoginController controller;


    public Scene display(Stage window)  {
        window.setTitle("Bank App");


        initializeFields();
        initializeGridPane();

        initializeButtonAction();


        Scene scene = new Scene(gridMainScene, 350, 250);
        return scene;

    }


    public void initializeFields() {
        tfUsername = new TextField();
        tfPassword = new TextField();
        btnLogin = new Button("Login");
        btnRegister = new Button("Register");
    }

    private void initializeGridPane(){
        gridMainScene = new GridPane();
        gridMainScene.setVgap(20);//set vertical spacing between cells
        gridMainScene.setHgap(20); //horizontal spacing

        gridMainScene.getChildren().addAll(tfUsername, tfPassword, btnLogin, btnRegister);
        gridMainScene.setVgap(8);//set vertical spacing between cells
        gridMainScene.setVgap(10); //horizontal spacing

        GridPane.setConstraints(tfUsername, 0, 0);
        GridPane.setConstraints(tfPassword, 1, 0);
        GridPane.setConstraints(btnLogin, 0, 1);
        GridPane.setConstraints(btnRegister, 1, 1);
    }

    public void initializeButtonAction(){
        controller.setLoginView(this);
        btnRegister.setOnAction(controller.handleRegisterListener());
        btnLogin.setOnAction(controller.handleLoginListener());
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    /*public void addLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }*/

    public void addRegisterButtonListener(EventHandler<ActionEvent> registerButtonListener) {
        btnRegister.setOnAction(registerButtonListener);
    }

    public Button getBtnRegister(){
        return this.btnRegister;
    }

    public static void setController (LoginController cntrl){
        controller = cntrl;
    }

}

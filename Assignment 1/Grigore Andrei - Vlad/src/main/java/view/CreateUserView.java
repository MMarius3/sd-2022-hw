package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Client;
import model.User;

import java.lang.reflect.Field;

public class CreateUserView extends Pane {
    private Button submit;
    private TextField username;
    private TextField password;
    private Button update;
    private ComboBox<String> category;


    public CreateUserView() {
        setPrefSize(200,250);
        initializeFields();
    }

    private void initializeFields(){
        submit = new Button("Submit");
        submit.setLayoutX(65);
        submit.setLayoutY(200);
        submit.setFont(Font.font("Times New Roman",14));
        submit.setStyle("-fx-base: cyan;");

        update = new Button("Update");
        update.setLayoutX(145);
        update.setLayoutY(200);
        update.setFont(Font.font("Times New Roman",14));
        update.setStyle("-fx-base: cyan;");

        username = new TextField();
        username.setPromptText("username");
        username.setLayoutX(60);
        username.setLayoutY(50);

        password = new TextField();
        password.setPromptText("password");
        password.setLayoutX(60);
        password.setLayoutY(100);

        category = new ComboBox<>();
        category.setPromptText("Category");
        category.setLayoutX(60);
        category.setLayoutY(10);

        for(Field field: (new User()).getClass().getDeclaredFields()){
            category.getItems().add(field.getName());
        }

        this.getChildren().addAll(update,category,submit,username,password);
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
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

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public ComboBox<String> getCategory() {
        return category;
    }

    public void setCategory(ComboBox<String> category) {
        this.category = category;
    }
}

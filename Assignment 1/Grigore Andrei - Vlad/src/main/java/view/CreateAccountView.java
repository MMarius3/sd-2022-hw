package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Account;
import model.Client;

import java.lang.reflect.Field;


public class CreateAccountView extends Pane {
    private TextField clientId;
    private TextField type;
    private TextField balance;
    private Button submit;
    private ComboBox<String> category;
    private Button update;


    public CreateAccountView(){
        initializeFields();
    }

    private void initializeFields(){
        submit = new Button("Submit");
        submit.setLayoutX(65);
        submit.setLayoutY(250);
        submit.setFont(Font.font("Times New Roman",14));
        submit.setStyle("-fx-base: cyan;");

        update = new Button("Update");
        update.setLayoutX(145);
        update.setLayoutY(250);
        update.setFont(Font.font("Times New Roman",14));
        update.setStyle("-fx-base: cyan;");

        clientId = new TextField();
        clientId.setPromptText("clientId");
        clientId.setLayoutX(60);
        clientId.setLayoutY(50);

        type = new TextField();
        type.setPromptText("type");
        type.setLayoutX(60);
        type.setLayoutY(100);

        balance= new TextField();
        balance.setPromptText("balance");
        balance.setLayoutX(60);
        balance.setLayoutY(150);

        category = new ComboBox<>();
        category.setPromptText("Category");
        category.setLayoutX(60);
        category.setLayoutY(10);

        for(Field field: (new Account()).getClass().getDeclaredFields()){
            category.getItems().add(field.getName());
        }

        this.getChildren().addAll(category,update,submit,clientId,type,balance);
    }

    public TextField getClientId() {
        return clientId;
    }

    public void setClientId(TextField clientId) {
        this.clientId = clientId;
    }

    public TextField getType() {
        return type;
    }

    public void setType(TextField type) {
        this.type = type;
    }

    public TextField getBalance() {
        return balance;
    }

    public void setBalance(TextField balance) {
        this.balance = balance;
    }

    public Button getSubmit() {
        return submit;
    }

    public ComboBox<String> getCategory() {
        return category;
    }

    public void setCategory(ComboBox<String> category) {
        this.category = category;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }
}

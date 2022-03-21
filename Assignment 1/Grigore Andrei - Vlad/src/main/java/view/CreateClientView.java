package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Client;

import java.lang.reflect.Field;

public class CreateClientView extends Pane {

    private Button submit;
    private Button update;
    private ComboBox<String> category;
    private TextField name;
    private TextField cardId;
    private TextField personalNumericalCode;
    private TextField address;

    public CreateClientView() {
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

        name = new TextField();
        name.setPromptText("name");
        name.setLayoutX(60);
        name.setLayoutY(50);

        cardId = new TextField();
        cardId.setPromptText("cardId");
        cardId.setLayoutX(60);
        cardId.setLayoutY(100);

        personalNumericalCode= new TextField();
        personalNumericalCode.setPromptText("personalNumericalCode");
        personalNumericalCode.setLayoutX(60);
        personalNumericalCode.setLayoutY(150);

        address = new TextField();
        address.setPromptText("address");
        address.setLayoutX(60);
        address.setLayoutY(200);

        category = new ComboBox<>();
        category.setPromptText("Category");
        category.setLayoutX(60);
        category.setLayoutY(10);

        for(Field field: (new Client()).getClass().getDeclaredFields()){
            category.getItems().add(field.getName());
        }


        this.getChildren().addAll(category,update,submit,name,cardId,address,personalNumericalCode);
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getCardId() {
        return cardId;
    }

    public void setCardId(TextField cardId) {
        this.cardId = cardId;
    }

    public TextField getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(TextField personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }

    public TextField getAddress() {
        return address;
    }

    public void setAddress(TextField address) {
        this.address = address;
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
}

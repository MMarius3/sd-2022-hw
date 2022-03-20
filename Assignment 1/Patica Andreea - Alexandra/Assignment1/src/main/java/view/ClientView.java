package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ClientView {

    private Text name;
    private Text idNumber;
    private Text personalIdentificationCode;
    private Text address;
    private Text accounts;

    private TextField nameField;
    private TextField idNumberField;
    private TextField personalIdentificationCodeField;
    private TextField addressField;

    private Button saveButton;
    private Button addAccountButton;

    private GridPane gridPane;
    private ScrollPane scrollPane;
    private HBox hBox;
    private ChoiceBox<String> accountsBox;

    public void display(){
        Stage window = new Stage();
        window.setTitle("Client");

        //initializeFields();
        initializeGridPane();
        initializeHbox();
        initializeButtonListener();


        window.setScene(new Scene(hBox, 600, 500));
        window.setResizable(false);
        window.showAndWait();
    }

    public void initializeFields(){
        name = new Text("Name");
        idNumber = new Text("Id Number");
        personalIdentificationCode = new Text("Personal Identification Code");
        address = new Text("Address");
        accounts = new Text("Accounts");

        nameField = new TextField();
        idNumberField = new TextField();
        personalIdentificationCodeField = new TextField();
        addressField = new TextField();

        saveButton = new Button("Save");
        addAccountButton = new Button("Add Account");

        scrollPane = new ScrollPane();
        scrollPane.setPrefViewportWidth(200);
        scrollPane.setPrefViewportHeight(100);
    }

    private void initializeGridPane(){
        gridPane = new GridPane();

        gridPane.setPadding( new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.getChildren().addAll(name, idNumber, personalIdentificationCode, address, accounts,
                nameField, idNumberField, personalIdentificationCodeField, addressField,
                saveButton, addAccountButton);

        GridPane.setConstraints(idNumber, 0, 1);
        GridPane.setConstraints(idNumberField, 1, 1);
        GridPane.setConstraints(name, 0, 2);
        GridPane.setConstraints(nameField, 1, 2);
        GridPane.setConstraints(personalIdentificationCode, 0, 3);
        GridPane.setConstraints(personalIdentificationCodeField, 1, 3);
        GridPane.setConstraints(address, 0, 4);
        GridPane.setConstraints(addressField, 1, 4);
        GridPane.setConstraints(saveButton, 1, 5);
        GridPane.setConstraints(accounts, 0, 6);
        GridPane.setConstraints(addAccountButton, 1, 6);
    }

    private void initializeHbox(){
        hBox = new HBox();

        hBox.setPadding(new Insets(20, 20, 20, 20));

        hBox.getChildren().addAll(gridPane, scrollPane);
    }

    public void refreshScrollPane(List<Button> buttons){
        scrollPane.setContent(null);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttons);
        scrollPane.setContent(vBox);
    }



    private void initializeButtonListener(){
        //saveButton.setOnAction();             //TODO
        //addAccountButton.setOnAction();
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(String text) {
        this.nameField.setText(text);
    }

    public TextField getIdNumberField() {
        return idNumberField;
    }

    public void setIdNumberField(String text) {
        this.idNumberField.setText(text);
    }

    public TextField getPersonalIdentificationCodeField() {
        return personalIdentificationCodeField;
    }

    public void setPersonalIdentificationCodeField(String text) {
        this.personalIdentificationCodeField.setText(text);
    }

    public TextField getAddressField() {
        return addressField;
    }

    public void setAddressField(String text) {
        this.addressField.setText(text);
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getAddAccountButton() {
        return addAccountButton;
    }

    public void setAddAccountButton(Button addAccountButton) {
        this.addAccountButton = addAccountButton;
    }
}

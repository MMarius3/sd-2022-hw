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

        initializeFields();
        initializeGridPane();
        initializeHbox();
        initializeButtonListener();


        window.setScene(new Scene(gridPane, 500, 500));
        window.setResizable(false);
        window.showAndWait();
    }

    private void initializeFields(){
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


    }


    private void initializeButtonListener(){
        //saveButton.setOnAction();             //TODO
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public TextField getIdNumberField() {
        return idNumberField;
    }

    public void setIdNumberField(TextField idNumberField) {
        this.idNumberField = idNumberField;
    }

    public TextField getPersonalIdentificationCodeField() {
        return personalIdentificationCodeField;
    }

    public void setPersonalIdentificationCodeField(TextField personalIdentificationCodeField) {
        this.personalIdentificationCodeField = personalIdentificationCodeField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public void setAddressField(TextField addressField) {
        this.addressField = addressField;
    }
}

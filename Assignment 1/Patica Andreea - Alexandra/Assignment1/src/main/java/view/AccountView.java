package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccountView {
    private Text id;
    private Text type;
    private Text balance;
    private Text creationDate;

    private TextField idField;
    private TextField typeField;
    private TextField balanceField;
    private TextField creationDateField;

    private Button saveButton;
    private Button deleteButton;

    private GridPane gridPane;

    public void display(){
        Stage window = new Stage();
        window.setTitle("Account");

        initializeFields();
        initializeGridPane();

        window.setScene(new Scene(gridPane, 400, 400));
        window.setResizable(false);
        window.showAndWait();
    }

    private void initializeFields(){
        id = new Text("Account id");
        type = new Text("Account type");
        balance = new Text("Balance");
        creationDate = new Text("Creation date");

        idField = new TextField();
        typeField = new TextField();
        balanceField = new TextField();
        creationDateField = new TextField();

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");
    }

    private void initializeGridPane(){
        gridPane = new GridPane();

        gridPane.setPadding( new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.getChildren().addAll(id, type, balance, creationDate,
                idField, typeField, balanceField, creationDateField,
                saveButton, deleteButton);

        GridPane.setConstraints(id, 0, 1);
        GridPane.setConstraints(idField, 1, 1);
        GridPane.setConstraints(type, 0, 2);
        GridPane.setConstraints(type, 1, 2);
        GridPane.setConstraints(balance, 0, 3);
        GridPane.setConstraints(balanceField, 1, 3);
        GridPane.setConstraints(creationDate, 0, 4);
        GridPane.setConstraints(creationDateField, 1, 4);
        GridPane.setConstraints(saveButton, 1, 5);
        GridPane.setConstraints(deleteButton, 1, 6);
    }


    public TextField getIdField() {
        return idField;
    }

    public void setIdField(TextField idField) {
        this.idField = idField;
    }

    public TextField getTypeField() {
        return typeField;
    }

    public void setTypeField(TextField typeField) {
        this.typeField = typeField;
    }

    public TextField getBalanceField() {
        return balanceField;
    }

    public void setBalanceField(TextField balanceField) {
        this.balanceField = balanceField;
    }

    public TextField getCreationDateField() {
        return creationDateField;
    }

    public void setCreationDateField(TextField creationDateField) {
        this.creationDateField = creationDateField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}

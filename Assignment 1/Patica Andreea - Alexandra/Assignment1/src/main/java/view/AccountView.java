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
    private Text payBill;

    private TextField idField;
    private TextField typeField;
    private TextField balanceField;
    private TextField creationDateField;
    private TextField payBillField;

    private Button saveButton;
    private Button deleteButton;
    private Button payBillButton;

    private GridPane gridPane;

    public void display(){
        Stage window = new Stage();
        window.setTitle("Account");

        //initializeFields();
        initializeGridPane();

        window.setScene(new Scene(gridPane, 400, 400));
        window.setResizable(false);
        window.showAndWait();
    }

    public void initializeFields(){
        id = new Text("Account id");
        type = new Text("Account type");
        balance = new Text("Balance");
        creationDate = new Text("Creation date");
        payBill = new Text("Pay bill");

        idField = new TextField();
        typeField = new TextField();
        balanceField = new TextField();
        creationDateField = new TextField();
        payBillField = new TextField();

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");
        payBillButton = new Button("Pay bill");
    }

    private void initializeGridPane(){
        gridPane = new GridPane();

        gridPane.setPadding( new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.getChildren().addAll(id, type, balance, creationDate,
                idField, typeField, balanceField, creationDateField,
                saveButton, deleteButton, payBill, payBillField, payBillButton);

        GridPane.setConstraints(id, 0, 1);
        GridPane.setConstraints(idField, 1, 1);
        GridPane.setConstraints(type, 0, 2);
        GridPane.setConstraints(typeField, 1, 2);
        GridPane.setConstraints(balance, 0, 3);
        GridPane.setConstraints(balanceField, 1, 3);
        GridPane.setConstraints(creationDate, 0, 4);
        GridPane.setConstraints(creationDateField, 1, 4);
        GridPane.setConstraints(saveButton, 1, 5);
        GridPane.setConstraints(deleteButton, 1, 6);
        GridPane.setConstraints(payBill, 0, 7);
        GridPane.setConstraints(payBillField, 1, 7);
        GridPane.setConstraints(payBillButton, 1, 8);
    }


    public String getIdField() {
        return idField.getText();
    }

    public void setIdField(String text) {
        this.idField.setText(text);
    }

    public String getTypeField() {
        return typeField.getText();
    }

    public void setTypeField(String text) {
        this.typeField.setText(text);
    }

    public String getBalanceField() {
        return balanceField.getText();
    }

    public void setBalanceField(String text) {
        this.balanceField.setText(text);
    }

    public String getCreationDateField() {
        return creationDateField.getText();
    }

    public void setCreationDateField(String text) {
        this.creationDateField.setText(text);
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

    public String getPayBillField() {
        return payBillField.getText();
    }



    public Button getPayBillButton() {
        return payBillButton;
    }

    public void setPayBillButton(Button payBillButton) {
        this.payBillButton = payBillButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}

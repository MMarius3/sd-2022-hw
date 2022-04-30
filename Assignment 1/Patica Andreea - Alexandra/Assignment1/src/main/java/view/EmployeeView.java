package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class EmployeeView {

    private Text id;
    private Text username;
    private Text password;
    private Text repeatPassword;
    private Text roles;
    private Text report;

    private TextField idField;
    private TextField usernameField;
    private TextField passwordField;
    private TextField repeatPasswordField;

    private TextArea reportArea;

    private ScrollPane rolesPane;

    private Button saveButton;
    private Button deleteButton;



    private GridPane gridPane;


    public void display(){
        Stage window = new Stage();
        window.setTitle("Account");

        //initializeFields();
        initializeGridPane();

        window.setScene(new Scene(gridPane, 500, 500));
        window.setResizable(false);
        window.showAndWait();
    }

    public void initializeFields(){
        id = new Text("ID");
        username = new Text("Username");
        password = new Text("Password");
        repeatPassword = new Text("Repeat Password");
        roles = new Text("Roles");
        report = new Text("Report");

        idField = new TextField();
        usernameField = new TextField();
        passwordField = new TextField();
        repeatPasswordField = new TextField();

        reportArea = new TextArea();

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");

        rolesPane = new ScrollPane();
    }

    private void initializeGridPane(){
        gridPane = new GridPane();

        gridPane.setPadding( new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.getChildren().addAll(id, username, password, repeatPassword, roles, report,
                idField, usernameField, passwordField, repeatPasswordField,
                saveButton, deleteButton, rolesPane, reportArea);

        GridPane.setConstraints(id, 0, 1);
        GridPane.setConstraints(idField, 1, 1);
        GridPane.setConstraints(username, 0, 2);
        GridPane.setConstraints(usernameField, 1, 2);
        GridPane.setConstraints(password, 0, 3);
        GridPane.setConstraints(passwordField, 1, 3);
        GridPane.setConstraints(repeatPassword, 0, 4);
        GridPane.setConstraints(repeatPasswordField, 1, 4);
        GridPane.setConstraints(roles, 0, 5);
        GridPane.setConstraints(rolesPane, 1, 5);
        GridPane.setConstraints(report, 0, 6);
        GridPane.setConstraints(reportArea, 1, 6);
        GridPane.setConstraints(saveButton, 0, 7);
        GridPane.setConstraints(deleteButton, 1, 7);
    }

    public TextField getIdField() {
        return idField;
    }

    public void setIdField(String text) {
        this.idField.setText(text);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(String text) {
        this.usernameField.setText(text);
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String text) {
        this.passwordField.setText(text);
    }

    public TextField getRepeatPasswordField() {
        return repeatPasswordField;
    }

    public void setRepeatPasswordField(String text) {
        this.repeatPasswordField.setText(text);
    }

    public ScrollPane getRolesPane() {
        return rolesPane;
    }

    public void setRolesPane(ScrollPane rolesPane) {
        this.rolesPane = rolesPane;
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

    public TextArea getReportArea() {
        return reportArea;
    }

    public void setReportArea(TextArea reportArea) {
        this.reportArea = reportArea;
    }
}

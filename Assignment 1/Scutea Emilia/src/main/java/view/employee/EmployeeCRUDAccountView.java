package view.employee;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Account;

import java.sql.Date;
import java.time.LocalDate;

public class EmployeeCRUDAccountView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button addBtn;
    private Button updateBtn;
    private Button viewAccountBtn;
    private Button backBtn;

    private TextField type_add;
    private TextField amount_add;
    private TextField dateOfCreation_add;

    private TextField id_update;
    private TextField type_update;
    private TextField amount_update;

    private TableView<Account> table;
    private TableColumn<Account, Long> table_id;
    private TableColumn<Account, String> table_type;
    private TableColumn<Account, Double> table_amount;
    private TableColumn<Account, Date> table_dateOfCreation;

    public EmployeeCRUDAccountView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 1000, 900);
        addBtn = new Button("Add");
        updateBtn = new Button("Update");
        viewAccountBtn = new Button("View all accounts");
        backBtn = new Button("Back");
        type_add = new TextField();
        amount_add = new TextField();
        dateOfCreation_add = new TextField();
        id_update = new TextField();
        type_update = new TextField();
        amount_update = new TextField();
        table = new TableView<>();
        table_id = new TableColumn<>();
        table_type = new TableColumn<>();
        table_amount = new TableColumn<>();
        table_dateOfCreation = new TableColumn<>();

        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(650);
        mainPage.prefWidth(900);

        addBtn.setLayoutY(808);
        addBtn.setLayoutY(49);

        type_add.setLayoutX(13);
        type_add.setLayoutY(49);
        amount_add.setLayoutY(198);
        amount_add.setLayoutY(49);
        dateOfCreation_add.setLayoutX(328);
        dateOfCreation_add.setLayoutY(49);

        type_add.setPromptText("Type");
        amount_add.setPromptText("Amount");
        dateOfCreation_add.setText(String.valueOf(Date.valueOf(LocalDate.now())));

        updateBtn.setLayoutY(817);
        updateBtn.setLayoutY(129);
        id_update.setLayoutX(13);
        id_update.setLayoutY(129);
        type_update.setLayoutY(168);
        type_update.setLayoutY(129);
        amount_update.setLayoutX(328);
        amount_update.setLayoutY(129);

        id_update.setPromptText("id");
        type_update.setPromptText("Type");
        amount_update.setPromptText("Amount");

        viewAccountBtn.setLayoutX(25);
        viewAccountBtn.setLayoutY(181);
        backBtn.setLayoutX(168);
        backBtn.setLayoutY(180);

        table_amount.setText("Amount");
        table_type.setText("Type");
        table_id.setText("id");
        table_dateOfCreation.setText("Date of creation");

        table.setPrefHeight(300);
        table.setPrefWidth(500);

        table.getColumns().addAll(table_id, table_type, table_amount, table_dateOfCreation);

        mainPage.add(type_add, 1, 1);
        mainPage.add(amount_add, 3, 1);
        mainPage.add(dateOfCreation_add, 5, 1);
        mainPage.add(addBtn, 11, 1);
        mainPage.add(id_update, 1, 3);
        mainPage.add(type_update, 3, 3);
        mainPage.add(amount_update, 5, 3);
        mainPage.add(updateBtn, 11, 3);
        mainPage.add(viewAccountBtn, 1, 5);
        mainPage.add(backBtn,3, 5);
        mainPage.add(table, 1, 10);
    }

    public void setScene(Scene scene){
        this.primaryStage.setScene(scene);
    }

    public Scene getScene(){
        return scene;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public Button getUpdateBtn() {
        return updateBtn;
    }

    public Button getViewAccountBtn() {
        return viewAccountBtn;
    }

    public Button getBackBtn(){return backBtn;}

    public String getType_add() {
        return type_add.getText();
    }

    public String getAmount_add() {
        return amount_add.getText();
    }

    public String getDateOfCreation_add() {
        return dateOfCreation_add.getText();
    }

    public String getId_update() {
        return id_update.getText();
    }

    public String getType_update() {
        return type_update.getText();
    }

    public String getAmount_update() {
        return amount_update.getText();
    }

    public TableView<Account> getTable() {
        return table;
    }

    public TableColumn<Account, Long> getTable_id() {
        return table_id;
    }

    public TableColumn<Account, String> getTable_type() {
        return table_type;
    }

    public TableColumn<Account, Double> getTable_amount() {
        return table_amount;
    }

    public TableColumn<Account, Date> getTable_dateOfCreation() {
        return table_dateOfCreation;
    }
}

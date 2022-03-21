package view.employee;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Client;

public class EmployeeCRUDClientView {

    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button addBtn;
    private TextField name_add;
    private TextField cardNumber_add;
    private TextField cnp_add;
    private TextField address_add;
    private TextField accountNumber_add;
    private TextField name_update;
    private TextField cardNumber_update;
    private TextField cnp_update;
    private TextField address_update;
    private TextField accountNumber_update;
    private Button updateBtn;
    private Button viewClientBtn;
    private Button backBtn;
    private TableView<Client> table;
    private TableColumn<Client, String> table_name;
    private TableColumn<Client, Long> table_cardNumber;
    private TableColumn<Client, String> table_address;
    private TableColumn<Client, Long> table_cnp;
    private TableColumn<Client, Long> table_accountNumber;



    public EmployeeCRUDClientView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 1000, 900);
        addBtn = new Button("Add");
        updateBtn = new Button("Update");
        viewClientBtn = new Button("View all clients");
        backBtn = new Button("Back");
        name_add = new TextField();
        cardNumber_add = new TextField();
        cnp_add = new TextField();
        address_add = new TextField();
        accountNumber_add = new TextField();
        name_update = new TextField();
        cardNumber_update = new TextField();
        cnp_update = new TextField();
        address_update = new TextField();
        accountNumber_update = new TextField();
        table = new TableView<>();
        table_name = new TableColumn<>();
        table_accountNumber = new TableColumn<>();
        table_address = new TableColumn<>();
        table_cnp = new TableColumn<>();
        table_cardNumber = new TableColumn<>();

        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(650);
        mainPage.prefWidth(900);

        addBtn.setLayoutY(808);
        addBtn.setLayoutY(49);
        name_add.setLayoutX(13);
        name_add.setLayoutY(49);
        cardNumber_add.setLayoutY(198);
        cardNumber_add.setLayoutY(49);
        cnp_add.setLayoutX(328);
        cnp_add.setLayoutY(49);
        address_add.setLayoutX(482);
        address_add.setLayoutY(49);
        accountNumber_add.setLayoutX(635);
        accountNumber_add.setLayoutY(49);

        name_add.setPromptText("Name");
        cardNumber_add.setPromptText("Card number");
        cnp_add.setPromptText("CNP");
        address_add.setPromptText("Address");
        accountNumber_add.setPromptText("Account number");

        updateBtn.setLayoutY(817);
        updateBtn.setLayoutY(129);
        name_update.setLayoutX(13);
        name_update.setLayoutY(129);
        cardNumber_update.setLayoutY(168);
        cardNumber_update.setLayoutY(129);
        cnp_update.setLayoutX(328);
        cnp_update.setLayoutY(129);
        address_update.setLayoutX(644);
        address_update.setLayoutY(129);
        accountNumber_update.setLayoutX(644);
        accountNumber_update.setLayoutY(129);

        name_update.setPromptText("Name");
        cardNumber_update.setPromptText("Card number");
        cnp_update.setPromptText("CNP");
        address_update.setPromptText("Address");
        accountNumber_update.setPromptText("Account number");

        viewClientBtn.setLayoutX(25);
        viewClientBtn.setLayoutY(181);
        backBtn.setLayoutX(168);
        backBtn.setLayoutY(180);

        table_name.setText("Name");
        table_cardNumber.setText("Card number");
        table_cnp.setText("CNP");
        table_address.setText("Address");
        table_accountNumber.setText("Account number");

        table.setPrefHeight(300);
        table.setPrefWidth(500);

        table.getColumns().addAll(table_name, table_cardNumber, table_cnp, table_address, table_accountNumber);

        mainPage.add(name_add, 1, 1);
        mainPage.add(cardNumber_add, 3, 1);
        mainPage.add(cnp_add, 5, 1);
        mainPage.add(address_add, 7, 1);
        mainPage.add(accountNumber_add, 9, 1);
        mainPage.add(addBtn, 11, 1);

        mainPage.add(name_update, 1, 3);
        mainPage.add(cardNumber_update, 3, 3);
        mainPage.add(cnp_update, 5, 3);
        mainPage.add(address_update, 7, 3);
        mainPage.add(accountNumber_update, 9, 3);
        mainPage.add(updateBtn, 11, 3);
        mainPage.add(viewClientBtn, 1, 5);
        mainPage.add(backBtn,3, 5);
        mainPage.add(table, 1, 10);
    }

    public TableView<Client> getTable() {
        return table;
    }

    public TableColumn<Client, String> getTable_name() {
        return table_name;
    }

    public TableColumn<Client, Long> getTable_cardNumber() {
        return table_cardNumber;
    }

    public TableColumn<Client, String> getTable_address() {
        return table_address;
    }

    public TableColumn<Client, Long> getTable_cnp() {
        return table_cnp;
    }

    public TableColumn<Client, Long> getTable_accountNumber() {
        return table_accountNumber;
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

    public Button getAddBtn(){
        return addBtn;
    }

    public Button getUpdateBtn(){
        return updateBtn;
    }

    public Button getViewClientBtn(){
        return viewClientBtn;
    }

    public Button getBackBtn(){return backBtn;}

    public String getNameAdd(){
        return name_add.getText();
    }

    public String getCardNumberAdd(){
        return  cardNumber_add.getText();
    }

    public String getCnpAdd(){
        return  cnp_add.getText();
    }

    public String getAddressAdd(){
        return address_add.getText();
    }

    public String getAccountNumberAdd(){
        return accountNumber_add.getText();
    }

    public String getNameUpdate(){
        return name_update.getText();
    }

    public String getCardNumberUpdate(){
        return  cardNumber_update.getText();
    }

    public String getCnpUpdate(){
        return  cnp_update.getText();
    }

    public String getAddressUpdate(){
        return address_update.getText();
    }

    public String getAccountNumberUpdate(){
        return accountNumber_update.getText();
    }
}

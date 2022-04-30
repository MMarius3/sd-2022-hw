package view.administrator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Role;
import model.User;

import java.util.List;

public class AdministratorCRUDEmployeeView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button addBtn;
    private Button updateBtn;
    private Button viewEmployeesBtn;
    private Button backBtn;
    private TextField username_add;
    private TextField password_add;
    private TextField password_update;
    private TextField username_update;
    private TextField id_update;

    private TableView<User> table;
    private TableColumn<User, String> table_username;
    private TableColumn<User, Long> table_id;
    private TableColumn<User, String> table_password;
    private TableColumn<User, List<Role>> table_role;

    public AdministratorCRUDEmployeeView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 1000, 900);
        addBtn = new Button("Add");
        updateBtn = new Button("Update");
        viewEmployeesBtn = new Button("View all employees");
        backBtn = new Button("Back");
        username_add=new TextField();
        password_add=new TextField();
        username_update=new TextField();
        password_update=new TextField();
        id_update=new TextField();
        table = new TableView<>();

        table_username= new TableColumn<>();
        table_id= new TableColumn<>();
        table_password= new TableColumn<>();
        table_role= new TableColumn<>();

        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(650);
        mainPage.prefWidth(900);

        addBtn.setLayoutY(808);
        addBtn.setLayoutY(49);
        username_add.setLayoutX(13);
        username_add.setLayoutY(49);
        password_add.setLayoutY(198);
        password_add.setLayoutY(49);

        username_add.setPromptText("Username");
        password_add.setPromptText("Password");

        updateBtn.setLayoutY(817);
        updateBtn.setLayoutY(129);
        username_update.setLayoutX(13);
        username_update.setLayoutY(129);
        password_update.setLayoutY(168);
        password_update.setLayoutY(129);
        id_update.setLayoutX(328);
        id_update.setLayoutY(129);

        username_update.setPromptText("Username");
        password_update.setPromptText("Password");
        id_update.setPromptText("id");

        viewEmployeesBtn.setLayoutX(25);
        viewEmployeesBtn.setLayoutY(181);
        backBtn.setLayoutX(168);
        backBtn.setLayoutY(180);

        table_id.setText("id");
        table_username.setText("Username");
        table_password.setText("Password");
        table_role.setText("Role");

        table.setPrefHeight(300);
        table.setPrefWidth(500);

        table.getColumns().addAll(table_id, table_username, table_password, table_role);

        mainPage.add(username_add, 1, 1);
        mainPage.add(password_add, 3, 1);
        mainPage.add(addBtn, 5, 1);

        mainPage.add(username_update, 1, 3);
        mainPage.add(password_update, 3, 3);
        mainPage.add(id_update, 5, 3);
        mainPage.add(updateBtn, 7, 3);
        mainPage.add(viewEmployeesBtn, 1, 5);
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

    public Button getViewEmployeesBtn() {
        return viewEmployeesBtn;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public TableView<User> getTable() {
        return table;
    }

    public TableColumn<User, String> getTable_username() {
        return table_username;
    }

    public TableColumn<User, Long> getTable_id() {
        return table_id;
    }

    public TableColumn<User, String> getTable_password() {
        return table_password;
    }

    public TableColumn<User, List<Role>> getTable_role() {
        return table_role;
    }

    public String getUsernameAdd(){
        return  username_add.getText();
    }

    public String getPasswordAdd(){
        return  password_add.getText();
    }

    public String getUsernameUpdate(){
        return username_update.getText();
    }

    public String getPasswordUpdate(){
        return  password_update.getText();
    }

    public String getIdUpdate(){
        return id_update.getText();
    }
}

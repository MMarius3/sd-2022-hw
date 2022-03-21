package view.employee;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmployeeView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private Button crudClient;
    private Button crudAccount;
    private Button bills;
    private Button transferMoney;
    private Button backBtn;

    public EmployeeView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 400, 600);
        crudClient = new Button("CRUD on client information");
        crudAccount = new Button("CRUD on account information");
        bills = new Button("Process bills");
        transferMoney = new Button("Transfer money from an account to another");
        backBtn = new Button("Back");
        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(400.0);
        mainPage.prefWidth(600.0);
        crudClient.setLayoutY(125);
        crudClient.setLayoutY(54);
        crudAccount.setLayoutX(125);
        crudAccount.setLayoutY(110);
        bills.setLayoutX(125);
        bills.setLayoutY(188);
        transferMoney.setLayoutX(125);
        transferMoney.setLayoutY(254);
        backBtn.setLayoutY(51);
        backBtn.setLayoutY(163);
        mainPage.add(crudClient, 3, 1);
        mainPage.add(crudAccount, 3, 3);
        mainPage.add(bills, 3, 5);
        mainPage.add(transferMoney, 3, 7);
        mainPage.add(backBtn,3,9);

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

    public Button getCrudClientButton(){
        return crudClient;
    }

    public Button getCrudAccountButton() {
        return crudAccount;
    }

    public Button getBillsButton() {
        return bills;
    }

    public Button getTransferMoneyButton() {
        return transferMoney;
    }

    public Button getBackBtn(){ return  backBtn;}

}

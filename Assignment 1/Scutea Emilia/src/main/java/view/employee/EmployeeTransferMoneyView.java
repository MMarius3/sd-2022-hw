package view.employee;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmployeeTransferMoneyView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private TextField transferFromId;
    private TextField transferToId;
    private TextField amount;
    private Button transferBtn;
    private Button backBtn;

    public EmployeeTransferMoneyView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 400, 600);
        transferFromId = new TextField();
        transferToId = new TextField();
        amount = new TextField();
        transferBtn = new Button("Transfer");
        backBtn = new Button("Back");

        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(400.0);
        mainPage.prefWidth(600.0);

        transferFromId.setLayoutX(59);
        transferFromId.setLayoutY(75);
        transferToId.setLayoutX(334);
        transferToId.setLayoutY(75);
        amount.setLayoutX(72);
        amount.setLayoutY(154);
        transferBtn.setLayoutX(374);
        transferBtn.setLayoutY(154);
        backBtn.setLayoutY(80);
        backBtn.setLayoutY(268);

        transferToId.setPromptText("Transfer money to account ID");
        transferFromId.setPromptText("Transfer money from account ID");
        amount.setPromptText("Amount to transfer");

        mainPage.add(transferFromId, 1, 1);
        mainPage.add(transferToId, 3, 1);
        mainPage.add(amount, 1, 3);
        mainPage.add(transferBtn, 3,3);
        mainPage.add(backBtn,1,5);
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

    public Button getTransferBtn() {
        return transferBtn;
    }

    public Button getBackBtn(){
        return backBtn;
    }

    public String getTransferFromId(){
        return transferFromId.getText();
    }

    public String getTransferToId(){
        return transferToId.getText();
    }

    public String getAmount(){
        return amount.getText();
    }

    public void clearFields(){
        transferToId.clear();
        transferFromId.clear();
        amount.clear();
    }
}

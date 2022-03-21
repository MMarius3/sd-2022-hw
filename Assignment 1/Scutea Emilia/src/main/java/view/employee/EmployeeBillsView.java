package view.employee;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class EmployeeBillsView {
    private GridPane mainPage;
    private Scene scene;
    private Stage primaryStage;

    private TextField payFromId;
    private TextField amount;
    private Button payBillsBtn;
    private Button backBtn;

    public EmployeeBillsView(){
        mainPage = new GridPane();
        scene = new Scene(mainPage, 400, 600);
        payFromId = new TextField();
        amount = new TextField();
        payBillsBtn = new Button("Pay bill");
        backBtn = new Button("Back");
        setUpView();
    }

    private void setUpView(){
        mainPage.prefHeight(400.0);
        mainPage.prefWidth(600.0);

        payFromId.setLayoutX(30);
        payFromId.setLayoutY(68);
        amount.setLayoutX(235);
        amount.setLayoutY(68);
        payBillsBtn.setLayoutX(175);
        payBillsBtn.setLayoutY(175);
        backBtn.setLayoutY(51);
        backBtn.setLayoutY(163);

        payFromId.setPromptText("Pay from account ID");
        amount.setPromptText("Amount");

        mainPage.add(payFromId, 1, 1);
        mainPage.add(amount, 3, 1);
        mainPage.add(payBillsBtn, 3, 2);
        mainPage.add(backBtn,1 ,2);
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

    public Button getPayBillsBtn() {
        return payBillsBtn;
    }

    public Button getBackBtn(){ return  backBtn;}

    public String getPayFromId(){
        return payFromId.getText();
    }

    public String getAmount(){
        return amount.getText();
    }
}

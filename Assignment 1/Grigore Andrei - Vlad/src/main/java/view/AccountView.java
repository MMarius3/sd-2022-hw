package view;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Account;
import model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.sql.Date;

public class AccountView extends Pane {
    private Button show = new Button("Show accounts");
    private Button create = new Button("Create account");
    private Button update = new Button("Update account");
    private Button delete = new Button("Delete account");
    private Button back = new Button("Back");
    private ComboBox<Account> transferFrom;
    private ComboBox<Account> transferTo;
    private Button transfer;
    private TableView<Account> accounts;
    private TextField sum;
    private Button processBills = new Button("Process Bills");

    public AccountView(){
        initializeFields();
    }

    private void initializeFields(){
        show.setLayoutX(30);
        show.setLayoutY(50);
        show.setFont(Font.font("Times New Roman",14));
        show.setStyle("-fx-base: cyan;");

        create.setLayoutX(30);
        create.setLayoutY(100);
        create.setFont(Font.font("Times New Roman",14));
        create.setStyle("-fx-base: cyan;");


        update.setLayoutX(30);
        update.setLayoutY(150);
        update.setFont(Font.font("Times New Roman",14));
        update.setStyle("-fx-base: cyan;");


        delete.setLayoutX(30);
        delete.setLayoutY(200);
        delete.setFont(Font.font("Times New Roman",14));
        delete.setStyle("-fx-base: cyan;");

        back.setLayoutX(30);
        back.setLayoutY(250);
        back.setFont(Font.font("Times New Roman",14));
        back.setStyle("-fx-base: cyan;");

        transferFrom = new ComboBox<>();
        transferFrom.setLayoutX(30);
        transferFrom.setLayoutY(300);
        transferFrom.setPromptText("transfer from");

        transferTo = new ComboBox<>();
        transferTo.setLayoutX(30);
        transferTo.setLayoutY(350);
        transferTo.setPromptText("transfer to");

        sum = new TextField();
        sum.setPromptText("Money to transfer");
        sum.setLayoutX(30);
        sum.setLayoutY(400);

        transfer = new Button("Transfer");
        transfer.setLayoutX(30);
        transfer.setLayoutY(450);
        transfer.setFont(Font.font("Times New Roman",14));
        transfer.setStyle("-fx-base: cyan;");

        processBills.setLayoutX(30);
        processBills.setLayoutY(500);
        processBills.setFont(Font.font("Times New Roman",14));
        processBills.setStyle("-fx-base: cyan;");

        accounts = new TableView<>();
        accounts.setLayoutX(200);
        accounts.setLayoutY(50);

        for(Field field:(new Account()).getClass().getDeclaredFields()){
            if(!field.getName().equals("created_at")) {
                TableColumn col = new TableColumn(field.getName());
                col.setCellValueFactory(new PropertyValueFactory<Parameter, String>(field.getName()));
                accounts.getColumns().add(col);
            }
        }

        this.getChildren().addAll(processBills,sum,transfer,transferFrom,transferTo,accounts,create,update,delete,show,back);
    }

    public Button getShow() {
        return show;
    }

    public Button getCreate() {
        return create;
    }

    public Button getUpdate() {
        return update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setShow(Button show) {
        this.show = show;
    }

    public void setCreate(Button create) {
        this.create = create;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getBack() {
        return back;
    }

    public void setBack(Button back) {
        this.back = back;
    }

    public TableView<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(TableView<Account> accounts) {
        this.accounts = accounts;
    }

    public ComboBox<Account> getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(ComboBox<Account> transferFrom) {
        this.transferFrom = transferFrom;
    }

    public ComboBox<Account> getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(ComboBox<Account> transferTo) {
        this.transferTo = transferTo;
    }

    public Button getTransfer() {
        return transfer;
    }

    public void setTransfer(Button transfer) {
        this.transfer = transfer;
    }

    public TextField getSum() {
        return sum;
    }

    public void setSum(TextField sum) {
        this.sum = sum;
    }

    public Button getProcessBills() {
        return processBills;
    }

    public void setProcessBills(Button processBills) {
        this.processBills = processBills;
    }
}

package view;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;

public class EmployeeView extends Pane {

    private Button show = new Button("Show clients");
    private Button create = new Button("Create client");
    private Button update = new Button("Update client");
    private Button delete = new Button("Delete client");
    private Button transaction = new Button("Do transaction");
    private Button accounts = new Button("Go to accounts");
    private TableView<Client> clients;
    private TextField sumToPay;

    public EmployeeView(){
        this.setPrefSize(800,600);
        this.initializeFields();
    }

    public void initializeFields(){
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


        transaction.setLayoutX(30);
        transaction.setLayoutY(250);
        transaction.setFont(Font.font("Times New Roman",14));
        transaction.setStyle("-fx-base: cyan;");



        accounts.setLayoutX(30);
        accounts.setLayoutY(350);
        accounts.setFont(Font.font("Times New Roman",14));
        accounts.setStyle("-fx-base: cyan;");

        clients = new TableView<>();
        clients.setLayoutX(200);
        clients.setLayoutY(50);

        Client client =  new Client();
        for(Field field: client.getClass().getDeclaredFields()){

                TableColumn col = new TableColumn(field.getName());
                col.setCellValueFactory(new PropertyValueFactory<Parameter, String>(field.getName()));
                clients.getColumns().add(col);
        }

        this.getChildren().addAll(clients,accounts,show,create,update,delete,transaction);

    }

    public Button getShow() {
        return show;
    }

    public void setShow(Button show) {
        this.show = show;
    }

    public Button getCreate() {
        return create;
    }

    public void setCreate(Button create) {
        this.create = create;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getTransaction() {
        return transaction;
    }

    public void setTransaction(Button transaction) {
        this.transaction = transaction;
    }

    public Button getAccounts() {
        return accounts;
    }

    public void setAccounts(Button accounts) {
        this.accounts = accounts;
    }

    public TableView<Client> getClients() {
        return clients;
    }

    public void setClients(TableView<Client> clients) {
        this.clients = clients;
    }

    public TextField getSumToPay() {
        return sumToPay;
    }

    public void setSumToPay(TextField sumToPay) {
        this.sumToPay = sumToPay;
    }
}

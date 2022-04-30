package View;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class EmployeeView {

    private final GridPane mainGrid=new GridPane();
    private final BorderPane mainPane = new BorderPane();
    private final Scene mainScene=new Scene(mainPane,900,600);
    private VBox vbox = new VBox();
    private Text clientTitle = new Text("Client info");
    private Text accountTitle = new Text("Account info");
    private Text clientIdMsg = new Text("Cliend ID: ");
    private Text clientNameMsg = new Text("Name :");
    private Text pncMsg = new Text("PNC : ");
    private Text addressMsg = new Text("Address : ");
    private Text cardNrMsg = new Text("Card nr : ");
    private Text accountFromMsg = new Text("Account From : ");
    private Text accountToMsg = new Text("Account To : ");
    private TextField accountFrom = new TextField();
    private TextField accountTo  = new TextField();
    private TextField clientId = new TextField();
    private TextField clientName = new TextField();
    private TextField pnc = new TextField();
    private TextField address = new TextField();
    private TextField cardNr = new TextField();
    private Text accountNrMsg = new Text("Account Number : ");
    private Text typeMsg = new Text("Type : ");
    private Text amountMsg = new Text("Amount : ");
    private Text amount2Msg = new Text("Amount : ");
    private TextField amount2 = new TextField();
    private TextField accountNr = new TextField();
    private TextField type = new TextField();
    private TextField amount = new TextField();
    private Text errorMsg = new Text();
    private Text accountNrMsg2 = new Text("Account Nr: ");
    private Text billPriceMsg = new Text("Bill Price : ");
    private TextField accountNr2 = new TextField();
    private TextField billPrice = new TextField();

    private Button addClient = new Button("Add Client");
    private Button viewClients = new Button("View Clients");
    private Button updateClient = new Button("Update Client");
    private Button addAccount = new Button("Add Account");
    private Button viewAccounts = new Button("View Accounts");
    private Button updateAccount = new Button("Update Account");
    private Button deleteAccount = new Button("Delete Account");
    private Button transferMoney = new Button("Transfer Money");
    private Button processBills = new Button("Pay Bill");
    private Button logOut=new Button("LogOut");

    public EmployeeView(){
        mainPane.setLeft(mainGrid);
        mainPane.setCenter(vbox);
        mainGrid.setHgap(5);
        mainGrid.setVgap(5);
        mainGrid.setPadding(new Insets(30,30,30,30));
        mainGrid.add(accountTitle,2,2);
        mainGrid.add(accountNrMsg,2,3);
        mainGrid.add(accountNr,3,3);
        mainGrid.add(typeMsg,2,4);
        mainGrid.add(type,3,4);
        mainGrid.add(amountMsg,2,5);
        mainGrid.add(amount,3,5);
        mainGrid.add(clientTitle,2,12);
        mainGrid.add(clientIdMsg,2,13);
        mainGrid.add(clientId,3,13);
        mainGrid.add(clientNameMsg,2,14);
        mainGrid.add(clientName,3,14);
        mainGrid.add(pncMsg,2,15);
        mainGrid.add(pnc,3,15);
        mainGrid.add(cardNrMsg,2,16);
        mainGrid.add(cardNr,3,16);
        mainGrid.add(addressMsg,2,17);
        mainGrid.add(address,3,17);


        addClient.setMaxSize(200,200);
        viewClients.setMaxSize(200,200);
        updateClient.setMaxSize(200,200);
        addAccount.setMaxSize(200,200);
        viewAccounts.setMaxSize(200,200);
        updateAccount.setMaxSize(200,200);
        deleteAccount.setMaxSize(200,200);
        transferMoney.setMaxSize(200,200);
        processBills.setMaxSize(200,200);
        logOut.setMaxSize(200,200);

        mainGrid.add(addClient,3,18);
        mainGrid.add(viewClients,3,19);
        mainGrid.add(updateClient,3,20);
        mainGrid.add(addAccount,3,6);
        mainGrid.add(viewAccounts,3,7);
        mainGrid.add(updateAccount,3,8);
        mainGrid.add(deleteAccount,3,9);
        mainGrid.add(accountFromMsg,6,3);
        mainGrid.add(accountToMsg,6,4);
        mainGrid.add(accountFrom,7,3);
        mainGrid.add(accountTo,7,4);
        mainGrid.add(amount2Msg,6,5);
        mainGrid.add(amount2,7,5);
        mainGrid.add(transferMoney,7,6);
        mainGrid.add(accountNrMsg2,6, 7);
        mainGrid.add(accountNr2,7,7);
        mainGrid.add(billPriceMsg,6,8);
        mainGrid.add(billPrice,7,8);
        mainGrid.add(processBills,7,9);
        mainGrid.add(logOut,3,21);
        mainGrid.add(errorMsg,5,14,5,20);
    }


    public TableView createTable(ObservableList<?> objects){

        TableView<Type> tableView=new TableView<>();
        if(!objects.isEmpty()) {
            tableView.setItems((ObservableList<Type>) objects);
            for (Field field : objects.get(0).getClass().getDeclaredFields()) {
                field.setAccessible(true);
                TableColumn<Type,String> column=new TableColumn(field.getName());
                column.setCellValueFactory(new PropertyValueFactory(field.getName()));
                tableView.getColumns().add(column);
            }
        }
        return tableView;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Button getLogOut() {
        return logOut;
    }

    public VBox getVbox() {
        return vbox;
    }

    public TextField getAccountFrom() {
        return accountFrom;
    }

    public TextField getAccountTo() {
        return accountTo;
    }

    public TextField getClientId() {
        return clientId;
    }

    public TextField getClientName() {
        return clientName;
    }

    public TextField getPnc() {
        return pnc;
    }

    public void setPnc(TextField pnc) {
        this.pnc = pnc;
    }

    public TextField getAddress() {
        return address;
    }

    public void setAddress(TextField address) {
        this.address = address;
    }

    public TextField getAccountNr() {
        return accountNr;
    }

    public TextField getType() {
        return type;
    }

    public void setType(TextField type) {
        this.type = type;
    }

    public TextField getAmount() {
        return amount;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public Button getAddClient() {
        return addClient;
    }

    public Button getViewClients() {
        return viewClients;
    }

    public Button getUpdateClient() {
        return updateClient;
    }

    public Button getAddAccount() {
        return addAccount;
    }

    public Button getViewAccounts() {
        return viewAccounts;
    }

    public Button getUpdateAccount() {
        return updateAccount;
    }

    public Button getDeleteAccount() {
        return deleteAccount;
    }

    public Button getTransferMoney() {
        return transferMoney;
    }

    public Button getProcessBills() {
        return processBills;
    }

    public Text getErrorMsg() {
        return errorMsg;
    }

    public TextField getAccountNr2() {
        return accountNr2;
    }

    public TextField getBillPrice() {
        return billPrice;
    }

    public TextField getCardNr() { return cardNr; }

    public TextField getAmount2() { return amount2; }
}

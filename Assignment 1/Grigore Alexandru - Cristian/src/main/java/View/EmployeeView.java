package View;

import Model.Client;
import Model.ClientAccount;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class EmployeeView extends Pane {

    private Button add;
    private Button edit;
    private Button view;
    private Button createAccount;
    private Button editAccount;
    private Button deleteAccount;
    private Button viewAccounts;
    private Button makeTransaction;
    private TextField clientName;
    private TextField clientIdentityCardNumber;
    private TextField personalNumericalCode;
    private TextField address;
    private TableView<Client> clientTableView;
    private TableView<ClientAccount> clientAccountTableView;
    private Button toEdit;
    private TextField accountIdentificationNumber;
    private TextField accountType;
    private TextField money;
    private DatePicker creationDate;
    private Button toEditAccount;
    private ComboBox<ClientAccount> account1;
    private ComboBox<ClientAccount> account2;
    private TextField amount;
    private TextField accountId;
    private Button payBill;

    public EmployeeView(){
        setPrefSize(1000,720);
        initializeFields();
        add.setLayoutX(20);
        add.setLayoutY(20);
        edit.setLayoutX(20);
        edit.setLayoutY(50);
        view.setLayoutX(20);
        view.setLayoutY(80);
        createAccount.setLayoutX(20);
        createAccount.setLayoutY(110);
        deleteAccount.setLayoutX(20);
        deleteAccount.setLayoutY(140);
        editAccount.setLayoutX(20);
        editAccount.setLayoutY(170);
        viewAccounts.setLayoutX(20);
        viewAccounts.setLayoutY(200);
        clientName.setLayoutX(160);
        clientName.setLayoutY(20);
        clientName.setPromptText("Client name");
        clientIdentityCardNumber.setLayoutX(160);
        clientIdentityCardNumber.setLayoutY(140);
        clientIdentityCardNumber.setPromptText("Identity card number");
        personalNumericalCode.setLayoutX(160);
        personalNumericalCode.setLayoutY(60);
        personalNumericalCode.setPromptText("CNP");
        address.setLayoutX(160);
        address.setLayoutY(100);
        address.setPromptText("Address");
        clientTableView.setLayoutX(360);
        clientTableView.setLayoutY(20);
        clientAccountTableView.setLayoutX(360);
        clientAccountTableView.setLayoutY(20);
        toEdit.setLayoutX(20);
        toEdit.setLayoutY(240);
        accountIdentificationNumber.setPromptText("Account number");
        accountIdentificationNumber.setLayoutX(160);
        accountIdentificationNumber.setLayoutY(180);
        accountType.setPromptText("Type");
        accountType.setLayoutX(160);
        accountType.setLayoutY(220);
        money.setPromptText("Amount");
        money.setLayoutX(160);
        money.setLayoutY(260);
        creationDate.setLayoutX(160);
        creationDate.setLayoutY(300);
        toEditAccount.setLayoutX(20);
        toEditAccount.setLayoutY(280);
        account1.setLayoutX(20);
        account1.setLayoutY(320);
        account1.setPromptText("Sender");
        account2.setLayoutX(20);
        account2.setLayoutY(360);
        account2.setPromptText("Receiver");
        amount.setLayoutX(20);
        amount.setLayoutY(400);
        makeTransaction.setLayoutX(20);
        makeTransaction.setLayoutY(440);
        accountId.setLayoutX(160);
        accountId.setLayoutY(340);
        payBill.setLayoutX(20);
        payBill.setLayoutY(480);
        for(Field field : Client.class.getDeclaredFields()){
            TableColumn col = new TableColumn(field.getName());
            col.setCellValueFactory(new PropertyValueFactory<Parameter, String>(field.getName()));
            clientTableView.getColumns().add(col);
        }

        for(Field field : ClientAccount.class.getDeclaredFields()){
            TableColumn col = new TableColumn(field.getName());
            col.setCellValueFactory(new PropertyValueFactory<Parameter, String>(field.getName()));
            clientAccountTableView.getColumns().add(col);
        }
        getChildren().addAll(add,edit,view,createAccount,deleteAccount,editAccount,viewAccounts,clientName,personalNumericalCode,address,clientIdentityCardNumber,clientTableView,toEdit,accountType,accountIdentificationNumber,money,creationDate, toEditAccount,account1,account2,amount,makeTransaction,accountId, payBill);
    }

    public Button getPayBill() {
        return payBill;
    }

    public TextField getAmount() {
        return amount;
    }

    public ComboBox<ClientAccount> getAccount1() {
        return account1;
    }

    public ComboBox<ClientAccount> getAccount2() {
        return account2;
    }

    public Button getToEditAccount() {
        return toEditAccount;
    }

    public TableView<ClientAccount> getClientAccountTableView() {
        return clientAccountTableView;
    }

    public TextField getAccountIdentificationNumber() {
        return accountIdentificationNumber;
    }

    public TextField getAccountType() {
        return accountType;
    }

    public TextField getMoney() {
        return money;
    }

    public DatePicker getCreationDate() {
        return creationDate;
    }

    public Button getToEdit() {
        return toEdit;
    }

    public TableView<Client> getClientTableView() {
        return clientTableView;
    }

    public Button getAdd() {
        return add;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getView() {
        return view;
    }

    public Button getCreateAccount() {
        return createAccount;
    }

    public Button getEditAccount() {
        return editAccount;
    }

    public Button getDeleteAccount() {
        return deleteAccount;
    }

    public Button getViewAccounts() {
        return viewAccounts;
    }

    public Button getMakeTransaction() {
        return makeTransaction;
    }

    public TextField getClientName() {
        return clientName;
    }

    public TextField getClientIdentityCardNumber() {
        return clientIdentityCardNumber;
    }

    public TextField getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public TextField getAddress() {
        return address;
    }

    public TextField getAccountId() {
        return accountId;
    }

    private void initializeFields(){
        add = new Button("Add client");
        edit = new Button("Edit client");
        view = new Button("View clients");
        createAccount = new Button("Create account");
        editAccount = new Button("Edit account");
        deleteAccount = new Button("Delete account");
        viewAccounts = new Button("View accounts");
        makeTransaction = new Button("Make transaction");
        toEdit = new Button("To edit");
        toEditAccount = new Button("Account to edit");
        payBill = new Button("Pay bill");

        clientName = new TextField();
        clientIdentityCardNumber = new TextField();
        personalNumericalCode = new TextField();
        address = new TextField();
        clientTableView = new TableView<>();
        clientAccountTableView = new TableView<>();
        accountIdentificationNumber = new TextField();
        accountType = new TextField();
        money = new TextField();
        creationDate = new DatePicker();
        account1 = new ComboBox<>();
        account2 = new ComboBox<>();
        amount = new TextField();
        amount.setPromptText("amount to Transfer");
        accountId = new TextField();
        accountId.setPromptText("Account id");
    }


}

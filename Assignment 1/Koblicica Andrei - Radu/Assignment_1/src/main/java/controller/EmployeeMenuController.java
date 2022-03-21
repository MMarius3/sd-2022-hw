package controller;

import database.JDBConnectionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import model.builder.AccountBuilder;
import model.builder.ActionBuilder;
import model.builder.ClientBuilder;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.TransactionValidator;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImplementation;
import service.client.ClientService;
import service.client.ClientServiceImplementation;
import service.user.UserService;
import service.user.UserServiceImplementation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class EmployeeMenuController {


    private User user;

    private ClientService clientService;
    private AccountService accountService;
    private UserService userService;
    private Client selectedClient;
    private Account selectedAccount;
    private ClientValidator clientValidator;
    private AccountValidator accountValidator;
    private TransactionValidator transactionValidator;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Long> clientIDColumn;
    @FXML
    private TableColumn<Client, String> clientNameColumn;
    @FXML
    private TableColumn<Client, String> clientCardNumberColumn;
    @FXML
    private TableColumn<Client, String> clientCNPColumn;
    @FXML
    private TableColumn<Client, String> clientAddressColumn;

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, Long> accountIDColumn;
    @FXML
    private TableColumn<Account, Long> accountClientIDColumn;
    @FXML
    private TableColumn<Account, String> accountIdentificationNumberColumn;
    @FXML
    private TableColumn<Account, String> accountTypeColumn;
    @FXML
    private TableColumn<Account, Float> accountBalanceColumn;
    @FXML
    private TableColumn<Account, Date> accountCreationDateColumn;

    @FXML
    private Button addClientButton;
    @FXML
    private Button editClientButton;
    @FXML
    private Button addAccountButton;
    @FXML
    private Button editAccountButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Button submitClientAccountButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField clientNameField;
    @FXML
    private TextField clientCardNumberField;
    @FXML
    private TextField clientCNPField;
    @FXML
    private TextField clientAddressField;
    @FXML
    private TextField accountIdentificationNumberField;
    @FXML
    private TextField accountClientIDField;

    @FXML
    private TextField accountBalanceField;
    @FXML
    private DatePicker accountCreationDateField;

    @FXML
    private ChoiceBox<String> accountTypeChoiceBox;

    @FXML
    private Label clientNameLabel;
    @FXML
    private Label clientCardNumberLabel;
    @FXML
    private Label clientCNPLabel;
    @FXML
    private Label clientAddressLabel;
    @FXML
    private Label accountIdentificationNumberLabel;
    @FXML
    private Label accountClientIDLabel;
    @FXML
    private Label accountTypeLabel;
    @FXML
    private Label accountBalanceLabel;
    @FXML
    private Label accountCreationDateLabel;

    @FXML
    private Label actionLabel;

    @FXML
    private TextField fromField;
    @FXML
    private TextField toField;
    @FXML
    private TextField transferSumField;
    @FXML
    private TextField billSumField;
    @FXML
    private TextField billAccountIdField;
    @FXML
    private TextField billDescriptionField;

    @FXML
    private Button submitTransferButton;
    @FXML
    private Button submitBillButton;



    public void initializeScene(User user, ClientService clientService, AccountService accountService, UserService userService, ClientValidator clientValidator, AccountValidator accountValidator, TransactionValidator transactionValidator){
        this.user=user;
        setClientVisibility(false);
        setAccountVisibility(false);

        this.clientService=clientService;
        this.accountService=accountService;
        this.userService=userService;
        initializeClientsTable();
        initializeAccountsTable();
        for(AccountType accountType: AccountType.values()){
            accountTypeChoiceBox.getItems().add(accountType.getText());
        }
        selectedClient=new Client();
        this.clientValidator=clientValidator;
        this.accountValidator=accountValidator;
        this.transactionValidator=transactionValidator;
    }

    private void setClientVisibility(boolean visibility){
        clientAddressLabel.setVisible(visibility);
        clientCNPLabel.setVisible(visibility);
        clientCardNumberLabel.setVisible(visibility);
        clientNameLabel.setVisible(visibility);
        clientAddressField.setVisible(visibility);
        clientCNPField.setVisible(visibility);
        clientNameField.setVisible(visibility);
        clientCardNumberField.setVisible(visibility);
        submitClientAccountButton.setVisible(visibility);
    }

    private void setAccountVisibility(boolean visibility){
        accountBalanceLabel.setVisible(visibility);
        accountClientIDLabel.setVisible(visibility);
        accountCreationDateLabel.setVisible(visibility);
        accountIdentificationNumberLabel.setVisible(visibility);
        accountTypeLabel.setVisible(visibility);
        accountBalanceField.setVisible(visibility);
        accountClientIDField.setVisible(visibility);
        accountCreationDateField.setVisible(visibility);
        accountTypeChoiceBox.setVisible(visibility);
        accountIdentificationNumberField.setVisible(visibility);
        submitClientAccountButton.setVisible(visibility);
    }
    private void clearClientFields(){
        clientNameField.clear();
        clientCardNumberField.clear();
        clientAddressField.clear();
        clientCNPField.clear();
    }

    private void clearAccountFields(){
        accountBalanceField.clear();
        accountIdentificationNumberField.clear();
        accountClientIDField.clear();
        accountCreationDateField.getEditor().clear();
    }
    private void clearOtherFields(){
        fromField.clear();
        toField.clear();
        transferSumField.clear();
        billAccountIdField.clear();
        billDescriptionField.clear();
        billSumField.clear();
    }

    private void initializeClientsTable(){
        clientIDColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        clientCardNumberColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("identityCardNumber"));
        clientCNPColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("personalNumericalCode"));
        clientAddressColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        clientTable.setItems(clientService.findAll());
    }

    private void initializeAccountsTable(){
        accountIDColumn.setCellValueFactory(new PropertyValueFactory<Account, Long>("id"));
        accountClientIDColumn.setCellValueFactory(new PropertyValueFactory<Account, Long>("clientId"));
        accountIdentificationNumberColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("identificationNumber"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("type"));
        accountBalanceColumn.setCellValueFactory(new PropertyValueFactory<Account, Float>("amount"));
        accountCreationDateColumn.setCellValueFactory(new PropertyValueFactory<Account, Date>("dateOfCreation"));
        accountTable.setItems(accountService.findAll());

    }


    @FXML
    private void pressAddClient(ActionEvent event){
        clearClientFields();
        setAccountVisibility(false);
        setClientVisibility(true);
        actionLabel.setText("Add Client:");
    }
    @FXML
    private void pressEditClient(ActionEvent event){
        if(clientTable.getSelectionModel().getSelectedItem()!=null){
            selectedClient=clientTable.getSelectionModel().getSelectedItem();
            clientNameField.setText(selectedClient.getName());
            clientCNPField.setText(selectedClient.getPersonalNumericalCode());
            clientCardNumberField.setText(selectedClient.getIdentityCardNumber());
            clientAddressField.setText(selectedClient.getAddress());
            setAccountVisibility(false);
            setClientVisibility(true);
            actionLabel.setText("Edit Client:");
        }

    }
    @FXML
    private void pressAddAccount(ActionEvent event){
        clearAccountFields();
        setClientVisibility(false);
        setAccountVisibility(true);
        actionLabel.setText("Add Account:");
    }
    @FXML
    private void pressEditAccount(ActionEvent event){
        if(accountTable.getSelectionModel().getSelectedItem()!=null){
            selectedAccount=accountTable.getSelectionModel().getSelectedItem();
            accountClientIDField.setText(String.valueOf(selectedAccount.getClientId()));
            accountIdentificationNumberField.setText(selectedAccount.getIdentificationNumber());
            accountTypeChoiceBox.setValue(selectedAccount.getType().getText());
            accountBalanceField.setText(String.valueOf(selectedAccount.getAmount()));
            accountCreationDateField.setValue(selectedAccount.dateOfCreation.toLocalDate());
            setClientVisibility(false);
            setAccountVisibility(true);
            actionLabel.setText("Edit Account:");
        }

    }
    @FXML
    private void pressDeleteAccount(ActionEvent event){
        if(accountTable.getSelectionModel().getSelectedItem()!=null){
            Account account=accountTable.getSelectionModel().getSelectedItem();
            accountService.remove(account);
            accountTable.setItems(accountService.findAll());
        }
        pressCancel(event);

    }

    @FXML
    private void pressSubmit(ActionEvent event){
        String action=actionLabel.getText();
        if(action.equals("Add Account:")||action.equals("Edit Account:")){
            submitAccount(action);
        }
        if(action.equals("Add Client:")||action.equals("Edit Client:")){
            submitClient(action);
        }
    }


    @FXML
    private void submitClient(String action){
        String name=clientNameField.getText();
        String identityCardNumber=clientCardNumberField.getText();
        String personalNumericalCode=clientCNPField.getText();
        String address=clientAddressField.getText();
        if(action.equals("Add Client:")){
          submitAddClient(name, identityCardNumber,personalNumericalCode,address);
        }
        else{
            if(action.equals("Edit Client:")){
               submitEditClient(name, identityCardNumber,personalNumericalCode,address);
            }
        }
    }
    private void submitAddClient(String name, String identityCardNumber, String personalNumericalCode, String address){
        clientValidator.validateAdd(name, identityCardNumber,personalNumericalCode,address);
        final String errors = clientValidator.getFormattedErrors();
        if(errors.isEmpty()){
            Client client = new ClientBuilder()
                    .setName(name)
                    .setIdentityCardNumber(identityCardNumber)
                    .setPersonalNumericalCode(personalNumericalCode)
                    .setAddress(address)
                    .build();
            clientService.save(client);
            userService.addAction(user.getId(),"Added a client");
            clientTable.setItems(clientService.findAll());
            setClientVisibility(false);
            actionLabel.setText("");
        }else{
            loadAlertBox(errors);
        }
    }

    private void submitEditClient(String name, String identityCardNumber, String personalNumericalCode, String address){
        boolean cardNumberChanged=clientCardNumberField.getText().equals(selectedClient.getIdentityCardNumber());
        boolean CNPChanged=clientCNPField.getText().equals(selectedClient.getPersonalNumericalCode());
        clientValidator.validateEdit(name, identityCardNumber,personalNumericalCode,address, !cardNumberChanged,!CNPChanged);
        final String errors = clientValidator.getFormattedErrors();
        if(errors.isEmpty()){
            Client client = new ClientBuilder()
                    .setId(selectedClient.getId())
                    .setName(name)
                    .setIdentityCardNumber(identityCardNumber)
                    .setPersonalNumericalCode(personalNumericalCode)
                    .setAddress(address)
                    .build();
            clientService.edit(client);
            userService.addAction(user.getId(),"Edited a client");
            clientTable.setItems(clientService.findAll());
            setClientVisibility(false);
            actionLabel.setText("");
        }else{
            loadAlertBox(errors);
        }
    }


    @FXML
    private void submitAccount(String action){
        String clientId=accountClientIDField.getText();
        String identificationNumber=accountIdentificationNumberField.getText();
        String type=accountTypeChoiceBox.getValue();
        String balance=accountBalanceField.getText();
        String date= String.valueOf(accountCreationDateField.getValue());

        if(action.equals("Add Account:")){
            submitAddAccount(clientId,identificationNumber,type,balance,date);

        }
        else{
            if(action.equals("Edit Account:")){
                submitEditAccount(clientId,identificationNumber,type,balance,date);
            }
        }
    }

    private void submitAddAccount(String clientId, String identificationNumber, String type, String balance, String date){
        accountValidator.validate(clientId,identificationNumber,type,balance,date, true);
        final String errors = accountValidator.getFormattedErrors();
        if(errors.isEmpty()){
            Account account = new AccountBuilder()
                    .setClientId(Long.valueOf(clientId))
                    .setIdentificationNumber(identificationNumber)
                    .setType(type)
                    .setAmount(Float.parseFloat(balance))
                    .setDateOfCreation(Date.valueOf(date))
                    .build();
            accountService.save(account);
            userService.addAction(user.getId(),"Added an account");
            accountTable.setItems(accountService.findAll());
            setAccountVisibility(false);
            actionLabel.setText("");
        }else{
            loadAlertBox(errors);
        }
    }

    private void submitEditAccount(String clientId, String identificationNumber, String type, String balance, String date){
        boolean identificationNumberChanged=identificationNumber.equals(selectedAccount.getIdentificationNumber());
        accountValidator.validate(clientId,identificationNumber,type,balance,date, !identificationNumberChanged);
        final String errors = accountValidator.getFormattedErrors();
        if(errors.isEmpty()){
            Account account = new AccountBuilder().setId(selectedAccount.getId())
                    .setClientId(Long.valueOf(clientId))
                    .setIdentificationNumber(identificationNumber)
                    .setType(type)
                    .setAmount(Float.parseFloat(balance))
                    .setDateOfCreation(Date.valueOf(date))
                    .build();
            accountService.edit(account);
            userService.addAction(user.getId(),"Edited an account");
            accountTable.setItems(accountService.findAll());
            setAccountVisibility(false);
            actionLabel.setText("");
        }else{
            loadAlertBox(errors);
        }
    }

    @FXML
    private void pressSubmitTransfer(ActionEvent event){
        String from=fromField.getText();
        String to=toField.getText();
        String sum=transferSumField.getText();
        transactionValidator.validateTransfer(from,to,sum);
        final String errors=transactionValidator.getFormattedErrors();
        if(errors.isEmpty()){
            accountService.updateAmount(-1*Float.parseFloat(sum),Long.parseLong(from));
            accountService.updateAmount(Float.parseFloat(sum),Long.parseLong(to));
            userService.addAction(user.getId(),"Performed a transfer");
            accountTable.setItems(accountService.findAll());
        }
        else{
            loadAlertBox(errors);
        }
    }

    @FXML
    private void pressSubmitBill(ActionEvent event){
        String id= billAccountIdField.getText();
        String sum=billSumField.getText();
        String description=billDescriptionField.getText();
        transactionValidator.validateBill(id,sum,description);
        final String errors=transactionValidator.getFormattedErrors();
        if(errors.isEmpty()){
            accountService.updateAmount(-1*Float.parseFloat(sum),Long.parseLong(id));
            userService.addAction(user.getId(),"Processed a bill");
            accountTable.setItems(accountService.findAll());
        }
        else{
            loadAlertBox(errors);
        }
    }


    @FXML
    private void pressCancel(ActionEvent event){
        clearAccountFields();
        clearClientFields();
        clearOtherFields();
        setAccountVisibility(false);
        setClientVisibility(false);
        actionLabel.setText("");
    }



    private void loadAlertBox(String errors){
        Stage stage=new Stage();
        stage.setTitle("Error");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AlertBox.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertBoxController alertBoxController=loader.getController();
        alertBoxController.initializeLabel(errors);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }



}

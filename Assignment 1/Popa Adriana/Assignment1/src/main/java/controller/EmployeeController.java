package controller;

import database.JDBConnectionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Account;
import model.Client;
import model.Report;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.ReportBuilder;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.report.ReportService;
import service.report.ReportServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static database.Constants.Schemas.PRODUCTION;

public class EmployeeController implements Initializable {

    @FXML private TextField clientId;
    @FXML private TextField name;
    @FXML private TextField identityCardNumber;
    @FXML private TextField personalNumericalCode;
    @FXML private TextField address;

    @FXML private TextField accountId;
    @FXML private TextField accountClientId;
    @FXML private TextField identificationNumber;
    @FXML private TextField type;
    @FXML private TextField amountOfMoney;
    @FXML private TextField dateOfCreation;

    @FXML private TextField transferFrom;
    @FXML private TextField transferTo;
    @FXML private TextField transferAmount;

    @FXML private TextField billAmount;
    @FXML private TextField billId;

    @FXML private TextArea textArea;
    @FXML private TableView<Client> clientTableView;
    @FXML private TableColumn<Client,Integer> clientLongTableCell;
    @FXML private TableColumn<Client,String> clientNameTableCell;
    @FXML private TableColumn<Client,String> clientIDCardTableCell;
    @FXML private TableColumn<Client,String> clientPNCTableCell;
    @FXML private TableColumn<Client,String> clientAddressTableCell;

    @FXML private TableView<Account> accountTableView;
    @FXML private TableColumn<Account,Integer> accountIntegerTableColumn;
    @FXML private TableColumn<Account,Integer> accountClientTableColumn;
    @FXML private TableColumn<Account,String> accountNumberTableColumn;
    @FXML private TableColumn<Account,String> accountTypeTableColumn;
    @FXML private TableColumn<Account,String> accountMoneyTableColumn;
    @FXML private TableColumn<Account,String> accountDateTableColumn;

    private final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    private final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
    private final ClientService clientService = new ClientServiceMySQL(clientRepository);
    private final ClientValidator clientValidator = new ClientValidator();

    private final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
    private final AccountService accountService = new AccountServiceMySQL(accountRepository);
    private final AccountValidator accountValidator = new AccountValidator();

    private final ReportRepository reportRepository = new ReportRepositoryMySQL(connection);
    private final ReportService reportService = new ReportServiceMySQL(reportRepository);

    private final String username;

    public EmployeeController(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientLongTableCell.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientNameTableCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientIDCardTableCell.setCellValueFactory(new PropertyValueFactory<>("identityCardNumber"));
        clientPNCTableCell.setCellValueFactory(new PropertyValueFactory<>("personalNumericalCode"));
        clientAddressTableCell.setCellValueFactory(new PropertyValueFactory<>("address"));

        accountIntegerTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        accountClientTableColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        accountNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        accountTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountMoneyTableColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfMoney"));
        accountDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
    }

    @FXML
    private void handleAddClient(){
        Client client = new ClientBuilder()
                .setName(name.getText())
                .setIdentityCardNumber(identityCardNumber.getText())
                .setPersonalNumericCode(personalNumericalCode.getText())
                .setAddress(address.getText())
                .build();

        clientValidator.validate(client);
        final List<String> errors = clientValidator.getErrors();
        if (errors.isEmpty()) {
            clientService.addClient(client);
            addReport("Client " + client.getName() + " added!");
            textArea.setText("Client added!");
        } else {
            textArea.setText(clientValidator.getFormattedErrors());
        }
    }

    @FXML
    private void handleViewButton(){
        if(!clientId.getText().isEmpty()){
            Integer id = Integer.parseInt(clientId.getText());
            Optional<Client> client = clientService.findClient(id);
            if(client.isPresent()){
                List<Client> clients = new ArrayList<>();
                clients.add(client.get());
                clientTableView.setItems(getObservableClients(clients));
                addReport("Client " + client.get().getName() + " accessed!");
            }
            else{
                textArea.setText("Client not found!");
            }
        }
        else{
            clientTableView.setItems(getObservableClients(clientService.viewClients()));
            addReport("List of clients requested!");
        }
    }

    private ObservableList<Client> getObservableClients(List<Client> clients){
        ObservableList<Client> observableClients = FXCollections.observableArrayList();
        observableClients.addAll(clients);
        return observableClients;
    }

    @FXML
    private void handleUpdateButton(){
        if(!clientId.getText().isEmpty()) {
            Client client = new ClientBuilder()
                    .setId(Integer.parseInt(clientId.getText()))
                    .setName(name.getText())
                    .setIdentityCardNumber(identityCardNumber.getText())
                    .setPersonalNumericCode(personalNumericalCode.getText())
                    .setAddress(address.getText())
                    .build();

            clientValidator.validate(client);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientService.updateClient(client);
                addReport("Client " + client.getName() + " updated!");
                textArea.setText("Client updated!");
            } else {
                textArea.setText(clientValidator.getFormattedErrors());
            }
        }
        else{
            textArea.setText("Introduce client id!");
        }
    }

    @FXML
    private void handleViewAccount(){
        if(!accountId.getText().isEmpty()) {
            Integer id = Integer.parseInt(accountId.getText());
            Optional<Account> account = accountService.viewAccount(id);
            if(account.isPresent()){
                List<Account> accounts = new ArrayList<>();
                accounts.add(account.get());
                accountTableView.setItems(getObservableAccounts(accounts));
                addReport("Account " + account.get().getIdentificationNumber() + "viewed!");
            }
            else{
                textArea.setText("Account not found!");
            }
        }
        else{
            accountTableView.setItems(getObservableAccounts(accountService.viewAccounts()));
            addReport("List of accounts requested!");
        }
    }

    private ObservableList<Account> getObservableAccounts(List<Account> accounts){
        ObservableList<Account> observableClients = FXCollections.observableArrayList();
        observableClients.addAll(accounts);
        return observableClients;
    }

    @FXML
    private void handleCreateAccount(){
        Account account = new AccountBuilder()
                .setClientId(Integer.parseInt(accountClientId.getText()))
                .setIdentificationNumber(identificationNumber.getText())
                .setType(type.getText())
                .setAmountOfMoney(amountOfMoney.getText())
                .setDateOfCreation(dateOfCreation.getText())
                .build();
        if(clientService.findClient(account.getClientId()).isPresent()){
            accountValidator.validate(account);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountService.createAccount(account);
                addReport("Account " + account.getIdentificationNumber() + " added!");
                textArea.setText("Account added!");
            } else {
                textArea.setText(accountValidator.getFormattedErrors());
            }
        }
        else{
            textArea.setText("Client id not found!");
        }
    }

    @FXML
    private void handleUpdateAccount(){
        if(!accountId.getText().isEmpty()) {
            Account account = new AccountBuilder()
                    .setId(Integer.parseInt(accountId.getText()))
                    .setClientId(Integer.parseInt(accountClientId.getText()))
                    .setIdentificationNumber(identificationNumber.getText())
                    .setType(type.getText())
                    .setAmountOfMoney(amountOfMoney.getText())
                    .setDateOfCreation(dateOfCreation.getText())
                    .build();
            if(clientService.findClient(account.getClientId()).isPresent()) {
                accountValidator.validate(account);
                final List<String> errors = accountValidator.getErrors();
                if (errors.isEmpty()) {
                    accountService.updateAccount(account);
                    addReport("Account " + account.getIdentificationNumber() + " updated!");
                    textArea.setText("Account updated!");
                } else {
                    textArea.setText(accountValidator.getFormattedErrors());
                }
            } else{
                textArea.setText("Client id not found!");
            }
        } else{
            textArea.setText(accountValidator.getFormattedErrors());
        }
    }

    @FXML
    private void handleDeleteAccount(){
        if(!accountId.getText().isEmpty()) {
            Integer id = Integer.parseInt(accountId.getText());
            accountService.deleteAccount(id);
            addReport("Account " + id  + " deleted!");
            textArea.setText("Account deleted");
        } else {
            textArea.setText("Introduce id!");
        }
    }

    @FXML
    private void handleTransfer(){
        if(!transferFrom.getText().isEmpty()){
            if(!transferTo.getText().isEmpty()){
                Optional<Client> client = clientService.findClient(Integer.parseInt(transferFrom.getText()));
                if(client.isPresent()){
                    Optional<Client> client1 = clientService.findClient(Integer.parseInt(transferTo.getText()));
                    if(client1.isPresent()){
                        Optional<Account> account = accountService.findByClientID(client.get().getId());
                        if(account.isPresent()){
                            Optional<Account> account1 = accountService.findByClientID(client1.get().getId());
                            if(account1.isPresent()){
                                double money = Double.parseDouble(transferAmount.getText());
                                if(Double.parseDouble(account.get().getAmountOfMoney()) >= money){
                                    double newSum = Double.parseDouble(account.get().getAmountOfMoney()) - money;
                                    account.get().setAmountOfMoney(String.valueOf(newSum));
                                    accountService.updateAccount(account.get());
                                    newSum = Double.parseDouble(account1.get().getAmountOfMoney()) + money;
                                    account1.get().setAmountOfMoney(String.valueOf(newSum));
                                    accountService.updateAccount(account1.get());

                                    addReport("Client " + client.get().getName() + " transferred " + money + " to client " + client1.get().getName());
                                    textArea.setText("Transfer successful!");
                                }
                                else{
                                    textArea.setText("Not enough money!");
                                }
                            }
                            else{
                                textArea.setText("Account for client " + client1.get().getId() + "not found");
                            }
                        }
                        else{
                            textArea.setText("Account for client " + client.get().getId() + "not found");
                        }
                    }
                    else{
                        textArea.setText("Client not found");
                    }
                }
                else{
                    textArea.setText("Client not found");
                }
            }
            else{
                textArea.setText("Introduce Id for Transfer to");
            }
        }
        else{
            textArea.setText("Introduce Id for Transfer for");
        }
    }

    @FXML
    private void handleBills(){
        if(!billAmount.getText().isEmpty()){
            if(!billId.getText().isEmpty()){
                Optional<Client> client = clientService.findClient(Integer.parseInt(billId.getText()));
                double money = Double.parseDouble(billAmount.getText());
                if(client.isPresent()) {
                    Optional<Account> account = accountService.findByClientID(client.get().getId());
                    if(account.isPresent()){
                        double m = Double.parseDouble(account.get().getAmountOfMoney());
                        if (m >= money) {
                            account.get().setAmountOfMoney(String.valueOf(m-money));
                            accountService.updateAccount(account.get());

                            addReport("Bill of " + money + "payed by client" + client.get().getName());
                            textArea.setText("Bill Payed!");
                        }
                        else{
                            textArea.setText("Not enough money!");
                        }
                    }
                    else{
                        textArea.setText("Client doesn't have an account!");
                    }
                }
                else{
                    textArea.setText("Id not valid!");
                }
            }
            else{
                textArea.setText("Introduce client id!");
            }
        }
        else{
            textArea.setText("Introduce amount of money!");
        }
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent actionEvent) throws IOException {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        final UserValidator userValidator = new UserValidator(userRepository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
        LogInController controller = new LogInController(authenticationService, userValidator, rightsRolesRepository);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    private void addReport(String action){
        Report report = new ReportBuilder()
                .setEmployee(username)
                .setAction(action)
                .setDate(Date.valueOf(LocalDate.now()))
                .build();
        reportService.addReport(report);
    }
}

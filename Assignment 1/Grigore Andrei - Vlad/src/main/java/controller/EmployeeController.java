package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Account;
import model.Action;
import model.Client;
import model.User;
import model.builder.ActionBuilder;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.user.ActionService;
import view.AccountView;
import view.CreateAccountView;
import view.CreateClientView;
import view.EmployeeView;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final CreateClientView createClientView;
    private final AccountView accountView;
    private final ActionService actionService;
    private final CreateAccountView createAccountView;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final Stage stage;
    private final Scene employeeScene;
    private final ClientValidator clientValidator;
    private final AccountValidator accountValidator;

    private Scene createClientScene;
    private Scene accountScene;
    private Scene createAccountScene;
    private User user;
    private Client client;
    private Account account;

    public EmployeeController(User user, EmployeeView employeeView, Scene employeeScene, AccountView accountView, CreateClientView createClientView, ActionService actionService, CreateAccountView createAccountView, ClientRepository clientRepository, AccountRepository accountRepository, Stage stage, ClientValidator clientValidator, AccountValidator accountValidator) {
        this.employeeView = employeeView;
        this.createClientView = createClientView;
        this.actionService = actionService;
        this.accountView = accountView;
        this.createAccountView = createAccountView;
        this.clientRepository = clientRepository;
        this.stage = stage;
        this.employeeScene = employeeScene;
        this.accountRepository = accountRepository;
        this.user = user;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;
        initScenes();
        initButtons();

        stage.setScene(employeeScene);
        stage.show();
    }

    private void initScenes(){
        createClientScene = new Scene(createClientView,270,300);
        accountScene = new Scene(accountView,800,600);
        createAccountScene = new Scene(createAccountView,270,300);
    }

    private void initButtons(){

        this.employeeView.getCreate().setOnAction(actionEvent -> {
            stage.setScene(createClientScene);
        });

        this.employeeView.getAccounts().setOnAction(actionEvent -> {
            stage.setScene(accountScene);
        });

        this.createClientView.getSubmit().setOnAction(actionEvent -> {
            String name = this.createClientView.getName().getText();
            String cardId = this.createClientView.getCardId().getText();
            String pnc = this.createClientView.getPersonalNumericalCode().getText();
            String address = this.createClientView.getAddress().getText();

            clientValidator.validate(name,pnc,cardId,address);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                actionService.createClient(name,cardId,pnc,address);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,clientValidator.getFormattedErrors(), ButtonType.OK);
                alert.showAndWait();
            }
            try {
                actionService.createAction(user,"create","User " + user.getUsername() +" created the client: "+ name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.setScene(employeeScene);


        });

        this.accountView.getCreate().setOnAction(actionEvent -> {
            stage.setScene(createAccountScene);
        });

        this.createAccountView.getSubmit().setOnAction(actionEvent -> {
            String clientId = this.createAccountView.getClientId().getText();
            String type = this.createAccountView.getType().getText();
            Long balance = Long.parseLong(this.createAccountView.getBalance().getText());
            Date date = Date.from(Instant.now());

            accountValidator.validate(clientId,type,balance);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                try {
                    actionService.createAccount(clientId,type,balance,date);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,accountValidator.getFormattedErrors(), ButtonType.OK);
                alert.showAndWait();
            }
            try {
                actionService.createAction(user,"create account","User " + user.getUsername() +" created the account with clientId: "+ clientId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.setScene(accountScene);
        });

        this.accountView.getUpdate().setOnAction(actionEvent -> {
            account = (Account) this.accountView.getAccounts().getSelectionModel().getSelectedItem();
            stage.setScene(createAccountScene);
        });

        this.createAccountView.getUpdate().setOnAction(actionEvent -> {
            String category = this.createAccountView.getCategory().getSelectionModel().getSelectedItem();
            String clientId = this.createAccountView.getClientId().getText();
            String type = this.createAccountView.getType().getText();
            String balance = this.createAccountView.getBalance().getText();
            actionService.updateAccount(category,account,clientId,type, Long.valueOf(balance));

            try {
                actionService.createAction(user,"update account","User " + user.getUsername() +" updated the account with clientId: "+ account.getClientId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            stage.setScene(accountScene);
        });


        this.accountView.getBack().setOnAction(actionEvent -> {
            stage.setScene(employeeScene);
        });

        this.accountView.getDelete().setOnAction(actionEvent -> {
            account = this.accountView.getAccounts().getSelectionModel().getSelectedItem();
            actionService.deleteAccount(account);
            try {
                actionService.createAction(user,"delete account","User " + user.getUsername() +" deleted the account with clientId: "+ account.getClientId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        this.employeeView.getShow().setOnAction(actionEvent -> {
            this.employeeView.getClients().getItems().clear();
            List<Client> clientList = clientRepository.findAll();
            this.employeeView.getClients().getItems().addAll(clientList);
        });

        this.accountView.getShow().setOnAction(actionEvent -> {
            this.accountView.getAccounts().getItems().clear();
            List<Account> accountListUp = accountRepository.findAll();
            this.accountView.getTransferFrom().getItems().clear();
            this.accountView.getTransferFrom().getItems().addAll(accountListUp);
            this.accountView.getTransferFrom().setConverter(new StringConverter<Account>() {
                @Override
                public String toString(Account account) {
                    return account.toString();
                }

                @Override
                public Account fromString(String s) {
                    return null;
                }
            });
            this.accountView.getTransferTo().getItems().clear();
            this.accountView.getTransferTo().getItems().addAll(accountListUp);
            this.accountView.getTransferTo().setConverter(new StringConverter<Account>() {
                @Override
                public String toString(Account account) {
                    return account.toString();
                }

                @Override
                public Account fromString(String s) {
                    return null;
                }
            });
            this.accountView.getAccounts().getItems().addAll(accountListUp);
        });

        this.accountView.getTransfer().setOnAction(actionEvent -> {
            Account accountFrom = this.accountView.getTransferFrom().getSelectionModel().getSelectedItem();
            Account accountTo = this.accountView.getTransferTo().getSelectionModel().getSelectedItem();
            Long sum = Long.parseLong(this.accountView.getSum().getText());
            try {
                actionService.transaction(accountFrom,sum,accountTo);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                actionService.createAction(user,"transfer","Transfer performed between " + accountFrom.getClientId()+ " and " + accountTo.getClientId()+ " with the sum:" + sum);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        this.employeeView.getDelete().setOnAction(actionEvent -> {
            client =(Client) this.employeeView.getClients().getSelectionModel().getSelectedItem();
            actionService.deleteClient(client);
            try {
                actionService.createAction(user,"delete client","User " + user.getUsername() +" deleted the client: "+ client.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.employeeView.getClients().getItems().remove(client);
        });

        this.employeeView.getUpdate().setOnAction(actionEvent -> {
            client = (Client) this.employeeView.getClients().getSelectionModel().getSelectedItem();
            stage.setScene(createClientScene);

        });

        this.accountView.getProcessBills().setOnAction(actionEvent -> {
            Long sumToPay = Long.parseLong(this.accountView.getSum().getText());
            Account account = this.accountView.getAccounts().getSelectionModel().getSelectedItem();
            Long newSum = account.getBalance() - sumToPay;
            actionService.updateAccount("balance",account,null,null,newSum);
            try {
                actionService.createAction(user,"process bills","bill processed from account: "+account.getClientId() );
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        this.createClientView.getUpdate().setOnAction(actionEvent -> {
            String category = this.createClientView.getCategory().getSelectionModel().getSelectedItem();
            String name = this.createClientView.getName().getText();
            String cardId = this.createClientView.getCardId().getText();
            String pnc = this.createClientView.getPersonalNumericalCode().getText();
            String address = this.createClientView.getAddress().getText();
            actionService.updateClient(category,client,name,cardId,pnc,address);

            try {
                actionService.createAction(user,"update client ","User " + user.getUsername() +" updated the client: "+ client.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            stage.setScene(employeeScene);
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package Controller;

import Model.Action;
import Model.Builder.ActionBuilder;
import Model.Builder.ClientAccountBuilder;
import Model.Builder.ClientBuilder;
import Model.Client;
import Model.ClientAccount;
import Model.User;
import Model.Validator.ClientAccountValidator;
import Model.Validator.ClientValidator;
import Repository.Action.ActionRepositoryMySQL;
import Repository.ClientAccount.ClientAccountRepository;
import Repository.ClientAccount.ClientAccountRepositoryMySQL;
import Service.User.RegularUserServiceMySQL;
import View.EmployeeView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final RegularUserServiceMySQL regularUserServiceMySQL;
    private final ActionRepositoryMySQL actionRepositoryMySQL;
    private Client client;
    private ClientAccount clientAccount;
    private User user;
    private final ClientValidator clientValidator;
    private final ClientAccountValidator clientAccountValidator;

    public EmployeeController(EmployeeView employeeView, RegularUserServiceMySQL regularUserServiceMySQL, ActionRepositoryMySQL actionRepositoryMySQL, User user, ClientValidator clientValidator, ClientAccountValidator clientAccountValidator){
        this.employeeView = employeeView;
        this.regularUserServiceMySQL = regularUserServiceMySQL;
        this.actionRepositoryMySQL = actionRepositoryMySQL;
        this.user = user;
        this.clientValidator = clientValidator;
        this.clientAccountValidator = clientAccountValidator;
        this.employeeView.setVisible(true);

        employeeView.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientValidator.validate(employeeView.getClientName().getText(),Long.parseLong(employeeView.getClientIdentityCardNumber().getText()),employeeView.getPersonalNumericalCode().getText(),employeeView.getAddress().getText());
                if(clientValidator.getErrors().isEmpty()) {
                    Client client = new ClientBuilder()
                            .setName(employeeView.getClientName().getText())
                            .setAddress(employeeView.getAddress().getText())
                            .setIdentityCardNumber(Long.parseLong(employeeView.getClientIdentityCardNumber().getText()))
                            .setPersonalNumericalCode(employeeView.getPersonalNumericalCode().getText())
                            .build();
                    regularUserServiceMySQL.addClient(client);
                    employeeView.getClientTableView().getItems().clear();
                    employeeView.getClientTableView().getItems().addAll(regularUserServiceMySQL.findClients());
                    LocalDateTime date = LocalDateTime.now();
                    Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                    Date actionDate = Date.from(instant);
                    Action action = new ActionBuilder().setActivity("add_client").setId(user.getId()).setActionDate(actionDate).build();
                    actionRepositoryMySQL.save(action);
                }
                else{
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setContentText(clientValidator.getFormattedErrors());
                    error.show();
                }
            }
        });

        employeeView.getToEdit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                client = employeeView.getClientTableView().getSelectionModel().getSelectedItem();
                employeeView.getClientName().setText(client.getName());
                employeeView.getAddress().setText(client.getAddress());
                employeeView.getClientIdentityCardNumber().setText(client.getIdentityCardNumber().toString());
                employeeView.getPersonalNumericalCode().setText(client.getPersonalNumericalCode());
            }
        });

        employeeView.getEdit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Client clientEdited = new ClientBuilder()
                        .setId(client.getId())
                        .setName(employeeView.getClientName().getText())
                        .setAddress(employeeView.getAddress().getText())
                        .setIdentityCardNumber(Long.parseLong(employeeView.getClientIdentityCardNumber().getText()))
                        .setPersonalNumericalCode(employeeView.getPersonalNumericalCode().getText())
                        .build();
                regularUserServiceMySQL.editClient(clientEdited);
                employeeView.getClientTableView().getItems().clear();
                employeeView.getClientTableView().getItems().addAll(regularUserServiceMySQL.findClients());
                LocalDateTime date = LocalDateTime.now();
                Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("edit_client").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
            }
        });

        employeeView.getView().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //employeeView.getChildren().remove(employeeView.getClientAccountTableView());
                //employeeView.getChildren().add(employeeView.getClientTableView());
                List<Client> clientList = regularUserServiceMySQL.findClients();
                employeeView.getClientTableView().getItems().addAll(clientList);
                LocalDateTime date = LocalDateTime.now();
                Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("view_clients").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
            }
        });

        employeeView.getCreateAccount().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate localDate = employeeView.getCreationDate().getValue();
                Calendar calendar = Calendar.getInstance();
                calendar.set(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth());
                Date date = calendar.getTime();
                clientAccountValidator.validate(Long.parseLong(employeeView.getAccountIdentificationNumber().getText()), employeeView.getAccountType().getText(),Integer.parseInt(employeeView.getMoney().getText()));
                if(clientAccountValidator.getErrors().isEmpty()) {
                    clientAccount = new ClientAccountBuilder()
                            .setId(Long.parseLong(employeeView.getAccountId().getText()))
                            .setCreationDate(date)
                            .setMoney(Integer.parseInt(employeeView.getMoney().getText()))
                            .setType(employeeView.getAccountType().getText())
                            .setIdentificationNumber(Long.parseLong(employeeView.getAccountIdentificationNumber().getText()))
                            .build();
                    regularUserServiceMySQL.addClientAccount(clientAccount);
                    LocalDateTime date1 = LocalDateTime.now();
                    Instant instant = date1.atZone(ZoneId.systemDefault()).toInstant();
                    Date actionDate = Date.from(instant);
                    Action action = new ActionBuilder().setActivity("create_account").setId(user.getId()).setActionDate(actionDate).build();
                    actionRepositoryMySQL.save(action);
                    System.out.println("here");
                }
                else{
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setContentText(clientValidator.getFormattedErrors());
                    error.show();
                }
                employeeView.getClientAccountTableView().getItems().clear();
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
            }
        });

        employeeView.getViewAccounts().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                employeeView.getChildren().remove(employeeView.getClientTableView());
                employeeView.getChildren().add(employeeView.getClientAccountTableView());
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
                for(ClientAccount c : regularUserServiceMySQL.findClientAccounts()){
                    employeeView.getAccount1().getItems().add(c);
                    employeeView.getAccount1().setConverter(new StringConverter<ClientAccount>() {
                        @Override
                        public String toString(ClientAccount clientAccount) {
                            return clientAccount.getId().toString();
                        }

                        @Override
                        public ClientAccount fromString(String s) {
                            return null;
                        }
                    });
                    employeeView.getAccount2().getItems().add(c);
                    employeeView.getAccount2().setConverter(new StringConverter<ClientAccount>() {
                        @Override
                        public String toString(ClientAccount clientAccount) {
                            return clientAccount.getId().toString();
                        }

                        @Override
                        public ClientAccount fromString(String s) {
                            return null;
                        }
                    });
                }
                LocalDateTime date = LocalDateTime.now();
                Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("view_client_accounts").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
            }
        });

        employeeView.getDeleteAccount().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                regularUserServiceMySQL.deleteClientAccount(employeeView.getClientAccountTableView().getSelectionModel().getSelectedItem());
                employeeView.getClientAccountTableView().getItems().clear();
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
                LocalDateTime date = LocalDateTime.now();
                Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("delete_client_accounts").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
            }
        });

        employeeView.getToEditAccount().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientAccount = employeeView.getClientAccountTableView().getSelectionModel().getSelectedItem();
                employeeView.getAccountIdentificationNumber().setText(clientAccount.getIdentificationNumber().toString());
                employeeView.getAccountType().setText(clientAccount.getType());
                employeeView.getMoney().setText(String.valueOf(clientAccount.getMoney()));
            }
        });

        employeeView.getEditAccount().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate localDate = employeeView.getCreationDate().getValue();
                Calendar calendar = Calendar.getInstance();
                calendar.set(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth());
                Date date = calendar.getTime();
                ClientAccount clientAccount1 = new ClientAccountBuilder()
                        .setCreationDate(date)
                        .setId(clientAccount.getId())
                        .setMoney(Integer.parseInt(employeeView.getMoney().getText()))
                        .setType(employeeView.getAccountType().getText())
                        .setIdentificationNumber(Long.parseLong(employeeView.getAccountIdentificationNumber().getText()))
                        .build();
                regularUserServiceMySQL.editClientAccount(clientAccount1);
                employeeView.getClientAccountTableView().getItems().clear();
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
                LocalDateTime date1 = LocalDateTime.now();
                Instant instant = date1.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("edit_client_accounts").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
            }
        });

        employeeView.getMakeTransaction().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                regularUserServiceMySQL.TransferMoney(employeeView.getAccount1().getSelectionModel().getSelectedItem(),employeeView.getAccount2().getSelectionModel().getSelectedItem(),Integer.parseInt(employeeView.getAmount().getText()));
                LocalDateTime date = LocalDateTime.now();
                Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
                Date actionDate = Date.from(instant);
                Action action = new ActionBuilder().setActivity("transaction_client_accounts").setId(user.getId()).setActionDate(actionDate).build();
                actionRepositoryMySQL.save(action);
                employeeView.getClientAccountTableView().getItems().clear();
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
            }
        });

        employeeView.getPayBill().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientAccount = employeeView.getAccount1().getSelectionModel().getSelectedItem();
                int amount = Integer.parseInt(employeeView.getAmount().getText());
                regularUserServiceMySQL.processBills(clientAccount,amount);
                employeeView.getClientAccountTableView().getItems().clear();
                employeeView.getClientAccountTableView().getItems().addAll(regularUserServiceMySQL.findClientAccounts());
            }
        });

    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import model.Account;
import model.Client;
import model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UserController {
    @FXML
    TextField cidTF;

    @FXML
    TextField cnameTF;

    @FXML
    TextField cidCardNoTF;

    @FXML
    TextField ccnpTF;

    @FXML
    TextField caddressTF;

    @FXML
    Button cAddB;

    @FXML
    Button cDeleteB;

    @FXML
    Button cViewB;

    @FXML
    Button cUpdateB;

    @FXML
    TextField aidTF;

    @FXML
    TextField aBalanceTF;

    @FXML
    Button aAddB;

    @FXML
    Button aDeleteB;

    @FXML
    Button aViewB;

    @FXML
    Button aUpdateB;

    @FXML
    DatePicker date;

    @FXML
    TextField aTypeTF;

    @FXML
    TextField tIdA1TF;

    @FXML
    TextField tIdA2TF;

    @FXML
    Button transferB;

    @FXML
    TextField tSumTF;

    @FXML
    TextField bIdAccTF;

    @FXML
    TextField bSumTF;

    @FXML
    Button payB;

    @FXML
    TextArea printTA;

    private String clientName,clientIdCardNo,clientCNP,clientAddress;
    long clientId;

    private String accountBalance1,accountType;
    long accountId,accountBalance;
    private Date dateOfCreation;

    ConnectionStuff con = new ConnectionStuff();

    Long currentUserId;
    String activity;
    String currentUsername;
    private void getCurrentUser(){
        currentUsername = con.getCurrentUserUsername();
        System.out.println(currentUsername);
        currentUserId = con.getAuthenticationService().viewByUsername(currentUsername).getId();
    }

    public void addClientButtonOnAction(){
        getDataClient();
        con.getClientValidator().validate(clientName, clientCNP,clientIdCardNo,clientAddress);
        final List<String> errors = con.getClientValidator().getErrors();
        if (errors.isEmpty()) {
            con.getClientService().add(clientName,clientIdCardNo,clientCNP,clientAddress);

            getCurrentUser();
            activity = String.format("User with id %d  added new client %s",currentUserId,clientName);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);

        } else {
            dialogue(con.getClientValidator().getFormattedErrors());
        }

    }

    public void addAccountButtonOnAction(){
        getDataAccount();
        con.getAccountValidator().validate(aBalanceTF.getText(),accountType,dateOfCreation,cidTF.getText());
        final List<String> errors = con.getAccountValidator().getErrors();
        if (errors.isEmpty()) {
            con.getAccountService().add(accountBalance,accountType,dateOfCreation,clientId);

            getCurrentUser();
            activity = String.format("User with id %d  added new account to user %d",currentUserId,clientId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);

        } else {
            dialogue(con.getAccountValidator().getFormattedErrors());
        }
    }

    public void deleteClientButtonOnAction(){
        try{
            clientId = Long.parseLong(cidTF.getText());
            if(!con.getClientService().delete(clientId)){
                dialogue("Client with this id not found");
            }

            getCurrentUser();
            activity = String.format("User with id %d  deleted client %d",currentUserId,clientId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);

        } catch(NumberFormatException ex){ // handle your exception
            System.out.println("Empty id field or not a number");
        }

    }

    public void deleteAccountButtonOnAction(){
        try{
            accountId = Long.parseLong(aidTF.getText());
            if(!con.getAccountService().delete(accountId)){
                dialogue("Account with this id not found");
            }
            getCurrentUser();
            activity = String.format("User with id %d  deleted account %d",currentUserId,accountId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);

        } catch(NumberFormatException ex){ // handle your exception
            System.out.println("Empty id field or not a number");
        }


    }

    public void updateClientButtonOnAction(){
        getDataClient();
        con.getClientValidator().validate(clientName, clientCNP,clientIdCardNo,clientAddress);
        final List<String> errors = con.getClientValidator().getErrors();
        if (errors.isEmpty()) {
            if(!con.getClientService().update(clientName,clientIdCardNo,clientCNP,clientAddress,clientId)){
                dialogue("Client with this id not found");
            }
            getCurrentUser();
            activity = String.format("User with id %d  updated client %d",currentUserId,clientId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);


        } else {
            dialogue(con.getClientValidator().getFormattedErrors());
        }
    }

    public void updateAccountButtonOnAction(){
        getDataAccount();
        con.getAccountValidator().validate(aBalanceTF.getText(),accountType,dateOfCreation,cidTF.getText());
        final List<String> errors = con.getClientValidator().getErrors();
        if (errors.isEmpty()) {
            if(!con.getAccountService().update(accountId,accountBalance,accountType,dateOfCreation,clientId)){
                dialogue("Account with this id not found");
            }
            getCurrentUser();
            activity = String.format("User with id %d  updated account %d",currentUserId,accountId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);


        } else {
            dialogue(con.getClientValidator().getFormattedErrors());
        }
    }

    public void viewClientButtonOnAction(){
        List<Client> clients = con.getClientService().view();
        StringBuilder sb = new StringBuilder();
            if(!clients.isEmpty()){
                for(Client c : clients)
                sb.append(String.format("Client with id %d : name %s, identity card number %s, cnp %s and address %s\n",c.getId(),c.getName(),c.getIdCardNumber(),c.getCnp(),c.getAddress()));
            }
         printTA.setText(sb.toString());

    }

    public void viewAccountButtonOnAction(){
        List<Account> accounts = con.getAccountService().view();
        StringBuilder sb = new StringBuilder();
        if(!accounts.isEmpty()){
            for(Account c : accounts)
                sb.append(String.format("Account with id %d : balance %d, type %s, dateOfCreation %s and clien_id %d\n",c.getId(),c.getBalance(),c.getType(),c.getDateOfCreation().toString(),c.getClient_id()));
        }
        printTA.setText(sb.toString());

    }

    private void getDataClient(){
        try{
            clientId = Long.parseLong(cidTF.getText());
        } catch(NumberFormatException ex){ // handle your exception
            System.out.println("Empty id field");
        }
        clientName = cnameTF.getText();
        clientIdCardNo = cidCardNoTF.getText();
        clientCNP = ccnpTF.getText();
        clientAddress = caddressTF.getText();
    }

    private void getDataAccount(){
        try {
            accountId = Long.parseLong(aidTF.getText());
            accountBalance = Long.parseLong(aBalanceTF.getText());
            clientId = Long.parseLong(cidTF.getText());
        }
        catch (NumberFormatException ex){
            dialogue("Fields empty or not numbers");
        }

        accountType = aTypeTF.getText();
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        dateOfCreation = Date.from(instant);




    }

    public void transferButtonOnAction(){
        Long transferIdA1,transferIdA2,transferSum;
        try {
            transferIdA1 = Long.parseLong(tIdA1TF.getText());
            transferIdA2 = Long.parseLong(tIdA2TF.getText());
            transferSum = Long.parseLong(tSumTF.getText());
            Account A1 = con.getAccountService().viewAccount(transferIdA1);
            Account A2 = con.getAccountService().viewAccount(transferIdA2);

            A1.transferMoney(transferSum);
            A2.receiveMoney(transferSum);

            if(!con.getAccountService().update(A1.getId(), A1.getBalance(),A1.getType(),A1.getDateOfCreation(),A1.getClient_id())){
                dialogue("Account with this id not found");
            }
            if(!con.getAccountService().update(A2.getId(), A2.getBalance(),A2.getType(),A2.getDateOfCreation(),A2.getClient_id())){
                dialogue("Account with this id not found");
            }
            getCurrentUser();
            activity = String.format("User with id %d transfered money between account %d and account %d",currentUserId,transferIdA1,transferIdA2);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);

        } catch (NumberFormatException ex){
            dialogue("Transfer necessay data is empty or not numbers");
        }
    }

    public void payButtonOnAction(){
        long billIdAcc,billsSum;
        try{
            billIdAcc = Long.parseLong(bIdAccTF.getText());
            billsSum = Long.parseLong(bSumTF.getText());

            Account account = con.getAccountService().viewAccount(billIdAcc);
            account.transferMoney(billsSum);

            if(!con.getAccountService().update(account.getId(), account.getBalance(),account.getType(),account.getDateOfCreation(),account.getClient_id())){
                dialogue("Account with this id not found");
            }
            getCurrentUser();
            activity = String.format("User with id %d  make a payment on account %d",currentUserId,accountId);
            con.getActivityUserService().add(currentUserId,activity,new Date(),currentUsername);


        }
        catch(NumberFormatException e){
            dialogue("Payment data is empty or not numbers");
        }
    }

    private void dialogue(String information){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

}

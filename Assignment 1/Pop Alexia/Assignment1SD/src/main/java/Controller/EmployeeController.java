package Controller;

import Model.User;
import Model.Validator.AccountValidator;
import Model.Validator.ClientValidator;
import Service.Account.AccountService;
import Service.Client.ClientService;
import Service.Event.EventService;
import View.EmployeeView;
import javafx.collections.ObservableList;

import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final EventService eventService;
    private final AccountService accountService;
    private final ClientService clientService;
    private final AccountValidator accountValidator;
    private final ClientValidator clientValidator;
    private User loggedUser;


    public EmployeeController(User loggedUser,EmployeeView employeeView, EventService eventService, AccountService accountService, ClientService clientService, AccountValidator accountValidator, ClientValidator clientValidator) {
        this.employeeView = employeeView;
        this.eventService = eventService;
        this.accountService = accountService;
        this.clientService = clientService;
        this.accountValidator = accountValidator;
        this.clientValidator = clientValidator;
        this.loggedUser = loggedUser;

        addClientAction();
        updateClientAction();
        viewClientsAction();
        addAccountAction();
        updateAccountAction();
        viewAccountsAction();
        deleteAccountAction();
        payBillAction();
        transferMoneyAction();

    }

    public void addAccountAction(){
        employeeView.getAddAccount().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String accNr = employeeView.getAccountNr().getText();
            String type = employeeView.getType().getText();
            String amount = employeeView.getAmount().getText();

            accountValidator.validate(accNr,type,amount);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountService.addAccount(Long.parseLong(accNr),type,Integer.parseInt(amount));
                eventService.addEvent(loggedUser.getUsername(),"added account");
            } else employeeView.getErrorMsg().setText(accountValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void deleteAccountAction(){
        employeeView.getDeleteAccount().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String cardnr = employeeView.getAccountNr().getText();

            accountValidator.validateDelete(cardnr);
            final List<String> errors = accountValidator.getErrors();
            if(errors.isEmpty()){
                accountService.deleteAccount(Long.parseLong(cardnr));
                eventService.addEvent(loggedUser.getUsername(),"deleted account");
            } else employeeView.getErrorMsg().setText(accountValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void viewAccountsAction(){
        employeeView.getViewAccounts().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            employeeView.getVbox().getChildren().clear();
            clearFieldsEmployee();
            employeeView.getVbox().getChildren().add(employeeView.createTable(accountService.viewAccounts()));
            eventService.addEvent(loggedUser.getUsername(),"viewed accounts");
        });
    }

    public void updateAccountAction(){
        employeeView.getUpdateAccount().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String accnr = employeeView.getAccountNr().getText();
            String type = employeeView.getType().getText();
            String amount = employeeView.getAmount().getText();

            if(amount.equals("")) amount ="-1";

            accountValidator.validateUpdate(accnr,type,amount);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountService.updateAccount(Long.parseLong(accnr),type,Integer.parseInt(amount));
                eventService.addEvent(loggedUser.getUsername(),"updated account");
            } else employeeView.getErrorMsg().setText(accountValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void transferMoneyAction(){
        employeeView.getTransferMoney().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String accnr1 = employeeView.getAccountFrom().getText();
            String accnr2 = employeeView.getAccountTo().getText();
            String amount = employeeView.getAmount2().getText();

            if(amount.equals("")) amount ="-1";
            accountValidator.validateTransaction(accnr1,accnr2,amount);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountService.transferMoney(Long.parseLong(accnr1),Long.parseLong(accnr2),Integer.parseInt(amount));
                eventService.addEvent(loggedUser.getUsername(),"transfered money");
            } else employeeView.getErrorMsg().setText(accountValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void payBillAction(){
        employeeView.getProcessBills().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String accnr = employeeView.getAccountNr2().getText();
            String billPrice = employeeView.getBillPrice().getText();

            if(billPrice.equals("")) billPrice ="-1";

            accountValidator.validatePayment(accnr,billPrice);
            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                accountService.payBill(Long.parseLong(accnr),Integer.parseInt(billPrice));
                eventService.addEvent(loggedUser.getUsername(),"payed bills");
            } else employeeView.getErrorMsg().setText(accountValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void addClientAction(){
        employeeView.getAddClient().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String name = employeeView.getClientName().getText();
            String cardnr = employeeView.getCardNr().getText();
            String pnc = employeeView.getPnc().getText();
            String address = employeeView.getAddress().getText();

            clientValidator.validate(cardnr,name,pnc,address);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientService.addClient(name,Long.parseLong(cardnr),Long.parseLong(pnc),address);
                eventService.addEvent(loggedUser.getUsername(),"added client");
            } else employeeView.getErrorMsg().setText(clientValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void viewClientsAction(){
        employeeView.getViewClients().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            employeeView.getVbox().getChildren().clear();
            clearFieldsEmployee();
            employeeView.getVbox().getChildren().add(employeeView.createTable((ObservableList<?>) clientService.viewClients()));
            eventService.addEvent(loggedUser.getUsername(),"viewed user");
        });
    }

    public void updateClientAction(){
        employeeView.getUpdateClient().setOnAction(e->{
            employeeView.getErrorMsg().setText("");
            String id = employeeView.getClientId().getText();
            String name = employeeView.getClientName().getText();
            String pnc = employeeView.getPnc().getText();
            String address = employeeView.getAddress().getText();

            if(pnc.equals("")) pnc ="-1";

            clientValidator.validateUpdate(id,name,pnc,address);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientService.updateClient(Long.parseLong(id),name,Long.parseLong(pnc),address);
                eventService.addEvent(loggedUser.getUsername(),"updated client");
            } else employeeView.getErrorMsg().setText(clientValidator.getFormattedErrors());

            clearFieldsEmployee();
        });
    }

    public void clearFieldsEmployee(){
        employeeView.getClientName().clear();
        employeeView.getClientId().clear();
        employeeView.getAddress().clear();
        employeeView.getPnc().clear();
        employeeView.getBillPrice().clear();
        employeeView.getAccountNr2().clear();
        employeeView.getAccountTo().clear();
        employeeView.getAccountFrom().clear();
        employeeView.getType().clear();
        employeeView.getAccountNr().clear();
        employeeView.getAmount().clear();
        employeeView.getCardNr().clear();
        employeeView.getAmount2().clear();
    }
}

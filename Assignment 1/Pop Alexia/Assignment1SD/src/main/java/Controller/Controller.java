package Controller;

import Model.Event;
import Model.Role;
import Model.User;
import Model.Validator.AccountValidator;
import Model.Validator.ClientValidator;
import Model.Validator.EventValidator;
import Model.Validator.UserValidator;
import Service.Account.AccountService;
import Service.Client.ClientService;
import Service.Employee.EmployeeService;
import Service.Event.EventService;
import Service.Secutiry.AuthenticationService;
import View.AdminView;
import View.EmployeeView;
import View.LoginView;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static Database.Constants.Roles.*;

public class Controller {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final EventService eventService;
    private final EmployeeService employeeService;
    private final AccountService accountService;
    private final ClientService clientService;
    private final UserValidator userValidator;
    private final AccountValidator accountValidator;
    private final ClientValidator clientValidator;
    private final EventValidator eventValidator;
    private User loggedUser;

    public Controller(LoginView loginView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService, EventService eventService, EmployeeService employeeService, AccountService accountService, ClientService clientService, UserValidator userValidator, AccountValidator accountValidator, ClientValidator clientValidator, EventValidator eventValidator) {
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
        this.eventService = eventService;
        this.employeeService = employeeService;
        this.accountService = accountService;
        this.clientService = clientService;
        this.userValidator = userValidator;
        this.loginView = loginView;
        this.accountValidator = accountValidator;
        this.clientValidator = clientValidator;
        this.eventValidator = eventValidator;

        loginAction();
        logOutAction();
        addClientAction();
        updateClientAction();
        viewClientsAction();
        addAccountAction();
        updateAccountAction();
        viewAccountsAction();
        deleteAccountAction();
        payBillAction();
        transferMoneyAction();
        addEmployeeAction();
        updateEmployeeAction();
        deleteEmployeeAction();
        viewEmployeesAction();
        generateReportAction();
    }

    public void loginAction() {
        loginView.getLogin().setOnAction(e->{
            loginView.getNoSuchUserMsg().setText("");
            String username = loginView.getUsername().getText();
            String password = loginView.getPassword().getText();

            userValidator.validateLogin(username, password);
            loggedUser = authenticationService.login(username, password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()){
                if(loggedUser == null) loginView.getNoSuchUserMsg().setText("Wrong password");
                else {
                    Optional<Role> role = loggedUser.getRoles().stream().filter(r -> r.getRole().equals(ADMINISTRATOR)).findFirst();
                    if (role.isPresent()) loginView.changeView(adminView.getMainScene());
                    else loginView.changeView(employeeView.getMainScene());
                }
            }else loginView.getNoSuchUserMsg().setText(userValidator.getFormattedErrors());

            loginView.getUsername().clear();
            loginView.getPassword().clear();
        });
    }

    public void logOutAction(){
        employeeView.getLogOut().setOnAction(e-> loginView.changeView(loginView.getMainScene()));
        adminView.getLogOut().setOnAction(e->loginView.changeView(loginView.getMainScene()));
    }

    public void addEmployeeAction(){
        adminView.getAddEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String username = adminView.getUsername().getText();
            String password = adminView.getPassword().getText();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) authenticationService.register(username, password);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void deleteEmployeeAction(){
        adminView.getDeleteEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String username = adminView.getUsername().getText();

            userValidator.validateDelete(username);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) employeeService.deleteEmp(username);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void updateEmployeeAction(){
        adminView.getUpdateEmployee().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            String id = adminView.getEmployeeId().getText();
            String username = adminView.getUsername().getText();
            String password = adminView.getPassword().getText();

            userValidator.validateUpdate(id,username,password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) employeeService.updateEmp(Long.parseLong(id),username,password);
            else adminView.getErrorMsg().setText(userValidator.getFormattedErrors());
            clearFieldsAdmin();
        });
    }

    public void viewEmployeesAction(){
        adminView.getViewEmployees().setOnAction(e->{
            adminView.getErrorMsg().setText("");
            adminView.getVbox().getChildren().clear();
            clearFieldsAdmin();
            adminView.getVbox().getChildren().add(adminView.createTable(employeeService.viewEmp()));
        });
    }

    public void generateReportAction(){
        adminView.getGenerateReport().setOnAction(e->{
            adminView.getVbox().getChildren().clear();
            adminView.getErrorMsg().setText("");
            String dateFrom = adminView.getDateFrom().getText();
            String dateTo = adminView.getDateTo().getText();
            String username = adminView.getUsername2().getText();

            eventValidator.validate(username,dateFrom,dateTo);
            final List<String> errors = eventValidator.getErrors();
            if(errors.isEmpty()){
                ObservableList<Event> events = eventService.findByUsernameAndDates(Date.valueOf(dateFrom),Date.valueOf(dateTo),username);
                adminView.getVbox().getChildren().add(adminView.createTable(events));
            }else adminView.getErrorMsg().setText(eventValidator.getFormattedErrors());
         clearFieldsAdmin();
        });
    }

    public void clearFieldsAdmin(){
        adminView.getEmployeeId().clear();
        adminView.getUsername().clear();
        adminView.getPassword().clear();
        adminView.getUsername2().clear();
        adminView.getDateTo().clear();
        adminView.getDateFrom().clear();
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
package controller;

import model.*;
import model.builder.ClientBuilder;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import respository.client.ClientRepository;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import view.RowFilterUtil;
import view.admin.AdminIndexView;
import view.employee.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private final EmployeeIndexView employeeIndexView;
    private final EmployeeAddClientView employeeAddClientView;
    private final EmployeeUpdateClientView employeeUpdateClientView;
    private final EmployeeAddAccountView employeeAddAccountView;
    private final EmployeeUpdateAccountView employeeUpdateAccountView;
    private final EmployeeTransferMoneyView employeeTransferMoneyView;
    private final EmployeeProcessBillsView employeeProcessBillsView;

    private final ClientValidator clientValidator;
    private final ClientService clientService;

    private final ActivityService activityService;
    private final String[] activities = {"Add client", "Update client", "Add client account", "Update client account",
            "Delete client account", "Transfer money", "Process utility bill"};

    private final AccountValidator accountValidator;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;


    public EmployeeController(ClientService clientService, ClientValidator clientValidator,
                              EmployeeIndexView employeeIndexView, EmployeeAddClientView employeeAddClientView,
                              EmployeeUpdateClientView employeeUpdateClientView, EmployeeAddAccountView employeeAddAccountView,
                              AccountValidator accountValidator, AccountService accountService,
                              EmployeeUpdateAccountView employeeUpdateAccountView, EmployeeTransferMoneyView employeeTransferMoneyView,
                              EmployeeProcessBillsView employeeProcessBillsView, ActivityService activityService, AuthenticationService authenticationService){
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.employeeIndexView = employeeIndexView;
        this.employeeAddClientView = employeeAddClientView;
        this.employeeUpdateClientView = employeeUpdateClientView;
        this.employeeAddAccountView = employeeAddAccountView;
        this.accountValidator = accountValidator;
        this.accountService = accountService;
        this.employeeUpdateAccountView = employeeUpdateAccountView;
        this.employeeTransferMoneyView = employeeTransferMoneyView;
        this.employeeProcessBillsView = employeeProcessBillsView;
        this.activityService = activityService;
        this.authenticationService = authenticationService;

        this.employeeIndexView.addClientViewBtnListener(new CreateClientViewBtnListener());
        this.employeeAddClientView.addClientBtnListener(new AddClientBtnListener());
        this.employeeIndexView.addUpdateClientBtnListener(new UpdateClientViewBtnListener());
        this.employeeUpdateClientView.addSearchClientBtnListener(new SearchClientBtnListener());
        this.employeeUpdateClientView.addEditClientBtnListener(new EditClientBtnListener());
        this.employeeIndexView.addBtnCreateAccountListener(new CreateAccountViewBtnListener());
        this.employeeAddAccountView.addAccountBtnListener(new AddAccountBtnListener());
        this.employeeIndexView.addBtnUpdateAccountListener(new UpdateAccountViewBtnListener());
        this.employeeUpdateAccountView.searchAccountBtnListener(new SearchAccountBtnListener());
        this.employeeUpdateAccountView.updateAccountBtnListener(new UpdateAccountBtnListener());
        this.employeeUpdateAccountView.deleteAccountBtnListener(new DeleteAccountBtnListener());
        this.employeeIndexView.addBtnTransferMoneyListener(new TransferMoneyViewBtnListener());
        this.employeeTransferMoneyView.transferMoneyBtnListener(new TransferMoneyBtnListener());
        this.employeeIndexView.addBtnProcessBillsListener(new ProcessBillsViewBtnListener());
        this.employeeProcessBillsView.processBillsBtnListener(new ProcessBillsBtnListener());
    }

    private class CreateClientViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeAddClientView.setVisible(true);
        }
    }

    private class AddClientBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeAddClientView.getName();
            String idCardNr = employeeAddClientView.getIdCardNr();
            String PNC = employeeAddClientView.getPNC();
            String address = employeeAddClientView.getAddress();
            String email = employeeAddClientView.getEmail();

            clientValidator.validate(name, idCardNr, PNC, address, email);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                if(clientService.addClient(name, idCardNr, PNC, address, email)){
                    JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), "Client added successfully");
                    Activity activity = activityService.save("Added client");
                    activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                }else{
                    JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), "Something went wrong");
                }

            } else {
                JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateClientViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeUpdateClientView.setVisible(true);
        }
    }

    private class SearchClientBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String PNCStr = employeeUpdateClientView.getTfPNC();

            if(clientValidator.validatePNC(PNCStr) == null){
                JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Personal numerical code is not valid");
            }else{
                Long PNC = Long.parseLong(PNCStr);
                Client client = clientService.findByPNC(PNC);

                if(client == null){
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Client not found");

                }else{
                    employeeUpdateClientView.setClientData(client.getName(), client.getIdCardNr(), client.getAddress(), client.getEmail());
                }
            }

        }
    }

    private class EditClientBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String PNCStr = employeeUpdateClientView.getTfPNC();
            String idCardNrStr = employeeUpdateClientView.getTfIdCardNr();
            String address = employeeUpdateClientView.getTfAddress();
            String email = employeeUpdateClientView.getTfEmail();
            String name = employeeUpdateClientView.getTfName();


            clientValidator.validateUpdate(idCardNrStr, PNCStr, email);
            final List<String> errors = clientValidator.getErrors();

            if (errors.isEmpty()){
                Long PNC = Long.parseLong(PNCStr);
                Long idCardNr = Long.parseLong(idCardNrStr);

                Client client = new ClientBuilder()
                        .setName(name)
                        .setIdCardNr(idCardNr)
                        .setPNC(PNC)
                        .setAddress(address)
                        .setEmail(email)
                        .build();

                if(clientService.updateClient(client)){
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Client updated");
                    Activity activity = activityService.save("Edited client");
                    activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                }else{
                    JOptionPane.showMessageDialog(employeeUpdateClientView.getContentPane(), "Something went wrong");
                }
            }else{
                JOptionPane.showMessageDialog(employeeAddClientView.getContentPane(), clientValidator.getFormattedErrors());

            }
        }
    }


    private class CreateAccountViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeAddAccountView.displayClients(clientService.getAllClients());
            employeeAddAccountView.setVisible(true);
        }
    }

    private class AddAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String client_id = employeeAddAccountView.getTfClient_id();
            String type = employeeAddAccountView.getTfType();
            String amountStr = employeeAddAccountView.getTfAmount();

            accountValidator.validate(client_id, amountStr);
            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()){
                int clientId = Integer.parseInt(client_id);
                Long amount = Long.parseLong(amountStr);

                if(accountService.addAccount(clientId, type, amount)){
                    JOptionPane.showMessageDialog(employeeAddAccountView.getContentPane(), "Account added successfully");
                    Activity activity = activityService.save("Added account");
                    activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                }else{
                    JOptionPane.showMessageDialog(employeeAddAccountView.getContentPane(), "Something went wrong");
                }
            }else{
                JOptionPane.showMessageDialog(employeeAddAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }

        }
    }


    private class UpdateAccountViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeUpdateAccountView.displayAccounts(accountService.getAllAccounts());
            employeeAddAccountView.displayClients(clientService.getAllClients());
            employeeUpdateAccountView.setVisible(true);
        }
    }

    private class SearchAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = employeeUpdateAccountView.getTfAccountId();

            if(accountValidator.validateClientOrAccountId(accountId) == -1){
                JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Account id is not valid");
            }else{
                int account_id = Integer.parseInt(accountId);
                Account account = accountService.findById(account_id);

                if(account == null){
                    JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Account not found");
                }else{
                    employeeUpdateAccountView.setAccountData(account.getType(), account.getAmount());
                }
            }

        }
    }

    private class UpdateAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeUpdateAccountView.getTfType();
            String amountStr = employeeUpdateAccountView.getTfAmount();
            String accountId = employeeUpdateAccountView.getTfAccountId();

            accountValidator.resetErrorsArray();
            accountValidator.validateAmount(amountStr);
            accountValidator.validateClientOrAccountId(accountId);
            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()){
                Long amount = Long.parseLong(amountStr);
                int account_id = Integer.parseInt(accountId);

                Account account = accountService.findById(account_id);
                if(account != null){
                    account.setType(type);
                    account.setAmount(amount);
                    if(accountService.updateAccount(account)){

                        JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Account updated");
                        Activity activity = activityService.save("Updated account");
                        activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                    }else{
                        JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Something went wrong");
                    }
                }else{
                    JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "This account does not exist");
                }

            }else{
                JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }

        }
    }

    private class DeleteAccountBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountId = employeeUpdateAccountView.getTfAccountId();
            accountValidator.resetErrorsArray();
            if(accountValidator.validateClientOrAccountId(accountId) != -1){
                int account_id = Integer.parseInt(accountId);
                Account account = accountService.findById(account_id);

                if(account != null){
                    if(accountService.removeAccount(account)){
                        JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Account deleted successfully");
                        Activity activity = activityService.save("Deleted account");
                        activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                    }else{
                        JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Something went wrong");
                    }
                }else{
                    JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "This account does not exist");
                }
            }else{
                JOptionPane.showMessageDialog(employeeUpdateAccountView.getContentPane(), "Account id is not valid");
            }
        }
    }


    private class TransferMoneyViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeUpdateAccountView.displayAccounts(accountService.getAllAccounts());
            employeeTransferMoneyView.setVisible(true);
        }
    }

    private class TransferMoneyBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromAccountId = employeeTransferMoneyView.getTfFrom();
            String toAccountId = employeeTransferMoneyView.getTfTo();
            String amountStr = employeeTransferMoneyView.getTfAmount();

            accountValidator.resetErrorsArray();
            accountValidator.validateClientOrAccountId(fromAccountId);
            accountValidator.validateClientOrAccountId(toAccountId);
            accountValidator.validateAmount(amountStr);

            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()){
                int fromAccount_id = Integer.parseInt(fromAccountId);
                int toAccount_id = Integer.parseInt(toAccountId);
                Long amount = Long.parseLong(amountStr);

                Account fromAccount = accountService.findById(fromAccount_id);
                Account toAccount = accountService.findById(toAccount_id);
                if(fromAccount != null && toAccount != null){
                    if(fromAccount.getAmount() - amount < 0){
                        JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), "Not enough money in the first account");
                    }else{
                        fromAccount.setAmount(fromAccount.getAmount() - amount);
                        toAccount.setAmount(toAccount.getAmount() + amount);

                        if(accountService.updateAccount(fromAccount) && accountService.updateAccount(toAccount)){
                            JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), "Transferred successfully");
                            Activity activity = activityService.save("Transferred money");
                            activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                        }else{
                            JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), "Something went wrong");
                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), "Account/s does not exist");
                }

            }else {
                JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class ProcessBillsViewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeUpdateAccountView.displayAccounts(accountService.getAllAccounts());
            employeeProcessBillsView.setVisible(true);
        }
    }

    private class ProcessBillsBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String account_id = employeeProcessBillsView.getTfFrom();
            String amountStr = employeeProcessBillsView.getTfAmount();

            accountValidator.resetErrorsArray();
            accountValidator.validateClientOrAccountId(account_id);
            accountValidator.validateAmount(amountStr);

            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()){
                int accountId = Integer.parseInt(account_id);
                Long amount = Long.parseLong(amountStr);

                Account account = accountService.findById(accountId);
                if(account != null){
                    if(account.getAmount() - amount < 0){
                        JOptionPane.showMessageDialog(employeeTransferMoneyView.getContentPane(), "Not enough money in the account");
                    }else{
                        account.setAmount(account.getAmount() - amount);
                        if(accountService.updateAccount(account)){
                            JOptionPane.showMessageDialog(employeeProcessBillsView.getContentPane(), "Bill payed successfully");
                            Activity activity = activityService.save("Payed bill");
                            activityService.addActivityToUser(authenticationService.getSentinel().getUser(), activity.getId());
                        }
                        else{
                            JOptionPane.showMessageDialog(employeeProcessBillsView.getContentPane(), "Something went wrong");
                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(employeeProcessBillsView.getContentPane(), "This account does not exist");
                }

            }else{
                JOptionPane.showMessageDialog(employeeProcessBillsView.getContentPane(), accountValidator.getFormattedErrors());
            }

        }
    }
}

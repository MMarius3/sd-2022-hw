package controller;

import database.Constants;
import model.Account;
import model.Activity;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.BillView;
import view.EmployeeView;
import view.LoginView;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import static database.Constants.Roles.EMPLOYEE;

public class EmployeeController extends SessionController{
    private final EmployeeView employeeView;
    private final LoginView loginView;
    private final TransferView transferView;
    private final BillView billView;

    private final ClientService clientService;
    private final ActivityService activityService;
    private final AccountService accountService;

    private final TableProcessing tableProcessing;

    public EmployeeController(EmployeeView employeeView, LoginView loginView, TransferView transferView, BillView billView,
                              ClientService clientService, ActivityService activityService,
                              AccountService accountService, TableProcessing tableProcessing){
        this.employeeView = employeeView;
        this.loginView = loginView;
        this.transferView = transferView;
        this.billView = billView;
        this.clientService = clientService;
        this.activityService = activityService;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        employeeView.setAddClientButtonListener(new AddClientButtonListener());
        employeeView.setLogoutButtonListener(new LogoutButtonListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        employeeView.setViewClientButtonListener(new ViewClientButtonListener());
        employeeView.setAddAccountButtonListener(new AddAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setViewAccountButtonListener(new ViewAccountButtonListener());
        employeeView.setTransferButtonListener(new TransferButtonListener());
        employeeView.setBillButtonListener(new BillButtonListener());
        this.employeeView.loadClientsTable(tableProcessing.generateTable(clientService.findAll(),
                Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.employeeView.loadAccountsTable(tableProcessing.generateTable(accountService.findAll(),
                Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class AddClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getName();
            String identityCardNumber = employeeView.getIdentityCardNumber();
            String personalNumericalCode = employeeView.getPersonalNumericalCode();
            String address = employeeView.getAddress();

            Client client = new ClientBuilder()
                    .setName(name)
                    .setIdentityCardNumber(identityCardNumber)
                    .setPersonalNumericalCode(personalNumericalCode)
                    .setAddress(address)
                    .build();

            Notification<Boolean> addClientNotification = clientService.save(client);
            if (addClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), addClientNotification.getFormattedErrors());
            } else {
                if (!addClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Registration not successful!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Registration successful!");
                    long millis=System.currentTimeMillis();
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Added client " + name)
                            .build();
                    activityService.save(activity);
                }
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getName();
            String identityCardNumber = employeeView.getIdentityCardNumber();
            String personalNumericalCode = employeeView.getPersonalNumericalCode();
            String address = employeeView.getAddress();
            Long clientId = employeeView.getSelectedRowFromClients();

            Client client = new ClientBuilder()
                    .setId(clientId)
                    .setName(name)
                    .setIdentityCardNumber(identityCardNumber)
                    .setPersonalNumericalCode(personalNumericalCode)
                    .setAddress(address)
                    .build();

            Notification<Boolean> updateClientNotification = clientService.updateClient(client);
            if (updateClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), updateClientNotification.getFormattedErrors());
            } else {
                if (!updateClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Editing client not successful!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Editing client successful!");
                    long millis=System.currentTimeMillis();
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Edited client " + name)
                            .build();
                    activityService.save(activity);
                }
            }
        }
    }

    private class ViewClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.loadClientsTable(tableProcessing.generateTable(clientService.findAll(),
                    Constants.Columns.CLIENT_TABLE_COLUMNS));
        }
    }

    private class AddAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            long millis=System.currentTimeMillis();
            Date date = new Date(millis);
            String type = employeeView.getTfType();
            Double balance = Double.parseDouble(employeeView.getBalance());
            Long clientId = employeeView.getSelectedRowFromClients();

            Account account = new AccountBuilder()
                    .setType(type)
                    .setBalance(balance)
                    .setDate(date)
                    .setIdClient(clientId)
                    .build();

            Notification<Boolean> addAccountNotification = accountService.save(account);
            if (addAccountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), addAccountNotification.getFormattedErrors());
            } else {
                if (!addAccountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Add account not successful!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Add account successful!");
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Added account")
                            .build();
                    activityService.save(activity);
                }
            }

        }
    }

    private class UpdateAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeView.getTfType();
            Double balance = Double.parseDouble(employeeView.getBalance());
            Long accountId = employeeView.getSelectedRowFromAccounts();

            Account account = new AccountBuilder()
                    .setId(accountId)
                    .setType(type)
                    .setBalance(balance)
                    .build();

            Notification<Boolean> updateAccountNotification = accountService.updateAccount(account);
            if (updateAccountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), updateAccountNotification.getFormattedErrors());
            } else {
                if (!updateAccountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Editing account not successful!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Editing account successful!");
                    long millis=System.currentTimeMillis();
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Edited account")
                            .build();
                    activityService.save(activity);
                }
            }
        }
    }

    private class DeleteAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Long accountId = employeeView.getSelectedRowFromAccounts();

            Account account = new AccountBuilder()
                    .setId(accountId)
                    .build();

            boolean deleteAccount = accountService.deleteAccount(account);
            if(deleteAccount){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Deleting account successful!");
                long millis=System.currentTimeMillis();
                Activity activity = new ActivityBuilder()
                        .setIdEmployee(getLoggedInUser())
                        .setRole(EMPLOYEE)
                        .setDate(new Date(millis))
                        .setDescription("Deleted account")
                        .build();
                activityService.save(activity);
            }
            else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Deleting account not successful!");
            }
        }
    }

    private class ViewAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.loadAccountsTable(tableProcessing.generateTable(accountService.findAll(),
                    Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        }
    }

    private class TransferButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            transferView.setVisible(true);
        }
    }

    private class BillButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            billView.setVisible(true);
        }
    }

    private class LogoutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            loginView.setVisible(true);
            loginView.clearTextFields();
        }
    }

}

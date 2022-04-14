package controller;

import database.Constants;
import model.Account;
import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.EmployeeView;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class TransferController extends SessionController{
    private final TransferView transferView;
    private final EmployeeView employeeView;

    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;
    private final ActivityService activityService;

    public TransferController (TransferView transferView, EmployeeView employeeView, AccountService accountService,
                               ClientService clientService, ActivityService activityService,
                               TableProcessing tableProcessing){
        this.transferView = transferView;
        this.employeeView = employeeView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.activityService = activityService;
        this.tableProcessing = tableProcessing;
        transferView.setTransferButtonListener(new TransferButtonListener());
        transferView.setViewClientAccountsButtonListener(new ViewClientAccountsButtonListener());
        transferView.setBackButtonListener(new BackButtonListener());
        this.transferView.loadClientsTable(tableProcessing.generateTable(clientService.findAll(),
                Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.transferView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(),
                Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class ViewClientAccountsButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = transferView.getSelectedClient();
            Long clientId = Long.parseLong(items.get(0).toString());
            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                JOptionPane.showMessageDialog(transferView.getContentPane(), "The chosen client has no accounts!");
            } else {
                transferView.loadAccountsTable(tableProcessing.generateTable(clientAccounts,
                        Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }

    private class TransferButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = transferView.getSelectedAccount();
            Long accountId1 = Long.parseLong(accountItems.get(0).toString());
            Long accountId2 = Long.parseLong(transferView.getAccount2());

            if (accountId1.longValue() == accountId2.longValue()) {
                JOptionPane.showMessageDialog(transferView.getContentPane(), "The chosen accounts must be different!");
                return;
            }

            Double sum = Double.parseDouble(transferView.getSum());
            Notification<Boolean> transferNotification = accountService.transferBetweenAccounts(accountId1,accountId2,sum);
            if (transferNotification.hasErrors()) {
                JOptionPane.showMessageDialog(transferView.getContentPane(), transferNotification.getFormattedErrors());
            } else {
                if (!transferNotification.getResult()) {
                    JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer not successful!");
                } else {
                    JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer successful!");
                    List<Account> accounts = accountService.findAccountsByClientId(Long.parseLong(transferView.getSelectedClient().get(0).toString()));
                    transferView.loadAccountsTable(tableProcessing.generateTable(accounts,
                            Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                    long millis=System.currentTimeMillis();
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Transferred money")
                            .build();
                    activityService.save(activity);
                }
            }
        }
    }

    private class BackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            transferView.setVisible(false);
            employeeView.setVisible(true);
        }
    }
}

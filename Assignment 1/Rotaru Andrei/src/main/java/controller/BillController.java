package controller;

import database.Constants;
import model.Account;
import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.BillView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class BillController extends SessionController{
    private final BillView billView;
    private final EmployeeView employeeView;

    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;
    private final ActivityService activityService;

    public BillController(BillView billView, EmployeeView employeeView, AccountService accountService,
                          ClientService clientService, ActivityService activityService,
                          TableProcessing tableProcessing){
        this.billView = billView;
        this.employeeView = employeeView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.activityService = activityService;
        this.tableProcessing = tableProcessing;
        billView.setViewClientAccountsButtonListener(new ViewClientAccountsButtonListener());
        billView.setPayButtonListener(new PayButtonListener());
        billView.setBackButtonListener(new BackButtonListener());
        this.billView.loadClientsTable(tableProcessing.generateTable(clientService.findAll(),
                Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.billView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(),
                Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class ViewClientAccountsButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = billView.getSelectedClient();
            Long clientId = Long.parseLong(items.get(0).toString());
            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                JOptionPane.showMessageDialog(billView.getContentPane(), "The chosen client has no accounts!");
            } else {
                billView.loadAccountsTable(tableProcessing.generateTable(clientAccounts,
                        Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }

    private class PayButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = billView.getSelectedAccount();
            Long accountId = Long.parseLong(accountItems.get(0).toString());

            Double sum = Double.parseDouble(billView.getSum());
            Notification<Boolean> billNotification = accountService.payBill(accountId, sum);
            if (billNotification.hasErrors()) {
                JOptionPane.showMessageDialog(billView.getContentPane(), billNotification.getFormattedErrors());
            } else {
                if (!billNotification.getResult()) {
                    JOptionPane.showMessageDialog(billView.getContentPane(), "Paying bill not successful!");
                } else {
                    JOptionPane.showMessageDialog(billView.getContentPane(), "Paying bill successful!");
                    List<Account> accounts = accountService.findAccountsByClientId(Long.parseLong(billView.getSelectedClient().get(0).toString()));
                    billView.loadAccountsTable(tableProcessing.generateTable(accounts,
                            Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                    long millis=System.currentTimeMillis();
                    Activity activity = new ActivityBuilder()
                            .setIdEmployee(getLoggedInUser())
                            .setRole(EMPLOYEE)
                            .setDate(new Date(millis))
                            .setDescription("Payed bill")
                            .build();
                    activityService.save(activity);
                }
            }
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            billView.setVisible(false);
            employeeView.setVisible(true);
        }
    }
}

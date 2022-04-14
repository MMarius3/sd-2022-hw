package controller;

import model.Account;
import model.Action;
import model.Client;
import model.User;
import model.builder.ActionBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import services.account.AccountService;
import services.action.ActionService;
import services.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ClientValidator clientValidator;
    private final ActionService actionService;
    private final User currentUser;

    List<Client> clients = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService, ActionService actionService, User currentUser) throws SQLException {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.actionService = actionService;
        this.currentUser = currentUser;
        this.employeeView.addSearchButtonActionListener(new SearchButtonListener());
        this.employeeView.addResetButtonActionListener(new ResetButtonListener());
        this.clientValidator = new ClientValidator(clientService);
        this.employeeView.addAddButtonActionListener(new AddButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addTransferButtonListener(new TransferButtonListener());
        this.employeeView.addPayBillButtonListener(new PayBillButtonListener());

        getClients();
        getAccounts();
    }

    private void getClients() throws SQLException {
        clients = clientService.findAll();
        employeeView.setClients(clients);
    }

    private void getAccounts() {
        try {
            accounts = accountService.findAll();
            employeeView.setAccounts(accounts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws SQLException {
        getClients();
        employeeView.populateClientTable();
    }

    private void refreshAccountTable() throws SQLException {
        getAccounts();
        employeeView.populateAccountTable();
    }

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String srcPnc = employeeView.getSearchPnc();

            List<Client> srcClients = clients.stream()
                    .filter(x -> x.getPnc().equals(srcPnc))
                    .collect(Collectors.toList());

            employeeView.setClients(srcClients);
            employeeView.populateClientTable();
        }
    }

    private class ResetButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setClients(clients);
            employeeView.populateClientTable();
        }
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] info = employeeView.getAddClientInfo();
            clientValidator.validate(info[2], info[1]);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                Client client = new ClientBuilder()
                        .setCardNumber(info[2])
                        .setPnc(info[1])
                        .setFullName(info[0])
                        .setAddress(info[3])
                        .build();
                clientService.save(client);
                try {
                    refreshTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] info = employeeView.update();
            Optional<Client> updateClient = clients.stream()
                    .filter(x -> x.getPnc().equals(info[1]) || x.getCardNumber().equals(info[2]))
                    .findFirst();
            if (updateClient.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "You cannot modify both PNC and Card Number!");
            } else {
                Client client = updateClient.get();
                client.setFullName(info[0]);
                client.setPnc(info[1]);
                client.setCardNumber(info[2]);
                client.setAddress(info[3]);
                clientService.update(client);
                try {
                    refreshTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String pnc = employeeView.delete();
            Optional<Client> client = clients.stream()
                    .filter(x -> x.getPnc().equals(pnc))
                    .findFirst();
            client.ifPresent(clientService::delete);
            try {
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class TransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] info = employeeView.transfer();
            Long fromID = Long.parseLong(info[0]);
            Long toID = Long.parseLong(info[1]);
            Float amount = Float.parseFloat(info[2]);
            if (info == null) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please complete all fields to transfer");
                return;
            }
            Optional<Account> from = accounts.stream()
                    .filter(x -> x.getId().equals(fromID))
                    .findFirst();
            Optional<Account> to = accounts.stream()
                    .filter(x -> x.getId().equals(toID))
                    .findFirst();
            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid account IDs");
                return;
            }

            Account fromAccount = from.get();
            Account toAccount = to.get();

            if (fromAccount.getBalance() - amount < 0) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Not enough funds");
                return;
            }
            float balance = fromAccount.getBalance() - amount;
            fromAccount.setBalance(balance);
            balance = toAccount.getBalance() + amount;
            toAccount.setBalance(balance);

            accountService.update(fromAccount);
            accountService.update(toAccount);
            try {
                refreshAccountTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            createTransferAction(fromAccount, toAccount, amount);
        }
    }

    private void createBillPaymentAction(Account account, Float amount) {
        Action action = new ActionBuilder()
                .setType("Bill payment")
                .setDetails(getBillPaymentDetails(account, amount))
                .setDate(LocalDate.now())
                .setUserID(Math.toIntExact(currentUser.getId()))
                .build();

        actionService.save(action);
    }

    private String getBillPaymentDetails(Account account, Float amount) {
        return "From account #" + account.getId() + ", amount = " + amount;
    }

    private void createTransferAction(Account fromAccount, Account toAccount, Float amount) {
        Action action = new ActionBuilder()
                .setType("Transfer")
                .setDetails(getTransferDetails(fromAccount, toAccount, amount))
                .setDate(LocalDate.now())
                .setUserID(Math.toIntExact(currentUser.getId()))
                .build();

        actionService.save(action);
    }

    private String getTransferDetails(Account fromAccount, Account toAccount, Float amount) {
        return "From account #" + fromAccount.getId() + " to account #" + toAccount.getId() + ", amount = " + amount;
    }

    private class PayBillButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] info = employeeView.payBill();
            Long id = Long.parseLong(info[0]);
            Float amount = Float.parseFloat(info[1]);

            Optional<Account> account = accounts.stream()
                    .filter(x -> x.getId().equals(id))
                    .findFirst();
            if (account.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid account");
                return;
            }
            Account newAccount = account.get();
            float balance = newAccount.getBalance() - amount;
            newAccount.setBalance(balance);

            accountService.update(newAccount);
            try {
                refreshAccountTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            createBillPaymentAction(newAccount, amount);
        }
    }
}

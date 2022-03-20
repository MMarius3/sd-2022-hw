package controller;

import helpers.Helpers;
import model.Account;
import model.AccountTypes;
import model.Bill;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import helpers.validation.Notification;
import service.banking.AccountService;
import service.logger.LoggerService;
import service.user.ClientService;
import view.PayBill;
import view.Transfer;
import view.UserView;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserViewController {
    private final UserView userView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final LoggerService loggerService;
    private PayBill billDialog;
    private Transfer transfer;

    private User loggedUser;
    private List<Client> clients;
    private Client selectedClient;
    private Account selectedAccount;

    public UserViewController(UserView userView, ClientService clientService,
                              AccountService accountService,
                              LoggerService loggerService) {
        this.userView = userView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.loggerService = loggerService;
        loadClientData();

        userView.getUsersTable().getSelectionModel()
                .addListSelectionListener(new ClientTableSelectionListener());
        userView.getAccountsTable().getSelectionModel()
                .addListSelectionListener(new AccountTableSelectionListener());

        userView.getCreateClient().addActionListener(new CreateUserListener());
        userView.getUpdateClient().addActionListener(new UpdateUserListener());
        userView.getDeleteClient().addActionListener(new DeleteUserListener());

        userView.getCreateAccount().addActionListener(new CreateAccountListener());
        userView.getUpdateAccount().addActionListener(new UpdateAccountListener());
        userView.getDeleteAccount().addActionListener(new DeleteAccountListener());

        userView.getPayBillButton().addActionListener(new PayBillListener());
        userView.getTransferMoneyButton().addActionListener(new TransferListener());

        userView.getBalance().setText("0");

    }

    private void loadClientData() {
        clients = clientService.fetchClients();
//        System.out.println(clients.toString());
        userView.setClients(clients);
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        //user has logged in, display data
        this.loggedUser = loggedUser;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    private class ClientTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
//            System.out.println(userView.getUsersTable().getSelectedRow());
            int selectedClientIndex = userView.getUsersTable().getSelectedRow();
            if (selectedClientIndex != -1) {
                selectedClient = clients.get(selectedClientIndex);
                setSelectedClient();
                userView.addAccountsToTable(selectedClient.getAccounts());
            }


        }
    }

    private void setSelectedClient() {
        userView.getEmail().setText(selectedClient.getUser().getUsername());
        userView.getNameField().setText(selectedClient.getName());
        userView.getIdNumber().setText(selectedClient.getIdNumber());
        userView.getCNP().setText(selectedClient.getCNP());
        userView.getAddress().setText(selectedClient.getAddress());
    }

    private void updateSelectedClient() {
        selectedClient.setName(userView.getNameField().getText());
        selectedClient.setIdNumber(userView.getIdNumber().getText());
        selectedClient.setCNP(userView.getCNP().getText());
        selectedClient.setAddress(userView.getAddress().getText());
    }

    private void setSelectedAccount() {
        userView.getAccountNumber().setText(selectedAccount.getAccountNumber());
        userView.getBalance().setText(Float.toString(selectedAccount.getBalance()));
        userView.getTypeComboBox().setSelectedItem(selectedAccount.getType().toString());
    }

    private void updateSelectedAccount() {
        selectedAccount.setAccountNumber(userView.getAccountNumber().getText());
        selectedAccount.setType(
                AccountTypes.valueOf((String) userView.getTypeComboBox().getSelectedItem()));
        selectedAccount.setBalance(Float.parseFloat(userView.getBalance().getText()));
    }

    private class AccountTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedAccountIndex = userView.getAccountsTable().getSelectedRow();
            if (selectedAccountIndex != -1) {
                selectedAccount = selectedClient.getAccounts().get(selectedAccountIndex);
                setSelectedAccount();
            } else {
                selectedAccount = null;
                userView.getAccountNumber().setText(null);
                userView.getBalance().setText("0");
                userView.getTypeComboBox().setSelectedItem(null);
            }

        }
    }

    private boolean ensureAccountAndClient() {
        if (selectedAccount == null || selectedClient == null) {
            JOptionPane.showMessageDialog(userView.getContentPane(), "No account/client " +
                    "selected");
            return true;
        }
        return false;
    }

    private boolean ensureClient() {
        if (selectedClient == null) {
            JOptionPane.showMessageDialog(userView.getContentPane(), "No client " +
                    "selected");
            return true;
        }
        return false;
    }

    private class CreateUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //show which users have no client account then create account for the chosen one
            Notification<List<User>> unregisteredClientsNotification =
                    clientService.fetchUsersWithoutAccounts();

            if (unregisteredClientsNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        unregisteredClientsNotification.getFormattedErrors());
            } else {
                if (unregisteredClientsNotification.getResult().size() == 0) {
                    JOptionPane.showMessageDialog(userView.getContentPane(), "There are no users" +
                            " available for creation into client");
                } else {
                    List<User> users = unregisteredClientsNotification.getResult();
                    String[] possibilities = new String[users.size()];
                    for (int i = 0; i < users.size(); i++) {
                        possibilities[i] = users.get(i).getUsername();
                    }

                    String s = (String) JOptionPane.showInputDialog(
                            userView.getContentPane(),
                            "Choose email of user to create client",
                            "Customized Dialog",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            users.get(0).getUsername());

//If a string was returned, say so.
                    if ((s != null) && (s.length() > 0)) {
                        for (int i = 0; i < users.size(); i++) {
                            if (s.equals(users.get(i).getUsername())) {

                                Notification<Client> insertNotification =
                                        clientService.createClient(
                                                new ClientBuilder().setUser(users.get(i))
                                                        .setAddress(userView.getAddress().getText())
                                                        .setCNP(userView.getCNP().getText())
                                                        .setIdNumber(
                                                                userView.getIdNumber().getText())
                                                        .setName(userView.getNameField().getText())
                                                        .build());
                                if (insertNotification.hasErrors()) {
                                    JOptionPane.showMessageDialog(userView.getContentPane(),
                                            insertNotification.getFormattedErrors());
                                } else {
                                    Client client = insertNotification.getResult();
                                    clients.add(client);
                                    userView.setClients(clients);
                                    loggerService.addClient(loggedUser.getId(), client);
                                }
                            }
                        }
                        return;
                    }

                    JOptionPane.showMessageDialog(userView.getContentPane(), "Something went " +
                            "wrong");
                }
            }
        }
    }

    private class DeleteUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ensureClient()) {
                return;
            }
            Notification<Boolean> deleteNotification =
                    clientService.deleteClient(selectedClient.getId());
            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        deleteNotification.getFormattedErrors());
            } else {
                clients = Helpers.removeClientById(clients, selectedClient.getId());
                loggerService.removeClient(loggedUser.getId(), selectedClient);
                selectedClient = null;
                userView.setClients(clients);

            }
        }
    }

    private class UpdateUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ensureClient()) {
                return;
            }
            updateSelectedClient();
            Notification<Boolean> updateNotification =
                    clientService.updateClient(selectedClient);
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        updateNotification.getFormattedErrors());
            } else {
                Helpers.replaceClient(clients, selectedClient);
                loggerService.editClient(loggedUser.getId(), selectedClient);
                userView.setClients(clients);
            }
        }
    }

    private class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ensureClient()) {
                return;
            }
            Notification<Account> insertNotification =
                    accountService.addAccountToClient(new AccountBuilder()
                            .setAccountNumber(userView.getAccountNumber().getText())
                            .setBalance(Float.parseFloat(userView.getBalance().getText()))
                            .setType(AccountTypes.valueOf(
                                    (String) userView.getTypeComboBox().getSelectedItem()))
                            .build(), selectedClient.getId());
            if (insertNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        insertNotification.getFormattedErrors());
            } else {
                Account account = insertNotification.getResult();
                loggerService.addAccount(loggedUser.getId(), account);
                selectedClient.getAccounts().add(account);
                userView.addAccountsToTable(selectedClient.getAccounts());
            }
        }
    }

    private class DeleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ensureAccountAndClient()) {
                return;
            }
            Notification<Boolean> deleteNotification =
                    accountService.deleteAccount(selectedAccount.getId());
            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        deleteNotification.getFormattedErrors());
            } else {
                selectedClient.setAccounts(Helpers.removeAccountFromClient(selectedAccount,
                        selectedClient));
                loggerService.deleteAccount(loggedUser.getId(), selectedAccount);
                selectedAccount = null;
                userView.addAccountsToTable(selectedClient.getAccounts());
            }
        }
    }


    private class UpdateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ensureAccountAndClient()) {
                return;
            }
            updateSelectedAccount();
            Notification<Boolean> updateNotification =
                    accountService.updateAccount(selectedAccount);
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        updateNotification.getFormattedErrors());
            } else {
                Helpers.replaceAccount(selectedClient.getAccounts(), selectedAccount);
                loggerService.editAccount(loggedUser.getId(), selectedAccount);
                userView.addAccountsToTable(selectedClient.getAccounts());
            }
        }
    }

    private class TransferListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ensureAccountAndClient()) return;
            transfer = new Transfer("Transfer Dialog");
            transfer.setVisible(true);
            transfer.getMakeTransferButton().addActionListener(new TransferButtonListener());
            transfer.getFrom().setText(selectedAccount.getAccountNumber());
            transfer.getFrom().setEditable(false);
        }
    }

    private class TransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ensureAccountAndClient()) return;
            Notification<Boolean> transferNotification =
                    accountService.transferMoney(transfer.getFrom().getText(),
                    transfer.getTo().getText(),
                            Float.parseFloat(transfer.getValueField().getText()));
            transfer.setVisible(false);
            if (transferNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        transferNotification.getFormattedErrors());
            } else {
                selectedAccount.setBalance(selectedAccount.getBalance() - Float.parseFloat(transfer.getValueField().getText()));
                Helpers.replaceAccount(selectedClient.getAccounts(), selectedAccount);
                Helpers.addMoneyToAccount(clients, transfer.getTo().getText(), Float.parseFloat(transfer.getValueField().getText()));
                userView.addAccountsToTable(selectedClient.getAccounts());
                loggerService.transferMoney(loggedUser.getId(),
                        transfer.getValueField().getText(), transfer.getFrom().getText(),
                        transfer.getTo().getText());
            }
        }
    }

    private class PayBillListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ensureAccountAndClient()) return;
            billDialog = new PayBill("Bill Payment Dialog");
            billDialog.setVisible(true);
            billDialog.getPayBillButton().addActionListener(new PayBillButtonListener());

        }
    }

    private class PayBillButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ensureAccountAndClient()) return;
            Bill bill =
                    Bill.builder().description(billDialog.getBillName().getText())
                            .value(Float.parseFloat(billDialog.getBillValue().getText())).build();
            Notification<Boolean> transferNotification =
                    accountService.payBill(selectedAccount.getId(), bill);
            billDialog.setVisible(false);
            if (transferNotification.hasErrors()) {
                JOptionPane.showMessageDialog(userView.getContentPane(),
                        transferNotification.getFormattedErrors());
            } else {
                selectedAccount.setBalance(selectedAccount.getBalance() - Float.parseFloat(billDialog.getBillValue().getText()));
                Helpers.replaceAccount(selectedClient.getAccounts(), selectedAccount);
                userView.addAccountsToTable(selectedClient.getAccounts());
                loggerService.payBill(loggedUser.getId(), billDialog.getBillValue().getText(),
                        billDialog.getBillName().getText(), selectedClient);
            }
        }
    }
}

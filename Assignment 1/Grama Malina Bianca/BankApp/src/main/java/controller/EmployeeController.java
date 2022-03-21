package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.Notification;
import service.account.CRUDAccount;
import service.client.CRUClient;
import view.employee.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeController {
    private final AddClientView addClientView;
    private final UpdateClientView updateClientView;
    private final DisplayClientView displayClientView;
    private final AddAccountView addAccountView;
    private final UpdateAccountView updateAccountView;
    private final DeleteAccountView deleteAccountView;
    private final DisplayAccountView displayAccountView;
    private final SearchClientAccountView searchClientAccountView;
    private final TransferMoneyView transferMoneyView;
    private final ProcessUtilitiesBillsView processUtilitiesBillsView;
    private final CRUClient cruClient;
    private final CRUDAccount crudAccount;

    public EmployeeController(EmployeeView employeeView, AddClientView addClientView,
                              UpdateClientView updateClientView, DisplayClientView displayClientView,
                              AddAccountView addAccountView, UpdateAccountView updateAccountView,
                              DeleteAccountView deleteAccountView, DisplayAccountView displayAccountView,
                              SearchClientAccountView searchClientAccountView, TransferMoneyView
                                      transferMoneyView, ProcessUtilitiesBillsView processUtilitiesBillsView, CRUClient cruClient, CRUDAccount crudAccount) {
        this.displayClientView = displayClientView;
        this.addAccountView = addAccountView;
        this.updateAccountView = updateAccountView;
        this.deleteAccountView = deleteAccountView;
        this.displayAccountView = displayAccountView;
        this.searchClientAccountView = searchClientAccountView;
        this.transferMoneyView = transferMoneyView;
        this.processUtilitiesBillsView = processUtilitiesBillsView;
        this.crudAccount = crudAccount;
        employeeView.setAddClientBtnListener(new SetAddClientBtnList());
        employeeView.setUpdateClientBtnListener(new SetUpdateClientBtnList());
        employeeView.setViewClientBtnListener(new SetDisplayClientBtnList());
        employeeView.setAddAccountBtnListener(new DisplayAddAccount());
        employeeView.setUpdateAccountBtnListener(new DisplayUpdateAccount());
        employeeView.setDeleteAccountBtnListener(new DisplayDeleteAccount());
        employeeView.setViewAccountBtnListener(new DisplaySearchForAccountView());
        employeeView.setTransferMoneyListener(new DisplayTransferMoney());
        employeeView.setProcessBillsListener(new DisplayProcessBills());
        this.addClientView = addClientView;
        this.addClientView.setAddClientButtonListener(new AddClientActionListener());
        this.updateClientView = updateClientView;
        this.updateClientView.setSearchClientButtonListener(new SearchClientActionListener());
        this.updateClientView.setUpdateClientButtonListener(new UpdateClientActionListener());
        this.addAccountView.setAddAccountBtnListener(new SetAddAccountBtnList());
        this.updateAccountView.setSearchAccountButtonListener(new UpdateAccountSearchButtonListener());
        this.updateAccountView.setUpdateAccountButtonListener(new UpdateAccountUpdateButtonListener());
        this.deleteAccountView.setSearchAccountButtonListener(new DeleteAccountSearchButtonListener());
        this.deleteAccountView.setDeleteAccountButtonListener(new DeleteAccountDeleteButtonListener());
        this.searchClientAccountView.setSearchClientButtonListener(new SearchClientDisplayAccountActionListener());
        this.transferMoneyView.setTransferListener(new TransferMoneyTransferButtonListener());
        this.processUtilitiesBillsView.setProcessBillsBtnListener(new ProcessBillsBtnListener());
        this.cruClient = cruClient;
    }

    private class SetAddClientBtnList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addClientView.setVisible();
        }
    }

    private class AddClientActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = addClientView.getName();
            String card_no = addClientView.getICNo();
            String cnp = addClientView.getCnp();
            String address = addClientView.getAddress();

            Notification<Boolean> addClientNotification = cruClient.addClient(name, cnp, card_no, address);

            if (addClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(addClientView.getContentPane(), addClientNotification.getFormattedErrors());
            } else {
                if (!addClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(addClientView.getContentPane(), "Adding a client not successful, please try again " +
                            "later.");
                } else {
                    JOptionPane.showMessageDialog(addClientView.getContentPane(), "Adding a client successful!");
                }
            }
        }
    }

    private class SetUpdateClientBtnList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateClientView.setVisible();
        }
    }

    private class SearchClientActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(updateClientView.getId());
            Client client = cruClient.searchById(id);
            if (client != null) {
                updateClientView.setName(client.getName());
                updateClientView.setCard_no(client.getIdCardNo());
                updateClientView.setCnp(client.getCNP());
                updateClientView.setAddress(client.getAddress());
            } else {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Client not found.");
            }
        }
    }

    private class UpdateClientActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(updateClientView.getId());
            Client client = new Client();
            client.setName(updateClientView.getName());
            client.setCNP(updateClientView.getCnp());
            client.setIdCardNo(updateClientView.getCardNo());
            client.setAddress(updateClientView.getAddress());
            if (!cruClient.updateClient(id, client)) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Client updated successfully.");
            } else {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "There was an error in updating the client. Please try again.");
            }
        }
    }

    private class SetDisplayClientBtnList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clientList = cruClient.viewClients();
            displayClientView.fetchClients(clientList);
            displayClientView.initializeTable(clientList);
            // displayClientView.setVisible(true);
        }
    }

    private class DisplayAddAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addAccountView.setVisible();
        }
    }

    private class SetAddAccountBtnList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientId = Long.valueOf(addAccountView.getClientId());
            Client client = cruClient.searchById(clientId);
            if (client != null) {
                LocalDate currentDate = LocalDate.now();
                int currentDay = currentDate.getDayOfMonth();
                int currentMonth = currentDate.getMonth().getValue();
                int currentYear = currentDate.getYear();
                Date dateOfCreation = new Date(currentYear - 1900, currentMonth - 1, currentDay);
                Account account = new AccountBuilder()
                        .setType(addAccountView.getAccountType())
                        .setAmount(Float.valueOf(addAccountView.getAccountAmount()))
                        .setDateOfCreation(dateOfCreation)
                        .build();
                boolean result = crudAccount.openAccount(client, account);
                if (result) {
                    JOptionPane.showMessageDialog(addAccountView.getContentPane(), "Account added successfully.");
                } else {
                    JOptionPane.showMessageDialog(addAccountView.getContentPane(), "Error in creating account. Try again.");
                }
            } else {
                JOptionPane.showMessageDialog(addAccountView.getContentPane(), "Error in creating account. Client not found. Try again.");
            }
        }
    }

    private class DisplayUpdateAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateAccountView.setVisible();
        }
    }

    private class UpdateAccountSearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(updateAccountView.getId());
            Account account = crudAccount.findById(id);
            if (account != null) {
                updateAccountView.setType(account.getType());
                updateAccountView.setAmount(String.valueOf(account.getAmount()));
            } else {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), "Account not found.");
            }
        }
    }

    private class UpdateAccountUpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(updateAccountView.getId());
            Account account = new Account();
            account.setType(updateAccountView.getAccType());
            account.setAmount(Float.valueOf(updateAccountView.getAccAmount()));
            if (crudAccount.update(id, account)) {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), "Account updated successfully.");
            } else {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), "There was an error in updating the account. Please try again.");
            }
        }
    }

    private class DisplayDeleteAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteAccountView.setVisible();
        }
    }

    private class DeleteAccountSearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(deleteAccountView.getId());
            Account account = crudAccount.findById(id);
            if (account != null) {
                deleteAccountView.setType(account.getType());
                deleteAccountView.setAmount(String.valueOf(account.getAmount()));
            } else {
                JOptionPane.showMessageDialog(deleteAccountView.getContentPane(), "Account not found.");
            }
        }
    }

    private class DeleteAccountDeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(deleteAccountView.getId());
            if (crudAccount.delete(id)) {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), "Account deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), "There was an error in deleting the account. Please try again.");
            }
        }
    }

    private class DisplaySearchForAccountView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            searchClientAccountView.setVisible(true);
        }
    }

    private class SearchClientDisplayAccountActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(searchClientAccountView.getClientId());
            Client client = cruClient.searchById(id);
            if (client != null) {
                List<Account> accounts = crudAccount.viewAccountForClient(client.getId());
                displayAccountView.initializeTable(accounts);
            } else {
                JOptionPane.showMessageDialog(searchClientAccountView.getContentPane(), "Client not found.");
            }
        }
    }

    private class DisplayTransferMoney implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            transferMoneyView.setVisible(true);
        }
    }

    private class TransferMoneyTransferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id1 = Long.valueOf(transferMoneyView.getAccount1Id());
            Long id2 = Long.valueOf(transferMoneyView.getAccount2Id());
            Account from = crudAccount.findById(id1);
            Account to = crudAccount.findById(id2);
            if (from != null && to != null) {
                Float amount = Float.valueOf(transferMoneyView.getAmount());
                boolean result = crudAccount.transferMoney(from, to, amount);
                if (result) {
                    JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "Money transferred successfully.");
                } else {
                    JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "Insufficient money.");
                }
            } else if (from == null) {
                JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "FROM account not found.");
            } else {
                JOptionPane.showMessageDialog(transferMoneyView.getContentPane(), "TO account not found.");
            }
        }
    }

    private class DisplayProcessBills implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            processUtilitiesBillsView.setVisible();
        }
    }

    private class ProcessBillsBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long accountId = Long.valueOf(processUtilitiesBillsView.getId());
            Account account = crudAccount.findById(accountId);
            if (account != null) {
                Float amount = Float.valueOf(processUtilitiesBillsView.getAmount());
                boolean response = crudAccount.extractMoney(account, amount);
                if (response) {
                    JOptionPane.showMessageDialog(processUtilitiesBillsView.getContentPane(), "Bill Processed Successfully.");
                }  else {
                    JOptionPane.showMessageDialog(processUtilitiesBillsView.getContentPane(), "Failed to Process Bill. Check if there is enough money in the account, then try again.");
                }
            } else {
                JOptionPane.showMessageDialog(processUtilitiesBillsView.getContentPane(), "Account not found.");
            }
        }
    }

}

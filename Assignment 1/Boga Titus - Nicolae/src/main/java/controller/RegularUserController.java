package controller;

import model.Account;
import model.Client;
import service.account.AccountService;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.transfer.TransferService;
import view.RegularUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class RegularUserController {
    private final RegularUserView regularUserView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final TransferService transferService;

    public RegularUserController(RegularUserView regularUserView, ClientService clientService, AccountService ac,TransferService transferService) {
        this.regularUserView = regularUserView;
        this.accountService = ac;
        this.transferService = transferService;
        regularUserView.setUpdateButtonListener(new updateButtonListener());
        regularUserView.setShowButtonListener(new showButtonListener());
        regularUserView.setAddButtonListener(new addButtonListener());
        regularUserView.setShowAccountButtonListener(new showAccountButtonListener());
        regularUserView.setUpdateAccountButtonListener(new updateAccountButtonListener());
        regularUserView.setAddAccountButtonListener(new addAccountButtonListener());
        regularUserView.setDeleteAccountButtonListener(new addDeleteButtonListener());
        regularUserView.setFromButtonListener(new fromButtonListener());
        regularUserView.setTotButtonListener(new toButtonListener());
        regularUserView.setTransfertButtonListener(new transferButtonListener());
        this.clientService= clientService;

        List<Client> clients = clientService.findAll();
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (Client client:clients
        ) {
            defaultListModel.addElement(client.getId().toString() + client.getName());
        }
        regularUserView.setClientList(defaultListModel);
    }

    private class updateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client selectedClient=clientService.findById(regularUserView.getSelectedId());
            selectedClient.setName(regularUserView.getClientName());
            selectedClient.setCnp(regularUserView.getClientCnp());
            selectedClient.setAddress(regularUserView.getClientAddress());
            clientService.update(selectedClient);
        }
    }

    private class showButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(regularUserView.getSelectedId());
            Client selectedClient=clientService.findById(regularUserView.getSelectedId());

            regularUserView.setClientName( selectedClient.getName());
            regularUserView.setClientCnp( selectedClient.getCnp());
            regularUserView.setTfClientAddress(selectedClient.getAddress());

            List<Account> accounts = accountService.findByClientId(regularUserView.getSelectedId());
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            for (Account account:accounts
            ) {
                defaultListModel.addElement(account.getId().toString());
            }
            regularUserView.setAccountList(defaultListModel);

        }
    }

    private class addButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("THIS WORKS");
            Client newClient = new Client();
            newClient.setName(regularUserView.getClientName());
            newClient.setCnp(regularUserView.getClientCnp());
            newClient.setAddress(regularUserView.getClientAddress());

            clientService.save(newClient);

            //print again
            List<Client> clients = clientService.findAll();
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            for (Client client:clients
            ) {
                defaultListModel.addElement(client.getId().toString() + client.getName());
            }
            regularUserView.setClientList(defaultListModel);
        }
    }

    private class showAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Account selectedAccount=accountService.findById(regularUserView.getSelectedAccount());
            regularUserView.setAccountType(selectedAccount.getType());
            regularUserView.setAccountAmount((long) selectedAccount.getAmount());
            regularUserView.setDate(selectedAccount.getDate());
        }
    }

    private class updateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Account selectedAccount=accountService.findById(regularUserView.getSelectedAccount());
            selectedAccount.setType(regularUserView.getAccountType());
            selectedAccount.setAmount(Math.toIntExact(regularUserView.getAccountAmount()));
            selectedAccount.setClientId(regularUserView.getSelectedId());
            try {
                selectedAccount.setDate(regularUserView.getDate());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            accountService.update(selectedAccount);
        }
    }

    private class addAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Account selectedAccount=accountService.findById(regularUserView.getSelectedAccount());
            selectedAccount.setType(regularUserView.getAccountType());
            selectedAccount.setAmount(Math.toIntExact(regularUserView.getAccountAmount()));
            selectedAccount.setClientId(regularUserView.getSelectedId());
            try {
                selectedAccount.setDate(regularUserView.getDate());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            accountService.save(selectedAccount);

            List<Account> accounts = accountService.findByClientId(regularUserView.getSelectedId());
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            for (Account account:accounts
            ) {
                defaultListModel.addElement(account.getId().toString());
            }
            regularUserView.setAccountList(defaultListModel);

        }

    }

    private class addDeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Deleting");
            Account selectedAccount=accountService.findById(regularUserView.getSelectedAccount());
            accountService.deleteById(selectedAccount.getId());

            List<Account> accounts = accountService.findByClientId(regularUserView.getSelectedId());
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            for (Account account:accounts
            ) {
                defaultListModel.addElement(account.getId().toString());
            }
            regularUserView.setAccountList(defaultListModel);

        }
    }

    private class fromButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Account account=accountService.findById(regularUserView.getSelectedAccount());
            regularUserView.setFrom("Account " + account.getId() + "from client "+ account.getClientId());
            transferService.setFrom(account);

        }
    }

    private class toButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Account account=accountService.findById(regularUserView.getSelectedAccount());
            regularUserView.setTo("Account " + account.getId() + "from client "+ account.getClientId());
            transferService.setTo(account);

        }
    }

    private class transferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            transferService.setAmount(regularUserView.getTransferAmount());
            transferService.transfer();

        }
    }
}

package controller;

import service.Account.AccountService;
import service.Client.ClientService;
import view.EmployeeView;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import view.AccountDTO;
import view.ClientDTO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final AccountService accountService;
    private final ClientService clientService;

    public EmployeeController(EmployeeView employeeView, AccountService accountService, ClientService clientService) {

        this.employeeView = employeeView;
        this.employeeView.setVisible(false);

        this.accountService = accountService;
        this.clientService = clientService;

        employeeView.setCreateAccountButtonListener(new CreateAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setViewAccountButtonListener(new ViewAccountButtonListener());
        employeeView.setTransferButtonListener(new TransferButtonListener());

        employeeView.setCreateClientButtonListener(new CreateClientButtonListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        employeeView.setDeleteClientButtonListener(new DeleteClientButtonListener());
        employeeView.setViewClientButtonListener(new ViewClientButtonListener());

    }

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            AccountDTO accountDTO = employeeView.getAccountDTO();

            Account ac = new AccountBuilder().builderAccountDTO(accountDTO);

            Notification<Boolean> accountNotification = accountService.save(ac);

            if (accountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                if (!accountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account failed to be created");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created with success");
                }
            }
        }
    }


    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = null;
            try {
                account = accountService.findById(Long.parseLong(employeeView.getId1()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            AccountDTO accountDTO = employeeView.getAccountDTO();

            Account account1 = new AccountBuilder().builderAccountDTO(accountDTO);

            account1.setId(account.getId());

            Notification<Boolean> accountNotification = accountService.update(account1);

            if (accountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                if (!accountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account failed to be updated");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account updated with success");
                }
            }
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = null;
            try {
                account = accountService.findById(Long.parseLong(employeeView.getId1()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            Boolean b = accountService.remove(account.getId());

            if (b)
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account deleted with success");
            else
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account failed to be deleted");

        }
    }

    private class ViewAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = null;
            try {
                account = accountService.findById(Long.parseLong(employeeView.getId1()));
            } catch (
                    EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account information: Type: " + account.getType()
                                                                        + " , Creation Date: " + account.getCreationDate()
                                                                        + ", Amount of money: " + account.getMoneyAmount()
                                                                        + ", identification nr: " + account.getIdentificationNr());
        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String moneyAmountForTransfer = employeeView.getMoneyAmountForTransfer();

            Account account1 = null;
            Account account2 = null;

            try {
                account1 = accountService.findById(Long.parseLong(employeeView.getId1()));
                account2 = accountService.findById(Long.parseLong(employeeView.getId2()));
                accountService.transfer(account1, account2, Integer.parseInt(moneyAmountForTransfer));
            } catch (
                    EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
        }
    }

    public void setVisible(boolean b) {
        employeeView.setVisible(b);
    }

    private class CreateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ClientDTO clientDTO = employeeView.getClientDTO();

            Client c = new ClientBuilder().builderClientDTO(clientDTO);

            Notification<Boolean> clientNotification = clientService.save(c);

            if (clientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                if (!clientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client failed to be created");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client created with success");
                }
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Client c = null;
            try {
                c = clientService.findById(Long.parseLong(employeeView.getClientId()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            ClientDTO clientDTO = employeeView.getClientDTO();

            Client c1 = new ClientBuilder().builderClientDTO(clientDTO);

            c1.setId(c.getId());

            Notification<Boolean> clientNotification = clientService.save(c1);
            if (clientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                if (!clientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client failed to be updated");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated with success");
                }
            }
        }
    }

    private class DeleteClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Client c = null;
            try {
                c = clientService.findById(Long.parseLong(employeeView.getClientId()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            Boolean b = clientService.remove(c.getId());

            if (b)
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client deleted with success");
            else
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client failed to be deleted");

        }
    }

    private class ViewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client c = null;
            try {
                c = clientService.findById(Long.parseLong(employeeView.getClientId()));
            } catch (
                    EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client information: Name: " + c.getName()
                                                                        + " , Address: " + c.getAddress()
                                                                        + ", Identity card number: " + c.getIdentificationNr()
                                                                        + ", CNP: " + c.getPersonalNumericalCode());
        }
    }
}

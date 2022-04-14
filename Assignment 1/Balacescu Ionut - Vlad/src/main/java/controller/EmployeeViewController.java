package controller;

import model.ActionEmployee;
import model.Client;
import model.builder.ActionBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import service.action.ActionService;
import service.client.ClientService;
import view.AccountView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeViewController {
    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final ClientValidator clientValidator;
    private final AccountView accountView;
    private final LoginView loginView;
    private final ActionService actionService;


    public EmployeeViewController(EmployeeView employeeView, ClientService clientService, ClientValidator clientValidator, AccountView accountView, LoginView loginView, ActionService actionService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.accountView = accountView;
        this.loginView = loginView;
        this.actionService = actionService;
        employeeView.setAddButtonListener(new AddButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setAccountsButtonListener(new AccountsButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String creationDate = formatter.format(date);
            ActionEmployee newAct = new ActionBuilder()
                    .setUsername(username).setName("ADD")
                    .setDate(creationDate)
                    .build();
            actionService.addAction(newAct);
            String fullName = employeeView.getFullName();
            String address = employeeView.getAddress();
            String pnc = employeeView.getPNC();
            String idCardNr = employeeView.getIdCardNumber();
            Client newClient = new ClientBuilder().setName(fullName)
                    .setAddress(address)
                    .setPNC(pnc)
                    .setIdCardNumber(idCardNr).build();
            clientValidator.validate(newClient);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientService.addClient(newClient);
                employeeView.resetTextFields();
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client added!");
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.resetTable();
            List<Client> allClients = clientService.findAll();
            employeeView.viewClients((ArrayList<Client>) allClients);
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String creationDate = formatter.format(date);
            ActionEmployee newAct = new ActionBuilder()
                    .setUsername(username).setName("UPDATE")
                    .setDate(creationDate)
                    .build();
            actionService.addAction(newAct);
            long index = employeeView.getIdForSelectedClient();
            ArrayList<String> info = employeeView.getInfoForSelectClient();
            String fullName = info.get(0);
            String address = info.get(1);
            String pnc = info.get(2);
            String idCardNr = info.get(3);
            Client newClient = new ClientBuilder().setId(index)
                    .setName(fullName)
                    .setAddress(address)
                    .setPNC(pnc)
                    .setIdCardNumber(idCardNr).build();
            clientValidator.validate(newClient);
            final List<String> errors = clientValidator.getErrors();
            if (employeeView.isSelected()) {
                if (errors.isEmpty()) {
                    clientService.updateClient(newClient);
                    employeeView.resetTextFields();
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
                }
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select a client!");
            }
        }
    }

    private class AccountsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!employeeView.isSelected()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select a client!");
            } else {
                accountView.setTF(employeeView.getInfoForSelectClient().get(0));
                accountView.setVisible(true);
            }
        }
    }
}

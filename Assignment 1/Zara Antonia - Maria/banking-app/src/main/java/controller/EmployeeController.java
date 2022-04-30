package controller;

import model.Activity;
import model.Client;
import model.ClientAccount;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.ClientAccountBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientAccountValidator;
import model.validator.ClientValidator;
import service.activity.ActivityService;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.client_account.ClientAccountService;
import service.client_account.ClientAccountServiceImpl;
import view.EmployeeView;
import view.ProcessUtilitiesView;
import view.TransferMoneyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class EmployeeController {

    private final User loggedUser;
    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final ClientAccountService clientAccountService;
    private final ActivityService activityService;
    private final ClientValidator clientValidator;
    private final ClientAccountValidator clientAccountValidator;

    public EmployeeController(EmployeeView employeeView, ClientService clientService,
                              ClientAccountService clientAccountService, User user,
                              ActivityService activityService, ClientValidator clientValidator,
                              ClientAccountValidator clientAccountValidator){
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.clientAccountService = clientAccountService;
        this.loggedUser = user;
        this.activityService = activityService;
        this.clientValidator = clientValidator;
        this.clientAccountValidator = clientAccountValidator;

        this.employeeView.addAddClientButtonListener(new AddClientButtonListener());
        this.employeeView.addUpdateClientButtonListener(new UpdateClientButtonListener());
        this.employeeView.addViewClientButtonListener(new ViewClientButtonListener());
        this.employeeView.addAddClientAccountButtonListener(new AddClientAccountButtonListener());
        this.employeeView.addDeleteClientAccountButtonListener(new DeleteClientAccountButtonListener());
        this.employeeView.addViewClientAccountButtonListener(new ViewClientAccountButtonListener());
        this.employeeView.addUpdateClientAccountButtonListener(new UpdateClientAccountButtonListener());
        this.employeeView.addTransferMoneyButtonListener(new TransferMoneyButtonListener());
        this.employeeView.addProcessUtilitiesButtonListener(new ProcessUtilitiesClientButtonListener());

        setClientComboBox();
        setClientAccountComboBox();
    }

    public EmployeeController returnEmployeeController(){
        return this;
    }

    private class AddClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idNumber = employeeView.getClientIdNumber();
            String address = employeeView.getClientAddress();
            String cnp = employeeView.getClientCNP();
            String name = employeeView.getClientName();

            clientValidator.validate(name,cnp,idNumber,address);


            clientValidator.validate(name,cnp,idNumber,address);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                Client client = new ClientBuilder()
                        .setIdNumber(idNumber)
                        .setAddress(address)
                        .setCnp(cnp)
                        .setName(name)
                        .build();

                if(!clientService.add(client)){
                    JOptionPane.showMessageDialog(null, "Cannot add client", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Client Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    activityService.add(new ActivityBuilder()
                            .setDate(date)
                            .setEmployee(loggedUser)
                            .setDescription("Added client")
                            .build());
                }
            } else {
                JOptionPane.showMessageDialog(null, clientValidator.getFormattedErrors());
            }
            setClientComboBox();
        }
    }

    private class ViewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = employeeView.getSelectedClient();

            employeeView.setClientName(client.getName());
            employeeView.setClientAddress(client.getAddress());
            employeeView.setClientCNP(client.getCnp());
            employeeView.setClientIdNumber(client.getIdNumber());

            Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            activityService.add(new ActivityBuilder()
                    .setDate(date)
                    .setEmployee(loggedUser)
                    .setDescription("Viewed client")
                    .build());
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idNumber = employeeView.getClientIdNumber();
            String address = employeeView.getClientAddress();
            String cnp = employeeView.getClientCNP();
            String name = employeeView.getClientName();

            Client client = employeeView.getSelectedClient();

            if(!clientService.update(client,idNumber,address,cnp,name)){

                clientValidator.validate(name,cnp,idNumber,address);
                final List<String> errors = clientValidator.getErrors();
                if(errors.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cannot update client", "WARNING", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, clientValidator.getFormattedErrors());
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Client Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                activityService.add(new ActivityBuilder()
                        .setDate(date)
                        .setEmployee(loggedUser)
                        .setDescription("Updated client")
                        .build());
            }

            setClientComboBox();
        }
    }

    private class AddClientAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeView.getClientAccountType();
            Optional<Double> amount = employeeView.getClientAccountAmount();
            Date creationDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            Client owner = employeeView.getSelectedClient();

            clientAccountValidator.validate(type,amount.toString(), creationDate.toString());

            final List<String> errors = clientValidator.getErrors();
            if(errors.isEmpty()) {
                ClientAccount clientAccount = new ClientAccountBuilder()
                        .setOwner(owner)
                        .setCreationDate(creationDate)
                        .setAmount(amount.get())
                        .setType(type)
                        .build();

                if(!clientAccountService.add(clientAccount)){
                    JOptionPane.showMessageDialog(null, "Cannot add client account", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Client Account Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    activityService.add(new ActivityBuilder()
                            .setDate(date)
                            .setEmployee(loggedUser)
                            .setDescription("Added client account")
                            .build());
                }

            } else {
                JOptionPane.showMessageDialog(null, clientAccountValidator.getFormattedErrors());
            }

            setClientAccountComboBox();
        }
    }

    private class ViewClientAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientAccount clientAccount = employeeView.getSelectedClientAccount();

            employeeView.setClientAccountAmount(clientAccount.getAmount());
            employeeView.setClientAccountCreationDate(clientAccount.getCreationDate());
            employeeView.setClientAccountID(clientAccount.getIdentificationNumber());
            employeeView.setClientAccountOwner(clientAccount.getOwner());
            employeeView.setClientAccountType(clientAccount.getType());

            Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            activityService.add(new ActivityBuilder()
                    .setDate(date)
                    .setEmployee(loggedUser)
                    .setDescription("Viewed client account")
                    .build());
        }
    }

    private class UpdateClientAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = employeeView.getClientAccountType();
            Optional<Double> amount = employeeView.getClientAccountAmount();

            ClientAccount clientAccount = employeeView.getSelectedClientAccount();

            clientAccountValidator.validate(type,amount.toString(), clientAccount.getCreationDate().toString());

            final List<String> errors = clientValidator.getErrors();
            if(errors.isEmpty()) {

                if(!clientAccountService.update(clientAccount,type,amount)){
                    JOptionPane.showMessageDialog(null, "Cannot update client account", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Client Account Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    activityService.add(new ActivityBuilder()
                            .setDate(date)
                            .setEmployee(loggedUser)
                            .setDescription("Updated client account")
                            .build());
                }
            } else {
                JOptionPane.showMessageDialog(null, clientAccountValidator.getFormattedErrors());
            }

            setClientAccountComboBox();
        }
    }

    private class DeleteClientAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientAccount clientAccount = employeeView.getSelectedClientAccount();
            if(!clientAccountService.delete(clientAccount)){
                JOptionPane.showMessageDialog(null, "Cannot delete client account", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Client Account Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                activityService.add(new ActivityBuilder()
                        .setDate(date)
                        .setEmployee(loggedUser)
                        .setDescription("Deleted client account")
                        .build());
            }

            setClientAccountComboBox();
        }
    }

    private class TransferMoneyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransferMoneyView transferMoneyView = new TransferMoneyView();
            TransferMoneyController transferMoneyController = new TransferMoneyController(transferMoneyView, returnEmployeeController(), clientAccountService, loggedUser, activityService, clientAccountValidator);
        }
    }

    private class ProcessUtilitiesClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProcessUtilitiesView processUtilitiesView = new ProcessUtilitiesView();
            ProcessUtilitiesController processUtilitiesController = new ProcessUtilitiesController(processUtilitiesView, clientAccountService, returnEmployeeController(), loggedUser, activityService, clientAccountValidator);
        }
    }

    public void setClientComboBox(){
        ArrayList<Client> clients = clientService.findAll();
        employeeView.setClientComboBox(clients);
    }

    public void setClientAccountComboBox(){
        ArrayList<ClientAccount> clientAccounts = clientAccountService.findAll();
        employeeView.setClientAccountComboBox(clientAccounts);
    }
}

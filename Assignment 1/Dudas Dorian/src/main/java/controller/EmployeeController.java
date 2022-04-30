package controller;

import database.Constants;
import model.*;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import repository.security.RightsRolesRepository;
import service.account.AccountService;
import service.client.ClientService;
import service.employeeActivity.EmployeeActivityService;
import view.EmployeeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EmployeeController {
    private final EmployeeView employeeView;
    private LoginController loginController;
    private Optional<User> loggedInUser = Optional.empty();
    private final ClientService clientService;
    private final AccountService accountService;
    private final EmployeeActivityService employeeActivityService;
    private final RightsRolesRepository rightsRolesRepository;

    private final ClientValidator clientValidator;

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService, EmployeeActivityService employeeActivityService, RightsRolesRepository rightsRolesRepository, ClientValidator clientValidator) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.employeeActivityService = employeeActivityService;
        this.rightsRolesRepository = rightsRolesRepository;
        this.clientValidator = clientValidator;

        this.employeeView.addViewClientInfoButtonListener(new ShowClientsListener());
        this.employeeView.addClientCreatorButtonListener(new AddClientListener());
        this.employeeView.addClientUpdaterButtonListener(new UpdateClientListener());

        this.employeeView.addBackButtonListener(new BackButtonListener());

        this.employeeView.addViewClientAccountsButtonListener(new ShowAccountsListener());
        this.employeeView.addCreateClientAccountButtonListener(new CreateAccountListener());
        this.employeeView.addUpdateClientAccountsButtonListener(new UpdateAccountListener());
        this.employeeView.addDeleteClientAccountButtonListener(new DeleteAccountListener());
        this.employeeView.addTransferMoneyButtonListener(new TransferMoneyListener());
        this.employeeView.addBillAccountButtonListener(new ProcessBillListener());
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public Optional<User> getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Optional<User> loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private void refreshTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Full Name");
        model.addColumn("ID Number");
        model.addColumn("CNP");
        model.addColumn("Address");

        List<Client> clients = clientService.findAll();
        for(Client c : clients){
            Object[] row = {c.getId(), c.getFullName(), c.getIdNumber(), c.getCnp(), c.getAddress()};
            model.insertRow(model.getRowCount(), row);
        }
        employeeView.getClientInfoTable().setModel(model);
    }

    private void refreshTable2(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Client ID");
        model.addColumn("Account Type");
        model.addColumn("Money Amount");
        model.addColumn("Creation Date");

        List<Account> accounts = accountService.findAll();
        for(Account a : accounts){
            Object[] row = {a.getId(), a.getClientId(), a.getAccountType(), a.getMoneyAmount(), a.getCreationDate().toString()};
            model.insertRow(model.getRowCount(), row);
        }
        employeeView.getClientAccountsTable().setModel(model);
    }

    private class ShowClientsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.VIEW_CLIENT_INFO);
            saveEmployeeActivity(right.getId());
        }
    }

    private class AddClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = getTextFieldInfo();
            clientValidator.validate(newClient.getCnp(), newClient.getIdNumber());
            final List<String> errors = clientValidator.getErrors();
            if(errors.isEmpty()){
                clientService.save(newClient);
                refreshTable();
                Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.ADD_CLIENT_INFO);
                saveEmployeeActivity(right.getId());
            }
            else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getClientInfoTable().getSelectedRow();
            Long id = (Long) employeeView.getClientInfoTable().getValueAt(row, 0);
            Client updatedClient = getTextFieldInfo();
            clientService.updateById(id, updatedClient);
            refreshTable();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.UPDATE_CLIENT_INFO);
            saveEmployeeActivity(right.getId());
        }
    }

    private class BackButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            loginController.getLoginView().setVisible(true);
        }
    }

    private Client getTextFieldInfo(){
        String fullName = employeeView.getTfFullName().getText();
        String idNumber = employeeView.getTfIdNumber().getText();
        String cnp = employeeView.getTfCNP().getText();
        String address = employeeView.getTfAddress().getText();
        return new ClientBuilder().setFullName(fullName).setIdNumber(idNumber)
                .setCNP(cnp).setAddress(address).build();
    }

    private void saveEmployeeActivity(Long actionId){
        if(loggedInUser.isPresent()){
            User employee = loggedInUser.get();
            employeeActivityService.save(new EmployeeActivity(employee.getId(), actionId, new Date()));
        }
    }

    private class ShowAccountsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable2();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.VIEW_CLIENT_ACCOUNT);
            saveEmployeeActivity(right.getId());
        }
    }

    private class CreateAccountListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account newAccount = getTextFieldInfo2();
            if(newAccount.getMoneyAmount() < 0){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Money amount must be positive");
            }
            else{
                newAccount.setCreationDate(new Date());
                accountService.save(newAccount);
                refreshTable2();
                Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.CREATE_CLIENT_ACCOUNT);
                saveEmployeeActivity(right.getId());
            }
        }
    }

    private class UpdateAccountListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Account updatedAccount = getTextFieldInfo2();
            updatedAccount.setCreationDate(new Date());
            int row = employeeView.getClientAccountsTable().getSelectedRow();
            Long id = (Long) employeeView.getClientAccountsTable().getValueAt(row, 0);
            accountService.updateById(id, updatedAccount);
            refreshTable2();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.UPDATE_CLIENT_ACCOUNT);
            saveEmployeeActivity(right.getId());
        }
    }

    private class DeleteAccountListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = employeeView.getClientAccountsTable().getSelectedRow();
            Long id = (Long) employeeView.getClientAccountsTable().getValueAt(row, 0);
            accountService.removeById(id);
            refreshTable2();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.DELETE_CLIENT_ACCOUNT);
            saveEmployeeActivity(right.getId());
        }
    }

    private class TransferMoneyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id1 = Long.getLong(employeeView.getTfAccountId1().getText());
            Long id2 = Long.getLong(employeeView.getTfAccountId2().getText());
            Long transferAmount = Long.getLong(employeeView.getTfTransferAmount().getText());
            accountService.transferMoney(id1, id2, transferAmount);
            refreshTable2();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.TRANSFER_MONEY);
            saveEmployeeActivity(right.getId());
        }
    }

    private class ProcessBillListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long billAmount = Long.getLong(employeeView.getTfBill().getText());
            int row = employeeView.getClientAccountsTable().getSelectedRow();
            Long id = (Long) employeeView.getClientAccountsTable().getValueAt(row, 0);
            accountService.processBillsForId(id, billAmount);
            refreshTable2();
            Right right = rightsRolesRepository.findRightByTitle(Constants.Rights.PROCESS_UTILITIES);
            saveEmployeeActivity(right.getId());
        }
    }

    private Account getTextFieldInfo2(){
        String clientId = employeeView.getTfClientId().getText();
        String accountType = employeeView.getTfAccountType().getText();
        String moneyAmount = employeeView.getTfMoneyAmount().getText();
        return new AccountBuilder().setClientId(Long.parseLong(clientId)).setAccountType(accountType)
                .setMoneyAmount(Long.parseLong(moneyAmount)).build();
    }
}

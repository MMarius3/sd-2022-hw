package controller;

import model.Client;
import model.validator.ClientValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.client.ClientServiceMySQL;
import view.AccountView;
import view.EmployeeView;
import view.NewClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class EmployeeController {

    private final EmployeeView employeeView;
    private final NewClientView newClientView;
    private final AccountView accountView;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientServiceMySQL clientServiceMySQL;
    private final ClientValidator clientValidator;


    public EmployeeController(EmployeeView employeeView, ClientRepository clientRepository,
                              AccountRepository accountRepository,AccountView accountView) {
        this.employeeView = employeeView;
        newClientView = new NewClientView();
//        this.employeeView.setVisible(true);
        this.accountView=accountView;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        clientServiceMySQL = new ClientServiceMySQL(clientRepository);
        clientValidator = new ClientValidator(clientRepository);

        this.employeeView.addNewClientBtnListener(new NewClientButtonListener());
        this.employeeView.searchClientBtnListener(new SearchClientButtonListener());
        this.employeeView.updateClientBtnListener(new UpdateClientButtonListener());
        this.employeeView.accountOperationsBtnListener(new AccountOperationsButtonListener());
        newClientView.addNewClientBtnListener(new SaveNewClientButtonListener());
    }

    private class NewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            newClientView.setVisible(true);
        }
    }

    private class SaveNewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String name = newClientView.getClientName();
            String cardNumber = newClientView.getCardNumber();
            String numericalCode = newClientView.getNumericalCode();
            String address = newClientView.getAddress();

            clientValidator.validate(name, cardNumber, numericalCode, address);
            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientServiceMySQL.register(cardNumber, numericalCode, address, name);
                JOptionPane.showMessageDialog(newClientView.getContentPane(), "Successfully saved new client");

            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }


        }
    }


    private class SearchClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String numericalCode = employeeView.getSearchedNumericalCode();

            Client foundClient = clientRepository.findByNumericalCode(numericalCode);

            if (foundClient == null) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client not found with this personal numerical code");
            } else {
                employeeView.setClientFields(foundClient.getPersonal_numerical_code(), foundClient.getId_card_number(),
                        foundClient.getAddress(), foundClient.getCreated_at(), foundClient.getName());
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name=employeeView.getClientName();
            String idCardNumber=employeeView.getClientIdCardNumber();
            String personalCode=  employeeView.getClientPersonalCode();
            String address= employeeView.getClientAddress();

            clientValidator.validateForUpdate(name, idCardNumber, personalCode, address);

            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                clientRepository.updateClient(name, idCardNumber,personalCode,address);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Changes saved");

            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }

        }
    }

    private class AccountOperationsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            accountView.setVisible(true);
        }
    }
}

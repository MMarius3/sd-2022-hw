package controller;

import model.Account;
import model.Client;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import service.user.EmployeeService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final EmployeeService employeeService;
    private String senderCardNumber;
    private String receiverCardNumber;
    private final ClientValidator clientValidator;
    private final AccountValidator accountValidator;

    public EmployeeController(EmployeeView employeeView, EmployeeService employeeService, ClientValidator clientValidator, AccountValidator accountValidator) {
        this.employeeView = employeeView;
        this.employeeService = employeeService;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;


        this.employeeView.addNewClientButtonListener(new AddNewClientButtonListener());
        this.employeeView.viewClientsButtonListener(new ViewClientsButtonListener());
        this.employeeView.updateClientButtonListener(new UpdateClientButtonListener());
        this.employeeView.createAccountButtonListener(new CreateAccountButtonListener());
        this.employeeView.viewAccountsButtonListener(new ViewAccountsButtonListener());
        this.employeeView.updateAccountButtonListener(new UpdateAccountButtonListener());
        this.employeeView.deleteAccountButtonListener(new DeleteAccountButtonListener());
        this.employeeView.processBillButtonListener(new ProcessBillButtonListener());
        this.employeeView.selectSenderButtonListener(new SelectSenderButtonListener());
        this.employeeView.selectReceiverButtonListener(new SelectReceiverButtonListener());
        this.employeeView.transferMoneyButtonListener(new TransferMoneyButtonListener());
    }



    private class AddNewClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getName();
            String address = employeeView.getAddress();
            String cardNumber = employeeView.getIDCardNumber();
            String cnp = employeeView.getCNP();
            clientValidator.validate(name,cnp,cardNumber);
            final List<String> errors = clientValidator.getErrors();
            if(errors.isEmpty()) {
                employeeService.addNewClient(name, cnp, cardNumber, address);
            }else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(),clientValidator.getFormattedErrors());
            }
        }
    }

    private class ViewClientsButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = employeeService.findAll();
            employeeView.setViewClientsTable(clients);
        }
    }

    private class CreateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = (Long) employeeView.getIdFromTable(employeeView.getClientTableSelection());
            String cardNumber = employeeView.getCardNumber();
            String type = employeeView.getAccountType();
            Float sumOfMoney = Float.parseFloat(employeeView.getSumOfMoney());
            accountValidator.validate(cardNumber,type,sumOfMoney);
            final List<String> errors = accountValidator.getErrors();
            if(errors.isEmpty()) {
                employeeService.createAccount(id, cardNumber, type, sumOfMoney);
            }else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(),accountValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        Long id = (Long) employeeView.getIdFromTable(employeeView.getClientTableSelection());
        String name = employeeView.getName();
        String address = employeeView.getAddress();
        String cardNumber = employeeView.getIDCardNumber();
        String cnp = employeeView.getCNP();
        if(name.isEmpty() && cnp.isEmpty() && cardNumber.isEmpty() && address.isEmpty()){
            JOptionPane.showMessageDialog(employeeView.getContentPane(),"Please enter fields to be updated");
        }else {
            if (!name.isEmpty()) {
                clientValidator.validateNameOnly(name);
            }
            if (!cnp.isEmpty()) {
                clientValidator.validateCnpOnly(cnp);
            }
            if (!cardNumber.isEmpty()) {
                clientValidator.validateIdCardOnly(cardNumber);
            }
            final List<String> errors = clientValidator.getErrors();
            if(errors.isEmpty()){
                employeeService.updateClient(id,name,cnp,cardNumber,address);
            }else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(),clientValidator.getFormattedErrors());
            }
        }

        }
    }
    private class ViewAccountsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Account> accounts = employeeService.findAllAccounts();
            employeeView.setViewAccountsTable(accounts);
        }
    }

    private class UpdateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardNumber = (String) employeeView.getCardNumberFromTable(employeeView.getAccountTableSelection());
            String type = employeeView.getAccountType();
            String newCardNumber = employeeView.getCardNumber();
            String sum = employeeView.getSumOfMoney();
            Float sumOfMoney = -1.0f;
            if(!sum.isEmpty()) {
                sumOfMoney = Float.parseFloat(employeeView.getSumOfMoney());
            }
            if(type.isEmpty() && newCardNumber.isEmpty() && sum.isEmpty()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Please enter fields to be updated!");
            }else{
                if(!type.isEmpty()){
                    accountValidator.validateTypeOnly(type);
                }
                if(!newCardNumber.isEmpty()){
                    accountValidator.validateCardOnly(newCardNumber);
                }
                if(!sum.isEmpty()){
                    accountValidator.validateSumOnly(sumOfMoney);
                }

                final List<String> errors = accountValidator.getErrors();
                if(errors.isEmpty()){
                    employeeService.updateAccount(cardNumber,newCardNumber,type,sumOfMoney);
                }else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),accountValidator.getFormattedErrors());
                }

            }

        }
    }

    private class DeleteAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardNumber = (String) employeeView.getCardNumberFromTable(employeeView.getAccountTableSelection());
            employeeService.deleteAccount(cardNumber);
        }
    }

    private class ProcessBillButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardNumber = (String) employeeView.getCardNumberFromTable(employeeView.getAccountTableSelection());
            Float amount = Float.parseFloat(employeeView.getAmountToPay());
            if(!employeeService.processBill(cardNumber, amount)){
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Not enough funds!");
            }
        }
    }

    private class SelectSenderButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
             senderCardNumber = (String) employeeView.getCardNumberFromTable(employeeView.getAccountTableSelection());
        }
    }

    private class SelectReceiverButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            receiverCardNumber = (String) employeeView.getCardNumberFromTable(employeeView.getAccountTableSelection());
        }

    }

    private class TransferMoneyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Float sumToBeTransferred = Float.parseFloat(employeeView.getSumToBeTransferred());
            if(!employeeService.transferMoney(senderCardNumber,receiverCardNumber,sumToBeTransferred)){
                JOptionPane.showMessageDialog(employeeView.getContentPane(),"Sender doesn't have enough funds!");
            }
        }
    }



}

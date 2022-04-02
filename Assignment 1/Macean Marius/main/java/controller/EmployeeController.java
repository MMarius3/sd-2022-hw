package controller;

import model.validator.AccountValidator;
import model.validator.ClientValidator;
import service.client.ClientService;
import service.account.AccountService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ClientValidator clientValidator;
    private final AccountValidator accountValidator;

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService, ClientValidator clientValidator, AccountValidator accountValidator) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;

        this.employeeView.addAddClientButtonListener(new AddClientButtonListener());
        this.employeeView.addUpdateClientButtonListener(new UpdateClientButtonListener());
        this.employeeView.addViewClientButtonListener(new ViewClientButtonListener());
        this.employeeView.addAddAccountButtonListener(new AddAccountButtonListener());
        this.employeeView.addUpdateAccountButtonListener(new UpdateAccountButtonListener());
        this.employeeView.addDeleteAccountButtonListener(new DeleteAccountButtonListener());
        this.employeeView.addViewAccountButtonListener(new ViewAccountButtonListener());
        this.employeeView.addTransferMoneyButtonListener(new TransferMoneyButtonListener());
        this.employeeView.addExecuteBillButtonListener(new ExecuteBillButtonListener());
        this.employeeView.setVisible(true);
    }

    private class AddClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getClientName();
            String idCardNumberString = employeeView.getClientIdCardNumber();
            String idCodeString = employeeView.getClientIdCode();

            clientValidator.validateAdd(name, idCardNumberString, idCodeString);

            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                Integer idCardNumber = Integer.parseInt(idCardNumberString);
                Long idCode = Long.parseLong(employeeView.getClientIdCode());
                clientService.addClient(name, idCardNumber, idCode);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getClientId();
            String name = employeeView.getClientName();
            String idCardNumberString = employeeView.getClientIdCardNumber();
            String idCodeString = employeeView.getClientIdCode();

            clientValidator.validateUpdate(idString, name, idCardNumberString, idCodeString);

            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.parseLong(idString);
                Integer idCardNumber = Integer.parseInt(idCardNumberString);
                Long idCode = Long.parseLong(employeeView.getClientIdCode());
                clientService.updateClient(id, name, idCardNumber, idCode);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class ViewClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getClientId();

            clientValidator.validateView(idString);

            final List<String> errors = clientValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.parseLong(idString);
                clientService.viewClient(id);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getFormattedErrors());
            }
        }
    }

    private class AddAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idClientString = employeeView.getClientId();
            String type = employeeView.getAccountType();
            String moneyAmountString = employeeView.getAccountMoneyAmount();

            accountValidator.validateAdd(type, moneyAmountString, idClientString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long idClient = Long.getLong(idClientString);
                Integer moneyAmount = Integer.parseInt(moneyAmountString);
                accountService.addAccount(type, moneyAmount, idClient);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getAccountId();
            String type = employeeView.getAccountType();
            String moneyAmountString = employeeView.getAccountMoneyAmount();

            accountValidator.validateUpdate(idString, type, moneyAmountString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.getLong(idString);
                Integer moneyAmount = Integer.parseInt(moneyAmountString);
                accountService.updateAccount(id, type, moneyAmount);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getAccountId();

            accountValidator.validateDelete(idString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.parseLong(idString);
                accountService.deleteAccount(id);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class ViewAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getAccountId();

            accountValidator.validateView(idString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.parseLong(idString);
                accountService.deleteAccount(id);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class TransferMoneyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id1String = employeeView.getAccountId();
            String id2String = employeeView.getAccountIdToOperateWith();
            String moneyAmountToTransferString = employeeView.getMoneyAmountToOperateWith();

            accountValidator.validateTransfer(id1String, id2String, moneyAmountToTransferString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long id1 = Long.parseLong(id1String);
                Long id2 = Long.parseLong(id2String);
                Integer moneyAmountToTransfer = Integer.getInteger(moneyAmountToTransferString);
                accountService.transferMoney(id1, id2, moneyAmountToTransfer);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }

    private class ExecuteBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = employeeView.getAccountIdToOperateWith();
            String billToExecuteString = employeeView.getMoneyAmountToOperateWith();

            accountValidator.validateBill(idString, billToExecuteString);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Long id = Long.parseLong(idString);
                Integer billToExecute = Integer.getInteger(billToExecuteString);
                accountService.executeBill(id, billToExecute);
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }
}

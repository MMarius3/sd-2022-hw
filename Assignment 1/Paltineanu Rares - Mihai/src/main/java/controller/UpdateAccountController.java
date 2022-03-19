package controller;

import model.Account;
import model.validator.ClientAccountValidator;
import service.client.ClientService;
import view.EmployeeView;
import view.client.account.UpdateAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class UpdateAccountController {
    private final UpdateAccountView updateAccountView;
    private final ClientAccountValidator clientAccountValidator;
    private final ClientService<Account, Long> accountService;
    private final EmployeeView employeeView;

    private final Long accountId;
    public UpdateAccountController(UpdateAccountView updateAccountView,
                                   ClientAccountValidator clientAccountValidator,
                                   ClientService<Account, Long> accountService,
                                   EmployeeView employeeView,
                                   Long accountId) {
        this.updateAccountView = updateAccountView;
        this.clientAccountValidator = clientAccountValidator;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.accountId = accountId;

        initializeTextFields();
        this.updateAccountView.setCancelAddInformationListener(new CancelButtonListener());
        this.updateAccountView.setActionButtonListener(new UpdateButtonListener());
    }

    private void initializeTextFields() {
        Account account = accountService.findById(accountId);
        updateAccountView.getIdField().setText(String.valueOf(account.getClient_id()));
        updateAccountView.getMoneyField().setText(String.valueOf(account.getMoney()));
        updateAccountView.getTypeField().setText(account.getType());
        updateAccountView.getNumberField().setText(account.getNumber());
        updateAccountView.getCreationField().setText(String.valueOf(account.getDate()));
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateAccountView.setVisible(false);
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientId = updateAccountView.getIdField().getText();
            String number = updateAccountView.getNumberField().getText();
            String type = updateAccountView.getTypeField().getText();
            String money = updateAccountView.getMoneyField().getText();

            clientAccountValidator.validate(clientId, number, type, money);

            final List<String> errors = clientAccountValidator.getErrors();

            if(errors.isEmpty()) {
                Account account = Account.builder()
                        .id(accountId)
                        .client_id(Long.parseLong(clientId))
                        .type(type)
                        .number(number)
                        .money(Integer.parseInt(money))
                        .date(new Date(1000, 10, 10))
                        .build();
                boolean flag = accountService.update(accountId, account);
                if(flag) {
                    JOptionPane.showMessageDialog(updateAccountView.getContentPane(),
                            "Account updated successful");
                    updateAccountView.setVisible(false);
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                }
            } else {
                JOptionPane.showMessageDialog(updateAccountView.getContentPane(), clientAccountValidator.getFormattedErrors());
            }

        }
    }
}

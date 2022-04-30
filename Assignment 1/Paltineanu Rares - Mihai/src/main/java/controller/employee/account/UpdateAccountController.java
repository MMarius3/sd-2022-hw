package controller.employee.account;

import model.Account;
import model.Action;
import model.User;
import model.validator.AccountValidator;
import service.action.ActionService;
import service.client.ClientService;
import view.EmployeeView;
import view.client.account.ActionAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.UPDATE_ACCOUNT;

public class UpdateAccountController {
    private final ActionAccountView actionAccountView;
    private final AccountValidator accountValidator;
    private final ClientService<Account, Long> accountService;
    private final EmployeeView employeeView;
    private final ActionService actionService;
    private final User user;
    private final Long accountId;

    public UpdateAccountController(ActionAccountView actionAccountView,
                                   AccountValidator accountValidator,
                                   ClientService<Account, Long> accountService,
                                   EmployeeView employeeView,
                                   ActionService actionService, User user, Long accountId) {
        this.actionAccountView = actionAccountView;
        this.accountValidator = accountValidator;
        this.accountService = accountService;
        this.employeeView = employeeView;
        this.actionService = actionService;
        this.user = user;
        this.accountId = accountId;

        initializeFields();
        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        this.actionAccountView.setTitle("Update account");
        this.actionAccountView.setCancelAddInformationListener(new CancelButtonListener());
        this.actionAccountView.setActionButtonListener(new UpdateButtonListener());
    }

    private void initializeFields() {
        Account account = accountService.findById(accountId);
        actionAccountView.setIdTextField(String.valueOf(account.getClient_id()));
        actionAccountView.setMoneyTextField(String.valueOf(account.getMoney()));
        actionAccountView.setTypeTextField(account.getType());
        actionAccountView.setNumberTextField(account.getNumber());

        actionAccountView.setActionButtonText("Update");
        addCreationDateField(account);
    }

    private void addCreationDateField(Account account) {
        JLabel creationLabel = new JLabel("Creation date");
        creationLabel.setBounds(30, 130, 100, 30);
        actionAccountView.addComponentToContainer(creationLabel);

        JLabel creationField = new JLabel(String.valueOf(account.getDate()));
        creationField.setBounds(130, 130, 120, 30);
        actionAccountView.addComponentToContainer(creationField);
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionAccountView.setVisible(false);
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientId = actionAccountView.getIdText();
            String number = actionAccountView.getNumberText();
            String type = actionAccountView.getTypeText();
            String money = actionAccountView.getMoneyText();

            Account account = accountService.findById(accountId);

            boolean verifyCardUniqueness = !account.getNumber().equals(number);
            accountValidator.validate(clientId, number, money, verifyCardUniqueness);

            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()) {
                Account newAccount = Account.builder()
                        .id(accountId)
                        .client_id(Long.parseLong(clientId))
                        .type(type)
                        .number(number)
                        .money(Integer.parseInt(money))
                        .date(Date.valueOf(LocalDate.now()))
                        .build();
                boolean flag = accountService.update(accountId, newAccount);
                if(flag) {
                    JOptionPane.showMessageDialog(actionAccountView.getContentPane(),
                            "Account updated successful");
                    actionAccountView.setVisible(false);
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(UPDATE_ACCOUNT)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                    employeeView.getAccountView().clickViewButton();
                }
            } else {
                JOptionPane.showMessageDialog(actionAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }

        }
    }
}

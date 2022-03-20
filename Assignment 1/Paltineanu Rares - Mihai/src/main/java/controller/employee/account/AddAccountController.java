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

import static database.Constants.Actions.ADD_ACCOUNT;

public class AddAccountController {
    private final ActionAccountView addAccountView;
    private final EmployeeView employeeView;
    private final ClientService<Account, Long> clientService;
    private final AccountValidator accountValidator;
    private final ActionService actionService;
    private final User user;
    public AddAccountController(ActionAccountView addAccountView,
                                ClientService<Account, Long> clientService,
                                AccountValidator accountValidator,
                                EmployeeView employeeView, ActionService actionService, User user){
        this.addAccountView = addAccountView;
        this.clientService = clientService;
        this.accountValidator = accountValidator;
        this.employeeView = employeeView;
        this.actionService = actionService;
        this.user = user;

        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
        addAccountView.setActionButtonListener(new AddAccountButtonListener());
        addAccountView.setCancelAddInformationListener(new CancelButtonListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addAccountView.setVisible(false);
        }
    }

    private class AddAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientId = addAccountView.getIdField().getText();
            String number = addAccountView.getNumberField().getText();
            String type = addAccountView.getTypeField().getText();
            String money = addAccountView.getMoneyField().getText();

            accountValidator.validate(clientId, number, money, false);

            final List<String> errors = accountValidator.getErrors();

            if(errors.isEmpty()) {
                Account account =  Account.builder()
                        .type(type)
                        .money(Integer.parseInt(money))
                        .number(number)
                        .client_id(Long.parseLong(clientId))
                        .date(Date.valueOf(LocalDate.now()))
                        .build();
                boolean flag = clientService.save(account);
                if(flag) {
                    JOptionPane.showMessageDialog(addAccountView.getContentPane(),
                            "Account added successful");
                    addAccountView.setVisible(false);
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(ADD_ACCOUNT)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                }

            } else {
                JOptionPane.showMessageDialog(addAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }
        }
    }
}

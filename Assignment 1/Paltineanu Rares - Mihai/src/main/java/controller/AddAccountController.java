package controller;

import model.Account;
import model.validator.AccountValidator;
import service.client.ClientService;
import view.EmployeeView;
import view.client.account.AddAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class AddAccountController {
    private final AddAccountView addAccountView;
    private final EmployeeView employeeView;
    private final ClientService<Account, Long> clientService;
    private final AccountValidator accountValidator;
    public AddAccountController(AddAccountView addAccountView,
                                    ClientService<Account, Long> clientService,
                                AccountValidator accountValidator,
                                EmployeeView employeeView){
        this.addAccountView = addAccountView;
        this.clientService = clientService;
        this.accountValidator = accountValidator;
        this.employeeView = employeeView;

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
                        .date(new Date(1000, 10, 10))
                        .build();
                boolean flag = clientService.save(account);
                if(flag) {
                    JOptionPane.showMessageDialog(addAccountView.getContentPane(),
                            "Account added successful");
                    addAccountView.setVisible(false);
                    employeeView.getAccountView().getBtnViewClientAccount().doClick();
                }

            } else {
                JOptionPane.showMessageDialog(addAccountView.getContentPane(), accountValidator.getFormattedErrors());
            }

//            String creation = addAccountView.getNumberField().getText();
        }
    }
}

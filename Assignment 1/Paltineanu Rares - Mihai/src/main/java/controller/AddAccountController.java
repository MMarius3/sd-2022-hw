package controller;

import model.Account;
import model.validator.ClientAccountValidator;
import service.client.ClientService;
import view.client.account.AddAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class AddAccountController {
    private final AddAccountView addAccountView;
    private final ClientService<Account, Long> clientService;
    private final ClientAccountValidator clientAccountValidator;
    public AddAccountController(AddAccountView addAccountView,
                                    ClientService<Account, Long> clientService,
                                ClientAccountValidator clientAccountValidator){
        this.addAccountView = addAccountView;
        this.clientService = clientService;
        this.clientAccountValidator = clientAccountValidator;

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

            clientAccountValidator.validate(clientId, number, type, money);

            final List<String> errors = clientAccountValidator.getErrors();

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
                            "Account added successfully");
                    addAccountView.setVisible(false);
                }

            } else {
                JOptionPane.showMessageDialog(addAccountView.getContentPane(), clientAccountValidator.getFormattedErrors());
            }

//            String creation = addAccountView.getNumberField().getText();
        }
    }
}

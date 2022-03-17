package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validator.ClientInformationValidator;
import service.client.ClientService;
import view.client.account.AddAccountView;
import view.client.information.AddInformationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddAccountController {
    private final AddAccountView addAccountView;
    private final ClientService<Client, Long> clientService;
    public AddAccountController(AddAccountView addAccountView,
                                    ClientService<Client, Long> clientService){
        this.addAccountView = addAccountView;
        this.clientService = clientService;

        addAccountView.setAddInformationListener(new AddAccountButtonListener());
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

//            String creation = addAccountView.getNumberField().getText();
        }

        private boolean isInteger(String text) {
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
}

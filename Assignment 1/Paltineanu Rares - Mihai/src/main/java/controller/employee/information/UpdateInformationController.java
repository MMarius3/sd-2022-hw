package controller.employee.information;

import model.Action;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validator.ClientInformationValidator;
import service.action.ActionService;
import service.client.ClientService;
import view.EmployeeView;
import view.client.information.ActionInformationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.UPDATE_CLIENT;

public class UpdateInformationController {
    private final ActionInformationView actionInformationView;
    private final ClientInformationValidator clientInformationValidator;
    private final ClientService<Client, Long> clientService;
    private final EmployeeView employeeView;

    private final ActionService actionService;
    private final User user;
    private final Long clientId;

    public UpdateInformationController(ActionInformationView actionInformationView,
                                       ClientInformationValidator clientInformationValidator,
                                       ClientService<Client, Long> clientService,
                                       EmployeeView employeeView,
                                       ActionService actionService, User user, Long clientId) {
        this.actionInformationView = actionInformationView;
        this.clientInformationValidator = clientInformationValidator;
        this.clientService = clientService;
        this.employeeView = employeeView;
        this.actionService = actionService;
        this.user = user;
        this.clientId = clientId;

        initializeTextFields();
        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
        actionInformationView.setAddInformationListener(new UpdateInformationButtonListener());
        actionInformationView.setCancelAddInformationListener(new CancelButtonListener());
    }

    private void initializeTextFields() {
        Client client = clientService.findById(clientId);
        actionInformationView.setAddressTextField(client.getAddress());
        actionInformationView.setNameTextField(client.getName());
        actionInformationView.setCnpTextField(client.getCNP());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionInformationView.setVisible(false);
        }
    }

    private class UpdateInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = actionInformationView.getNameText();
            String address = actionInformationView.getAddressText();
            String cnp = actionInformationView.getCNPText();

            Client client = clientService.findById(clientId);
            boolean validatePNCUniqueness = !client.getCNP().equals(cnp);
            clientInformationValidator.validate(name, cnp, validatePNCUniqueness);

            final List<String> errors = clientInformationValidator.getErrors();

            if (errors.isEmpty()) {
                Client newClient = new ClientBuilder()
                        .setId(clientId)
                        .setName(name)
                        .setAddress(address)
                        .setCNP(cnp)
                        .build();
                boolean flag = clientService.update(clientId, newClient);
                if (flag) {
                    JOptionPane.showMessageDialog(actionInformationView.getContentPane(),
                            "Client updated successful");
                    actionInformationView.setVisible(false);
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(UPDATE_CLIENT)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                    employeeView.getInformationView().clickViewButton();
                }

            } else {
                JOptionPane.showMessageDialog(actionInformationView.getContentPane(), clientInformationValidator.getFormattedErrors());
            }
        }
    }
}

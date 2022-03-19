package controller.employee.information;

import model.Action;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validator.ClientInformationValidator;
import service.action.ActionService;
import service.client.ClientService;
import view.EmployeeView;
import view.client.information.UpdateInformationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.UPDATE_CLIENT;

public class UpdateInformationController {
    private final UpdateInformationView updateInformationView;
    private final ClientInformationValidator clientInformationValidator;
    private final ClientService<Client, Long> clientService;
    private final EmployeeView employeeView;

    private final ActionService actionService;
    private final User user;
    private final Long clientId;
    public UpdateInformationController(UpdateInformationView updateInformationView,
                                       ClientInformationValidator clientInformationValidator,
                                       ClientService<Client, Long> clientService,
                                       EmployeeView employeeView,
                                       ActionService actionService, User user, Long clientId) {
        this.updateInformationView = updateInformationView;
        this.clientInformationValidator = clientInformationValidator;
        this.clientService = clientService;
        this.employeeView = employeeView;
        this.actionService = actionService;
        this.user = user;
        this.clientId = clientId;

        initializeTextFields();
        updateInformationView.setAddInformationListener(new UpdateInformationButtonListener());
        updateInformationView.setCancelAddInformationListener(new CancelButtonListener());
    }

    private void initializeTextFields() {
        Client client = clientService.findById(clientId);
        updateInformationView.getAddressField().setText(client.getAddress());
        updateInformationView.getNameField().setText(client.getName());
        updateInformationView.getCnpField().setText(client.getCNP());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateInformationView.setVisible(false);
        }
    }

    private class UpdateInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = updateInformationView.getName();
            String address = updateInformationView.getAddress();
            String cnp = updateInformationView.getCNP();

            clientInformationValidator.validate(name, cnp);

            final List<String> errors = clientInformationValidator.getErrors();

            if(errors.isEmpty()) {
                Client client = new ClientBuilder()
                        .setId(clientId)
                        .setName(name)
                        .setAddress(address)
                        .setCNP(cnp)
                        .build();
                boolean flag = clientService.update(clientId, client);
                if(flag) {
                    JOptionPane.showMessageDialog(updateInformationView.getContentPane(),
                            "Client updated successful");
                    updateInformationView.setVisible(false);
                    employeeView.getInformationView().getBtnViewClientInformation().doClick();
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(UPDATE_CLIENT)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                }

            } else {
                JOptionPane.showMessageDialog(updateInformationView.getContentPane(), clientInformationValidator.getFormattedErrors());
            }
        }
    }
}

package controller.employee.information;

import model.Action;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validator.ClientInformationValidator;
import service.action.ActionService;
import service.client.ClientService;
import view.EmployeeView;
import view.client.information.AddInformationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Actions.ADD_CLIENT;

public class AddInformationController {
    private final AddInformationView addInformationView;
    private final ClientInformationValidator clientInformationValidator;
    private final ClientService<Client, Long> clientService;
    private final EmployeeView employeeView;
    private final ActionService actionService;
    private final User user;

    public AddInformationController(AddInformationView addInformationView,
                                    ClientInformationValidator clientInformationValidator,
                                    ClientService<Client, Long> clientService,
                                    EmployeeView employeeView, ActionService actionService, User user) {
        this.addInformationView = addInformationView;
        this.clientInformationValidator = clientInformationValidator;
        this.clientService = clientService;
        this.employeeView = employeeView;
        this.actionService = actionService;
        this.user = user;

        addInformationView.setAddInformationListener(new AddInformationButtonListener());
        addInformationView.setCancelAddInformationListener(new CancelButtonListener());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addInformationView.setVisible(false);
        }
    }

    private class AddInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = addInformationView.getName();
            String address = addInformationView.getAddress();
            String cnp = addInformationView.getCNP();

            clientInformationValidator.validate(name, cnp);

            final List<String> errors = clientInformationValidator.getErrors();

            if(errors.isEmpty()) {
                Client client = new ClientBuilder()
                        .setName(name)
                        .setAddress(address)
                        .setCNP(cnp)
                        .build();
                boolean flag = clientService.save(client);
                if(flag) {
                    JOptionPane.showMessageDialog(addInformationView.getContentPane(),
                            "Client added successful");
                    addInformationView.setVisible(false);
                    employeeView.getInformationView().getBtnViewClientInformation().doClick();
                    actionService.save(Action.builder()
                            .user_id(user.getId())
                            .action(ADD_CLIENT)
                            .date(Date.valueOf(LocalDate.now()))
                            .build());
                }

            } else {
                JOptionPane.showMessageDialog(addInformationView.getContentPane(), clientInformationValidator.getFormattedErrors());
            }
        }
    }
}

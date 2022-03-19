package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validator.ClientInformationValidator;
import service.client.ClientService;
import view.EmployeeView;
import view.client.information.AddInformationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddInformationController {
    private final AddInformationView addInformationView;
    private final ClientInformationValidator clientInformationValidator;
    private final ClientService<Client, Long> clientService;
    private final EmployeeView employeeView;
    public AddInformationController(AddInformationView addInformationView,
                                    ClientInformationValidator clientInformationValidator,
                                    ClientService<Client, Long> clientService,
                                    EmployeeView employeeView) {
        this.addInformationView = addInformationView;
        this.clientInformationValidator = clientInformationValidator;
        this.clientService = clientService;
        this.employeeView = employeeView;

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
                }

            } else {
                JOptionPane.showMessageDialog(addInformationView.getContentPane(), clientInformationValidator.getFormattedErrors());
            }
        }
    }
}

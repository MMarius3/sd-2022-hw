package controller;

import model.Client;
import model.builder.ClientBuilder;
import service.client.ClientService;
import view.AddClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddClientController {

    private final AddClientView addClientView;

    private final Connection connection;

    private final ClientService clientService;

    public AddClientController(AddClientView addClientView, Connection connection, ClientService clientService) {
        this.addClientView = addClientView;
        this.clientService = clientService;
        this.addClientView.addClientButtonListener(new AddClientController.AddClientButtonListener());
        this.connection = connection;
    }

    private class AddClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = new ClientBuilder()
                    .setAddress(addClientView.getAddressTextField())
                    .setPersNumCode(addClientView.getPncTextField())
                    .setName(addClientView.getName())
                    .setIdCardNumber(addClientView.getIcnTextField())
                    .build();

            clientService.save(client);
        }
    }
}

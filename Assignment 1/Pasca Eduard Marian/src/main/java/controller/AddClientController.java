package controller;

import model.Client;
import model.builder.ClientBuilder;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import view.AddClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddClientController {

    private final AddClientView addClientView;

    private final Connection connection;

    public AddClientController(AddClientView addClientView, Connection connection){
        this.addClientView = addClientView;
        this.addClientView.addClientButtonListener(new AddClientController.AddClientButtonListener());
        this.connection = connection;

        this.addClientView.setVisible(true);
    }

    private class AddClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            Client client = new ClientBuilder()
                    .setAddress(addClientView.getAddressTextField())
                    .setPersNumCode(addClientView.getPncTextField())
                    .setName(addClientView.getName())
                    .setIdCardNumber(addClientView.getIcnTextField())
                    .build();

            ClientService clientService = new ClientServiceMySQL(new ClientRepositoryMySQL(connection));
            clientService.save(client);
        }
    }
}

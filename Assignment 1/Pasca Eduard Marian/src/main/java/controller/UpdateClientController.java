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

public class UpdateClientController {

    private final UpdateClientView updateClientView;

    private final Connection connection;

    public UpdateClientController(UpdateClientView updateAccountView, Connection connection){
        this.updateClientView = updateClientView;
        this.updateClientView.updateClientButtonListener(new UpdateClientController.UpdateAccountButtonListener());
        this.connection = connection;

        this.updateClientView.set
    }

    private class UpdateAccountButtonListener implements ActionListener {

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

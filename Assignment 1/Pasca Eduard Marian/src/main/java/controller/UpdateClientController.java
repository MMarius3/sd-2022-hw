package controller;

import model.Client;
import model.builder.ClientBuilder;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import view.UpdateClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UpdateClientController {

    private final UpdateClientView updateClientView;

    private final Connection connection;

    public UpdateClientController(UpdateClientView updateClientView, Connection connection){
        this.updateClientView = updateClientView;
        this.updateClientView.updateClientButtonListener(new UpdateClientController.UpdateClientButtonListener());
        this.connection = connection;
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            Client client = new ClientBuilder()
                    .setId(updateClientView.getIcnTextField())
                    .setAddress(updateClientView.getAddressTextField())
                    .setPersNumCode(updateClientView.getPncTextField())
                    .setName(updateClientView.getName())
                    .setIdCardNumber(updateClientView.getIcnTextField())
                    .build();

            ClientService clientService = new ClientServiceMySQL(new ClientRepositoryMySQL(connection));
            clientService.save(client);
        }
    }
}

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

    private final ClientService clientService;

    public UpdateClientController(UpdateClientView updateClientView, Connection connection, ClientService clientService){
        this.updateClientView = updateClientView;
        this.clientService = clientService;
        this.updateClientView.updateClientButtonListener(new UpdateClientController.UpdateClientButtonListener());
        this.connection = connection;
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            Client client = new ClientBuilder()
                    .setId(updateClientView.getId())
                    .setAddress(updateClientView.getAddressTextField())
                    .setPersNumCode(updateClientView.getPncTextField())
                    .setName(updateClientView.getNameTextField())
                    .setIdCardNumber(updateClientView.getIcnTextField())
                    .build();

            clientService.save(client);
        }
    }
}

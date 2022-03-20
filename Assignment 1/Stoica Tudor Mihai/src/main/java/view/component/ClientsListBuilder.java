package view.component;

import model.Client;

import javax.swing.*;
import java.util.List;

public class ClientsListBuilder extends ComponentsFetcher {

    private JList<String> clientsList;

    public ClientsListBuilder setClientsList(List<Client> clients) {

        List<String> clientsNames = clients.stream().map(Client::getName).toList();

        String[] clientsArray = new String[clients.size()];
        clientsArray = clientsNames.toArray(clientsArray);
        clientsList = new JList<String>(clientsArray);

        this.addComponent(clientsList);
        return this;
    }
}

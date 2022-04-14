package service;

import model.Client;

public interface RegularUserClientInformationActions {

    long createClient(Client client);

    void updateClient(Client client);

    Client getClient();
}

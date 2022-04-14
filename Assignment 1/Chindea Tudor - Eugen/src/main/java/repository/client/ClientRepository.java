package repository.client;

import controller.Response;
import model.Client;

public interface ClientRepository {

    void addClient(Client client);

    void updateClientAddress(String cnp, String address);

    Client findClientByCnp(String cnp);
    Response<Boolean> existsByCNP(String CNP);
}

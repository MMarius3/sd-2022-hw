package service.client;

import javafx.collections.ObservableList;
import model.Client;

import java.util.List;

public interface ClientService {
    ObservableList<Client> findAll();

    Client findById(Long id);

    boolean save(Client client);

    boolean edit(Client client);
}

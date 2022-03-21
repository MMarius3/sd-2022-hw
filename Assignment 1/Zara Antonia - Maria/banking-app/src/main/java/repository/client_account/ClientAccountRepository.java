package repository.client_account;

import model.Client;
import model.ClientAccount;

import java.util.ArrayList;
import java.util.Optional;

public interface ClientAccountRepository {

    ArrayList<ClientAccount> findAll();

    boolean update(ClientAccount clientAccount);

    boolean delete(ClientAccount clientAccount);

    boolean add(ClientAccount clientAccount, Client client);

    Optional<Client> findOwnerOfAccount(ClientAccount clientAccount);

    boolean removeAll();
}

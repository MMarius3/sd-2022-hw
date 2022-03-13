package repository.client;

import model.Client;
import model.User;
import repository.Repository;

public interface ClientRepository extends Repository<Long, Client> {

    Client findByName(String name);

    Client findByCNP(String cnp);

    Client findByCardNumber(String cardNumber);

    boolean transferMoney(Client fromClient, Client toClient, int amount);
}

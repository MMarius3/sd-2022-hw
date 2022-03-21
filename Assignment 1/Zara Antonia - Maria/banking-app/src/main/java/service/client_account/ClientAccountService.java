package service.client_account;

import model.ClientAccount;

import java.util.ArrayList;
import java.util.Optional;

public interface ClientAccountService {

    ArrayList<ClientAccount> findAll();

    boolean add(ClientAccount clientAccount);

    boolean update(ClientAccount clientAccount, String type, Optional<Double> amount);

    boolean delete(ClientAccount clientAccount);

    boolean transferMoney(ClientAccount fromAccount, ClientAccount toAccount, Double amount);

    boolean processUtility(ClientAccount clientAccount, String utility, Double amount);
}

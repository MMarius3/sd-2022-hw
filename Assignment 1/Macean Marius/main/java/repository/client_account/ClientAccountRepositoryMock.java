package repository.client_account;

import model.Client_Account;

import java.util.ArrayList;
import java.util.List;

public class ClientAccountRepositoryMock implements ClientAccountRepository {

    private List<Client_Account> client_accounts;

    public ClientAccountRepositoryMock() {
        client_accounts = new ArrayList<>();
    }

    @Override
    public boolean addClientAccount(Long clientId, Long accountId) {
        Client_Account client_account = new Client_Account();
        client_account.setClientId(clientId);
        client_account.setAccountId(accountId);
        return client_accounts.add(client_account);
    }
}
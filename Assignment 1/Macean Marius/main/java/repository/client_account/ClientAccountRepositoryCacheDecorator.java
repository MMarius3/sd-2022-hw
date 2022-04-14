package repository.client;

import model.Client;
import repository.Cache;
import repository.client_account.ClientAccountRepository;
import repository.client_account.ClientAccountRepositoryDecorator;

import java.util.List;
import java.util.Optional;

public class ClientAccountRepositoryCacheDecorator extends ClientAccountRepositoryDecorator {

    private Cache<Client> cache;

    public ClientAccountRepositoryCacheDecorator(ClientAccountRepository clientAccountRepository, Cache<Client> cache) {
        super(clientAccountRepository);
        this.cache = cache;
    }

    @Override
    public boolean addClientAccount(Long clientId, Long accountId) {
        cache.invalidateCache();
        return clientAccountDecoratedRepository.addClientAccount(clientId, accountId);
    }
}

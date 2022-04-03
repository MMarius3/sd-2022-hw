package repository.client_account;

public abstract class ClientAccountRepositoryDecorator implements ClientAccountRepository {

    protected ClientAccountRepository clientAccountDecoratedRepository;

    public ClientAccountRepositoryDecorator(ClientAccountRepository clientAccountRepository) {
        this.clientAccountDecoratedRepository = clientAccountRepository;
    }

}
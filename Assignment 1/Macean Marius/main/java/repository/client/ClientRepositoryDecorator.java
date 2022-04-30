package repository.client;

public abstract class ClientRepositoryDecorator implements ClientRepository {

    protected ClientRepository clientDecoratedRepository;

    public ClientRepositoryDecorator(ClientRepository clientRepository) {
        this.clientDecoratedRepository = clientRepository;
    }

}
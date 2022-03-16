package repositories.client;

import repositories.dtos.ClientDTO;

public class ClientRepositoryMySQL implements ClientRepository<ClientDTO>{
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(ClientDTO object) {
        return 0;
    }

    @Override
    public ClientDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(ClientDTO object) {

    }
}

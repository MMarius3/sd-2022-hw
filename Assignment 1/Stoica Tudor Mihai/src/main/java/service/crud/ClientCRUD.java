package service.crud;

import dtos.AccountDTO;
import dtos.ClientDTO;
import model.Client;
import org.modelmapper.ModelMapper;
import repository.AbstractRepository;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientCRUD implements CrudActions<Client, ClientDTO> {

    private ModelMapper clientMapper;
    private ClientRepository<ClientDTO> clientRepository;

    @Override
    public CrudActions<Client, ClientDTO> setRepository(AbstractRepository<ClientDTO> repository) {
        this.clientRepository = (ClientRepository<ClientDTO>) repository;
        return this;
    }

    @Override
    public CrudActions<Client, ClientDTO> setMapper(ModelMapper mapper) {
        this.clientMapper = mapper;
        return this;
    }

    public List<Client> getAllClients() {
        List<ClientDTO> clientDTOs = this.clientRepository.getAll();
        return clientDTOs
                .stream()
                .map(user -> clientMapper.map(user, Client.class)).toList();
    }

    @Override
    public Optional<Client> getByID(long id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<Client>> getAll() {
        return null;
    }

    @Override
    public void deleteByID(long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Client> update(ClientDTO object) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> create(ClientDTO object) {
        return Optional.empty();
    }
}

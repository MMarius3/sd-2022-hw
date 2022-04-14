package repository;

import model.Client;

import java.util.List;
import java.util.Optional;

public class ClientRepository implements GenericRepository<Client>{
    @Override
    public int add(Client object) {
        return 0;
    }

    @Override
    public boolean delete(Client object) {
        return false;
    }

    @Override
    public boolean update(Client object) {
        return false;
    }

    @Override
    public Optional<Client> getByID(int id) {
        return null;
    }

    @Override
    public List<Optional<Client>> getAll() {
        return null;
    }
}

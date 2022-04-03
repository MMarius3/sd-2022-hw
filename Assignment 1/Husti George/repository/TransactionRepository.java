package repository;

import model.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionRepository implements GenericRepository<Transaction>{

    @Override
    public int add(Transaction object) {
        return 0;
    }

    @Override
    public boolean delete(Transaction object) {
        return false;
    }

    @Override
    public boolean update(Transaction object) {
        return false;
    }

    @Override
    public Optional<Transaction> getByID(int id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<Transaction>> getAll() {
        return null;
    }
}

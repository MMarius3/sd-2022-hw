package repositories.transaction;

import repositories.dtos.TransactionDTO;

public class TransactionRepositoryMySQL implements TransactionRepository<TransactionDTO>{
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(TransactionDTO object) {
        return 0;
    }

    @Override
    public TransactionDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(TransactionDTO object) {

    }
}

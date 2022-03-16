package repositories.account;

import repositories.dtos.AccountDTO;

public class AccountRepositoryMySQL implements AccountRepository<AccountDTO>{

    @Override
    public void deleteByID() {

    }

    @Override
    public long create(AccountDTO object) {
        return 0;
    }

    @Override
    public AccountDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(AccountDTO object) {

    }
}

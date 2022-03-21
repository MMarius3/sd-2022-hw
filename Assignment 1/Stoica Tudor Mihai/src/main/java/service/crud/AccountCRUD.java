package service.crud;

import dtos.AccountDTO;
import model.Account;
import org.modelmapper.ModelMapper;
import repository.AbstractRepository;
import repository.account.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountCRUD implements CrudActions<Account, AccountDTO> {

    private ModelMapper accountMapper;
    private AccountRepository<AccountDTO> accountRepository;

    @Override
    public CrudActions<Account, AccountDTO> setRepository(AbstractRepository<AccountDTO> repository) {
        this.accountRepository = (AccountRepository<AccountDTO>) repository;
        return this;
    }

    @Override
    public CrudActions<Account, AccountDTO> setMapper(ModelMapper mapper) {
        this.accountMapper = mapper;
        return this;
    }

    @Override
    public Optional<Account> getByID(long id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<Account>> getAll() {
        return null;
    }

    @Override
    public void deleteByID(long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Account> update(AccountDTO object) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> create(AccountDTO object) {
        return Optional.empty();
    }
}

package service.client.account;

import model.Account;
import service.client.ClientService;

import java.util.List;

public class AccountServiceMySQL implements ClientService<Account, Long> {
    @Override
    public List<Account> findall() {
        return null;
    }

    @Override
    public Account findById(Long aLong) {
        return null;
    }

    @Override
    public boolean save(Account entity) {
        return false;
    }

    @Override
    public boolean update(Long id, Account newEntity) {

        return false;
    }

    @Override
    public Account view(Long aLong) {
        return null;
    }
}

package repository.account;

public abstract class AccountRepositoryDecorator implements AccountRepository {

    protected AccountRepository accountDecoratedRepository;

    public AccountRepositoryDecorator(AccountRepository accountRepository) {
        this.accountDecoratedRepository = accountRepository;
    }

}
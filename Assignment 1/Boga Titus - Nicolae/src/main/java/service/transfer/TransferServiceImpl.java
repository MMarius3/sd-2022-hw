package service.transfer;

import model.Account;
import repository.account.AccountRepository;

public class TransferServiceImpl implements TransferService{

    private Account from;
    private Account to;
    private Long amount;
    private final AccountRepository repository;

    @Override
    public Long getAmount() {
        return amount;
    }
    @Override
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public AccountRepository getRepository() {
        return repository;
    }

    public TransferServiceImpl(AccountRepository repository)
    {
        this.repository= repository;
    }
    @Override
    public Account getFrom() {
        return from;
    }
    @Override
    public void setFrom(Account from) {
        this.from = from;
    }
    @Override
    public Account getTo() {
        return to;
    }
    @Override
    public void setTo(Account to) {
        this.to = to;
    }

    @Override
    public boolean transfer() {
        from.setAmount((int) (from.getAmount()-amount));
        to.setAmount((int)(to.getAmount()+amount));
        repository.update(from);
        repository.update(to);
        return true;
    }
}

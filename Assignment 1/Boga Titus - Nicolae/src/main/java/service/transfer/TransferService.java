package service.transfer;


import model.Account;

public interface TransferService {

    public boolean transfer();

    public Account getFrom();

    public void setFrom(Account from);

    public Account getTo();

    public void setTo(Account to);

    public Long getAmount();
    public void setAmount(Long amount);

}

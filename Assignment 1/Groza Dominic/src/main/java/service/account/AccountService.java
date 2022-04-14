package service.account;

public interface AccountService {

    boolean register(Long client_id, Long account_balance, String account_type);
}
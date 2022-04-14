package repository.client_account;

public interface ClientAccountRepository {

    boolean addClientAccount(Long clientId, Long accountId);
}
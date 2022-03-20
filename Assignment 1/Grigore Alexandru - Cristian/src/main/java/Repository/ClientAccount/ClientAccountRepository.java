package Repository.ClientAccount;

import Model.ClientAccount;

import java.util.List;
import java.util.Optional;

public interface ClientAccountRepository {
    List<ClientAccount> findAll();

    Optional<ClientAccount> findByIdentificationNumber(Long id);

    boolean save(ClientAccount clientAccount);

    void removeAll();
}

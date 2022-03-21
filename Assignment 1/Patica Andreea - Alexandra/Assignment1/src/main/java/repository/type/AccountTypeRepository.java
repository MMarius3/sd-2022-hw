package repository.type;

import model.AccountType;

public interface AccountTypeRepository {

    AccountType findTypeById(Long id);

    void addType(String type);
}

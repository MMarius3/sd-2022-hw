package service.process_utilities_bills;

import dtos.AccountDTO;
import model.Account;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepository;

public interface UtilitiesBillsProcessor {
    void process(Account account, float sum);
    UtilitiesBillsProcessor setAccountRepository(AccountRepository<AccountDTO> accountRepository);
    UtilitiesBillsProcessor setAccountMapper(ModelMapper modelMapper);
}

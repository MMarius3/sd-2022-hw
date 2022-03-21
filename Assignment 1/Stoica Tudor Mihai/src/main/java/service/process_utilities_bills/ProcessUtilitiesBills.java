package service.process_utilities_bills;

import dtos.AccountDTO;
import model.Account;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepository;

public class ProcessUtilitiesBills implements UtilitiesBillsProcessor {

    AccountRepository<AccountDTO> accountRepository;
    ModelMapper modelMapper;

    @Override
    public void process(Account account, float sum) {
        account.setSum(account.getSum() - sum);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountRepository.update(accountDTO);
    }

    @Override
    public UtilitiesBillsProcessor setAccountRepository(AccountRepository<AccountDTO> accountRepository) {
        this.accountRepository = accountRepository;
        return this;
    }

    @Override
    public UtilitiesBillsProcessor setAccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        return this;
    }
}

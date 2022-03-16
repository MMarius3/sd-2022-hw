package services.TransferMoneyBetweenAccounts;

import models.Account;
import org.modelmapper.ModelMapper;
import repositories.account.AccountRepository;
import repositories.dtos.AccountDTO;
import services.TransferMoneyBetweenAccounts.exceptions.AccountRepositoryNotInjectedException;
import services.TransferMoneyBetweenAccounts.exceptions.MapperNotInjectedException;

public class TransferMoneyBetweenAccounts {

    private AccountRepository<AccountDTO> accountDTOAccountRepository;
    private ModelMapper accountMapper;

    public void transfer(Account senderAccount, Account receiverAccount, float sum) throws AccountRepositoryNotInjectedException, MapperNotInjectedException {

        if(accountDTOAccountRepository == null) {
            throw new AccountRepositoryNotInjectedException();
        }

        if(accountMapper == null) {
            throw new MapperNotInjectedException();
        }

        senderAccount.setSum(senderAccount.getSum() - sum);
        receiverAccount.setSum(receiverAccount.getSum() + sum);

        AccountDTO senderAccountDTO = accountMapper.map(senderAccount, AccountDTO.class);
        AccountDTO receiverAccountDTO = accountMapper.map(receiverAccount, AccountDTO.class);
        accountDTOAccountRepository.update(senderAccountDTO);
        accountDTOAccountRepository.update(receiverAccountDTO);
    }

    public TransferMoneyBetweenAccounts setAccountRepository(AccountRepository<AccountDTO> accountDTOAccountRepository) {
        this.accountDTOAccountRepository = accountDTOAccountRepository;
        return this;
    }

    public TransferMoneyBetweenAccounts setAccountMapper(ModelMapper accountMapper) {
        this.accountMapper = accountMapper;
        return this;
    }
}

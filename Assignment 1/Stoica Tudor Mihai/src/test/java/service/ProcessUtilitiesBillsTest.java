package service;

import dtos.AccountDTO;
import mapper.AccountMapper;
import model.Account;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import service.crud.AccountCRUD;
import service.process_utilities_bills.ProcessUtilitiesBills;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessUtilitiesBillsTest {

    private AccountRepository<AccountDTO> accountRepository;
    private ModelMapper accountMapper;

    @Test
    public void process() {

        long accountID = 1;
        float sum = (float) 2.2;

        accountRepository = new AccountRepositoryMySQL();
        accountMapper = new AccountMapper().getMapper();

        AccountCRUD accountCRUD = new AccountCRUD();
        accountCRUD
                .setRepository(accountRepository)
                .setMapper(accountMapper);

        Optional<Account> accountPreProcessing = accountCRUD.getByID(accountID);
        float initialSum = accountPreProcessing.get().getSum();

        ProcessUtilitiesBills processUtilitiesBills = new ProcessUtilitiesBills();
        processUtilitiesBills
                .setAccountRepository(accountRepository)
                .setAccountMapper(accountMapper)
                .process(accountPreProcessing.get(), sum);

        Optional<Account> accountPostProcessing = accountCRUD.getByID(accountID);
        float postSum = accountPostProcessing.get().getSum();

        assertEquals(sum,postSum - initialSum);
    }
}

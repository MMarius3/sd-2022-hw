package service;

import dtos.ClientDTO;
import dtos.CurrencyDTO;
import mapper.AccountMapper;
import mapper.ClientMapper;
import mapper.CurrencyMapper;
import mapper.UserActionMapper;
import model.Account;
import model.Client;
import model.Currency;
import model.Transaction;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepositoryMySQL;
import repository.transaction.TransactionRepository;
import repository.transaction.TransactionRepositoryMySQL;
import service.TransferMoneyBetweenAccounts.TransferMoneyBetweenAccounts;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferMoneyTest {

    @Test
    public void transactionTest() throws Exception {

        AccountRepositoryMySQL accountRepositoryMySQL = new AccountRepositoryMySQL(true);
        TransactionRepositoryMySQL transactionRepositoryMySQL = new TransactionRepositoryMySQL(true);

        ModelMapper accountMapper = new UserActionMapper().getMapper();
        ModelMapper clientMapper = new ClientMapper().getMapper();
        ModelMapper currencyMapper = new CurrencyMapper().getMapper();

        Account senderAccount = accountMapper.map(accountRepositoryMySQL.getByID(3), Account.class);

        ClientDTO clientDTO = accountRepositoryMySQL.getByID(3).getClient();
        Client mappedClient = clientMapper.map(clientDTO, Client.class);
        senderAccount.setClient(mappedClient);

        CurrencyDTO currencyDTO = accountRepositoryMySQL.getByID(3).getCurrency();
        Currency mappedCurrency = currencyMapper.map(currencyDTO, Currency.class);
        senderAccount.setCurrency(mappedCurrency);

        Account receiverAccount = accountMapper.map(accountRepositoryMySQL.getByID(4), Account.class);
        receiverAccount.setClient(clientMapper.map(accountRepositoryMySQL.getByID(4).getClient(), Client.class));
        receiverAccount.setCurrency(currencyMapper.map(accountRepositoryMySQL.getByID(4).getCurrency(), Currency.class));

        TransferMoneyBetweenAccounts transferMoneyBetweenAccounts = new TransferMoneyBetweenAccounts();
        transferMoneyBetweenAccounts
                .setAccountMapper(accountMapper)
                .setAccountRepository(accountRepositoryMySQL)
                .setTransactionRepository(transactionRepositoryMySQL)
                .transfer(senderAccount, receiverAccount, 1);

        assertTrue(true);
    }
}

package service.TransferMoneyBetweenAccounts;

import dtos.CurrencyDTO;
import dtos.TransactionDTO;
import lombok.Setter;
import lombok.experimental.Accessors;
import model.Account;
import org.modelmapper.ModelMapper;
import repository.account.AccountRepository;
import dtos.AccountDTO;
import repository.transaction.TransactionRepository;
import service.TransferMoneyBetweenAccounts.exception.InvalidTransactionException;

import java.sql.Date;
import java.time.LocalDateTime;

@Setter
@Accessors(chain = true)
public class TransferMoneyBetweenAccounts implements TransactionMaker{

    private AccountRepository<AccountDTO> accountRepository;
    private TransactionRepository<TransactionDTO> transactionRepository;
    private ModelMapper accountMapper;

    public void transfer(Account senderAccount, Account receiverAccount, float sum) throws Exception {

        validateTransfer(senderAccount, receiverAccount, sum);

        senderAccount.setSum(senderAccount.getSum() - sum);
        receiverAccount.setSum(receiverAccount.getSum() + sum);

        AccountDTO senderAccountDTO = accountMapper.map(senderAccount, AccountDTO.class);
        AccountDTO receiverAccountDTO = accountMapper.map(receiverAccount, AccountDTO.class);
        accountRepository.update(senderAccountDTO);
        accountRepository.update(receiverAccountDTO);

        createTransaction(sum, senderAccountDTO, receiverAccountDTO);
    }

    private void createTransaction(float sum, AccountDTO senderAccountDTO, AccountDTO receiverAccountDTO) {

        TransactionDTO transactionDTO =
                new TransactionDTO()
                        .setSum(sum)
                        .setDate(new Date(System.currentTimeMillis()))
                        .setCurrency(senderAccountDTO.getCurrency())
                        .setSenderAccount(senderAccountDTO)
                        .setReceiverAccount(receiverAccountDTO);

        transactionRepository.create(transactionDTO);
    }

    private void validateTransfer(Account senderAccount, Account receiverAccount, float sum) throws InvalidTransactionException {
        if (senderAccount.getSum() - sum < 0) {
            throw new InvalidTransactionException("Transaction sum is to big");
        }

        if (senderAccount.getCurrency().getId() != receiverAccount.getCurrency().getId()) {
            throw new InvalidTransactionException("Accounts must have the same currency");
        }
    }
}

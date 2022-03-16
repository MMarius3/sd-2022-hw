package repositories.dtos;

import lombok.Getter;
import lombok.Setter;
import models.Account;
import models.Currency;

@Getter
@Setter
public class TransactionDTO {
    private long id;
    private float sum;
    private Currency currency;
    private String date;
    private Account senderAccount;
    private Account receiverAccount;
}

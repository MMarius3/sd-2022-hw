package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class TransactionDTO {
    private long id;
    private float sum;
    private CurrencyDTO currency;
    private Date date;
    private AccountDTO senderAccount;
    private AccountDTO receiverAccount;
}

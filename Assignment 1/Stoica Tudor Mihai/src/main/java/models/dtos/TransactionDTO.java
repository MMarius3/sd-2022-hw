package models.dtos;

import models.Account;
import models.Currency;

public class TransactionDTO {
    private long id;
    private float sum;
    private Currency currency;
    private String date;
    private Account senderAccount;
    private Account receiverAccount;
}

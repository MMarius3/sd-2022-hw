package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private long id;
    private float sum;
    private Currency currency;
    private String date;
    private Account senderAccount;
    private Account receiverAccount;
}

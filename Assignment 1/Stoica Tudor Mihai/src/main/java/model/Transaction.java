package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Transaction {
    private long id;
    private float sum;
    private Currency currency;
    private Date date;
    private Account senderAccount;
    private Account receiverAccount;
}

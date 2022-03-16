package repositories.dtos;

import lombok.Getter;
import lombok.Setter;
import models.Currency;
import models.Transaction;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AccountDTO {
    private long clientID;
    private long id;
    private String identificationNumber;
    private float sum;
    private Currency currency;
    private Date creationDate;
    private List<Transaction> transactionList;
}

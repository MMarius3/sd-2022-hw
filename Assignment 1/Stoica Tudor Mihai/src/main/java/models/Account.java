package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Account {
    private long clientID;
    private long id;
    private String identificationNumber;
    private float sum;
    private Currency currency;
    private Date creationDate;
    private List<Transaction> transactionList;
}

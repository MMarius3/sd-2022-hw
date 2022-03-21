package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Account {
    private long id;
    private Client client;
    private String identificationNumber;
    private float sum;
    private Currency currency;
    private Date creationDate;
    private List<Transaction> transactionList;
}

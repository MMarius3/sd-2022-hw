package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AccountDTO {
    private long id;
    private ClientDTO client;
    private String identificationNumber;
    private float sum;
    private CurrencyDTO currency;
    private Date creationDate;
    private List<TransactionDTO> transactionList;
}

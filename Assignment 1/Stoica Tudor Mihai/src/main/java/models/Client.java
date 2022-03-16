package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private long id;
    private String name;
    private String identityCardNumber;
    private String personalNumericCode;
    private String address;
}

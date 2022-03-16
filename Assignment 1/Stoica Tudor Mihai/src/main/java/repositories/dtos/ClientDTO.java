package repositories.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private long id;
    private String name;
    private String identityCardNumber;
    private String personalNumericCode;
    private String address;
}

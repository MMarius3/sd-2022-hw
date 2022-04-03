package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Client {
    private int id;
    private String name;
    private String identityCard;
    private String personalCode;
    private String address;
}

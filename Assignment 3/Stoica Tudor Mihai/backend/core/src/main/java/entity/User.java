package entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private UserType userType;
}

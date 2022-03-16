package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorUser {
    private User user;
    private long id;
}

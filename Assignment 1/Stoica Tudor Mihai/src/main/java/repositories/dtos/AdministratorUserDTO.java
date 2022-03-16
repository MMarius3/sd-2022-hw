package repositories.dtos;

import lombok.Getter;
import lombok.Setter;
import models.User;

@Getter
@Setter
public class AdministratorUserDTO {
    private User user;
    private long id;
}

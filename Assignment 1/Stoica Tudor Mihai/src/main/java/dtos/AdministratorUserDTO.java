package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AdministratorUserDTO {
    private UserDTO user;
    private long id;
}

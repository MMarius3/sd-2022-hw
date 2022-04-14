package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserActionDTO {
    private long id;
    private UserDTO user;
    private String action;
    private String actionDetails;
}

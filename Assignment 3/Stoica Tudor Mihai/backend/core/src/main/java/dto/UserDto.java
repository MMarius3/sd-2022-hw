package dto;

import com.sun.istack.NotNull;
import entity.UserType;
import lombok.Data;

@Data
public class UserDto {
    @NotNull
    public int id;

    @NotNull
//    @StringLength(minLimit = 3, maxLimit = 50)
    public String name;

    @NotNull
//    @StringLength(minLimit = 5, maxLimit = 50)
    public String email;

    @NotNull
    public String password;

    @NotNull
    public UserType userType;
}

package dto;

import com.sun.istack.NotNull;
import entity.UserType;
import lombok.Data;

@Data
public class User {
    @NotNull
    int id;

    @NotNull
//    @StringLength(minLimit = 3, maxLimit = 50)
    String name;

    @NotNull
//    @StringLength(minLimit = 5, maxLimit = 50)
    String email;

    @NotNull
    String password;

    @NotNull
    UserType userType;
}

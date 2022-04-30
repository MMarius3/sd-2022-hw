package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private Long id;

    private String username;
    private String password;
    private List<Role> roles;
}

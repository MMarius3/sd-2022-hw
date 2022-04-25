package com.lab4.demo.user.dto;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private String password;
    private Set<Role> roles;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof UserDto)) {
            return false;
        }

        // typecast o to ItemDto so that we can compare data members
        UserDto c = (UserDto) o;

        // Compare the data members and return accordingly
        return this.username.equals(((UserDto) o).getUsername())
                && this.email.equals(((UserDto) o).getEmail())
                && this.password.equals(((UserDto) o).password);
    }
}

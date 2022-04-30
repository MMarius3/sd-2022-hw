package com.user.mapper;

import com.user.dto.UserListDto;
import com.user.dto.UserMinimalDto;
import com.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDto userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDto userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "name"),
            @Mapping(target = "roles", ignore = true)
    })
    User userFromUserListDto(UserListDto userListDto);


    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDto userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
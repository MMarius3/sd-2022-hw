package mapper;

import dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    entity.User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(entity.User user);
}

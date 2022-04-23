package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserListDTO createUser(UserListDTO userListDTO) {
        return userMapper.userListDtoFromUser(userRepository.save(userMapper.userFromUserListDto(userListDTO)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserListDTO updateUser(Long id, UserListDTO userListDTO) {
        return userMapper.userListDtoFromUser(userRepository.save(userMapper.userFromUserListDto(userListDTO)));
    }

}

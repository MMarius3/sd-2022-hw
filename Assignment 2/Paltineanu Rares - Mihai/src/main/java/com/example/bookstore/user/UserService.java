package com.example.bookstore.user;

import com.example.bookstore.user.dto.UserDTO;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.mapper.UserMapper;
import com.example.bookstore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserMinimalDTO> allUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());

    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map( user-> {
                    UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDTO);
                    return userListDTO;
                })
                .collect(toList());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO create(UserDTO user) {
        return userMapper.toDto(userRepository.save(userMapper.fromDto(user)));
    }

    public UserDTO findById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found: " + id)));
    }

}

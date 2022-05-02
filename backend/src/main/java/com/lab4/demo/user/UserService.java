package com.lab4.demo.user;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.model.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

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

    public UserListDTO create(UserListDTO user) {
        userValidator.validateCreateUpdate(user);
        final List<String> errors = userValidator.getErrors();
        if (errors.isEmpty()) {
            return userMapper.userListDtoFromUser(userRepository.save(
                    userMapper.userFromUserListDto(user)
            ));
        } else {
            return null;
        }
    }

    public UserListDTO edit(Long id, UserListDTO user) {
        userValidator.validateCreateUpdate(user);
        final List<String> errors = userValidator.getErrors();
        if (errors.isEmpty()) {
            User actUser = findById(id);
            actUser.setEmail(user.getEmail());
            actUser.setUsername(user.getName());
            return userMapper.userListDtoFromUser(
                    userRepository.save(actUser)
            );
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        userValidator.validateUserId(id);
        final List<String> errors = userValidator.getErrors();
        if (errors.isEmpty()) {
            userRepository.deleteById(id);
        }
    }

}

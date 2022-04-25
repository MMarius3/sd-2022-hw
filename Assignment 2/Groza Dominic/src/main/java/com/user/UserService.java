package com.user;

import com.book.model.Book;
import com.book.model.dto.BookDto;
import com.security.AuthService;
import com.security.dto.MessageResponse;
import com.security.dto.SignupRequest;
import com.user.dto.UserListDto;
import com.user.dto.UserMinimalDto;
import com.user.mapper.UserMapper;
import com.user.model.ERole;
import com.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found" + id));
    }

    public ResponseEntity<MessageResponse> create(UserListDto user) {

        if (authService.existsByUsername(user.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        SignupRequest signUpRequest=new SignupRequest(user.getName(), user.getEmail(), user.getPassword(),null);

        authService.register(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public List<UserMinimalDto> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDto> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                    UserListDto userListDto = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDto);
                    return userListDto;
                })
                .collect(toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserListDto edit(Long id, UserListDto user) {
        User userToUpdate=findById(id);
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUsername(user.getName());
        return userMapper.userListDtoFromUser(userRepository.save(userToUpdate));
    }
}
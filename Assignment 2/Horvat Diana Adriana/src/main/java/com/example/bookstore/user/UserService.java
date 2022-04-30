package com.example.bookstore.user;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.mapper.UserMapper;
import com.example.bookstore.user.model.User;
import com.example.bookstore.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(user -> {
                    UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDTO);
                    return userListDTO;
                }).collect(toList());
    }

    public ResponseEntity<?> create(UserListDTO user){
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

        SignupRequest signupRequest = new SignupRequest(user.getName(), user.getEmail(), user.getPassword(), null);
        authService.register(signupRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO edit(Long id, UserListDTO user) {
        User actUser = findById(id);
        actUser.setUsername(user.getName());
        actUser.setEmail(user.getEmail());
        if(!Objects.equals(user.getPassword(), actUser.getPassword())){
            actUser.setPassword(encoder.encode(user.getPassword()));
        }

        return userMapper.userListDtoFromUser(
                userRepository.save(actUser)
        );
    }
}

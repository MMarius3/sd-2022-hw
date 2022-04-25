package com.assignment2.book_store.security.service;


import com.assignment2.book_store.data.entity.jpa.User;
import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import com.assignment2.book_store.data.entity.jpa.enums.UserStatus;
import com.assignment2.book_store.repository.jpa.UserRepository;
import com.assignment2.book_store.security.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.assignment2.book_store.data.entity.jpa.enums.UserStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class AuthService {

    private static final UserStatus DEFAULT_STATUS = ACTIVE;
    private static final Double DEFAULT_ACCOUNT_BALANCE = 0.0;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .username(signUpRequest.getUsername())
                .passwordHash(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .accountStatus(DEFAULT_STATUS)
                .accountBalance(DEFAULT_ACCOUNT_BALANCE)
                .build();
        user.setRole(UserRole.CUSTOMER);
        userRepository.save(user);
    }

}

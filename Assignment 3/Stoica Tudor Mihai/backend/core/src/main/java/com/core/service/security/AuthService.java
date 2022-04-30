package com.core.service.security;

import com.core.dto.security.SignupRequest;
import com.infrastructure.entity.User;
import com.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public boolean existsByUsername(String username) {
        return userRepository.existsByName(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = new User()
                .setName(signUpRequest.getUsername())
                .setPassword(encoder.encode(signUpRequest.getPassword()))
                .setEmail(signUpRequest.getEmail());

        Set<String> rolesStr = signUpRequest.getRoles();

        userRepository.save(user);
    }
}

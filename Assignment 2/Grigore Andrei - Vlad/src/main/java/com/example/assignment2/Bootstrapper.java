package com.example.assignment2;

import com.example.assignment2.bookstore.BookRepository;
import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.security.AuthService;
import com.example.assignment2.security.dto.SignupRequest;
import com.example.assignment2.user.RoleRepository;
import com.example.assignment2.user.UserRepository;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BookRepository bookRepository;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .role(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("andrei2@gmail.com")
                    .username("adolf2")
                    .password("parolaHei1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            bookRepository.save(Book.builder()
                    .author("Damn Daniel")
                    .genre("Dank Memes")
                    .quantity(10)
                    .title("True Story")
                    .build());
        }
    }
}

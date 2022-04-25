package com.lab4.demo;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
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
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            bookRepository.save(Book.builder()
                    .id(1L)
                    .title("The Lord of the Rings")
                    .author("J.R.R. Tolkien")
                    .genre("Fantasy")
                    .price(19.99)
                    .quantity(10)
                    .build());
            bookRepository.save(Book.builder()
                    .id(1L)
                    .title("The Lady of the Rings")
                    .author("J.R.R. Tolkien")
                    .genre("Fantasy")
                    .price(19.00)
                    .quantity(0)
                    .build());
        }
    }
}

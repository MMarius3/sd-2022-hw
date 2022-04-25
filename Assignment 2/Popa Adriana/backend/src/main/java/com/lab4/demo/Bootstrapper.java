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
                    .title("Lala")
                    .author("Ana")
                    .genre("Romance")
                    .quantity(25)
                    .price(25.5)
                    .build());
            bookRepository.save(Book.builder()
                    .title("Bla")
                    .author("Ana")
                    .genre("Drama")
                    .quantity(25)
                    .price(25.5)
                    .build());
            bookRepository.save(Book.builder()
                    .title("Blah")
                    .author("Ana")
                    .genre("Drama")
                    .quantity(0)
                    .price(25.5)
                    .build());
            bookRepository.save(Book.builder()
                    .title("Haha")
                    .author("Alina")
                    .genre("Drama")
                    .quantity(0)
                    .price(33.33)
                    .build());
        }
    }
}

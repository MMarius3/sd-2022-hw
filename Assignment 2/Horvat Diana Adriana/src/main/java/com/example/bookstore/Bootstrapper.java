package com.example.bookstore;

import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.RoleRepository;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.UserRepository;
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

    private final BookRepository itemRepository;

    @Value("false")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            itemRepository.deleteAll();
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

            for(int i = 0; i < 5; i++){
                Book book = Book.builder()
                        .author(String.format("Auth%d",i+1))
                        .title(String.format("Book%d",i+1))
                        .genre("Comedy")
                        .price((i + 1))
                        .quantity(i+1)
                        .build();
                itemRepository.save(book);
            }
        }
    }
}
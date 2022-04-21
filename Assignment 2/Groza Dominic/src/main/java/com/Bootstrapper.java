package com;


import com.book.BookRepository;
import com.book.model.Book;
import com.security.AuthService;
import com.security.dto.SignupRequest;
import com.user.RoleRepository;
import com.user.UserRepository;
import com.user.model.ERole;
import com.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;
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
//            itemRepository.deleteAll();
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
                    .roles(Set.of("CUSTOMER"))
                    .build());
            for(int i = 0; i < 5; i++){
                Random rand=new Random();
                Book book = Book.builder()
                        .author(String.format("Auth%d",i+1))
                        .title(String.format("Book%d",i+1))
                        .genre("Comedy")
                        .price((long) (i + 1) * rand.nextInt(1000))
                        .build();
                bookRepository.save(book);
            }
        }
    }
}
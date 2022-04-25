package com.example.demo;

import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
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

    private final ItemRepository itemRepository;

    @Value("${app.bootstrap}")
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
                Item item = Item.builder()
                .author(String.format("Auth%d",i))
                .title(String.format("Book%d",i))
                .genre("Comedy")
                .price((double) (i))
                .quantity(i)
                .build();
                itemRepository.save(item);
            }
        }
    }
}

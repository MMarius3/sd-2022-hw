package com.lab4.demo;

import com.lab4.demo.item.GenreRepository;
import com.lab4.demo.item.model.EGenre;
import com.lab4.demo.item.model.Genre;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final GenreRepository genreRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            genreRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            for (EGenre value : EGenre.values()) {
                genreRepository.save(
                        Genre.builder()
                                .name(value)
                                .build()
                );
            }
        }
    }
}

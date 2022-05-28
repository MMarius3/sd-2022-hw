package com;

import com.coin.CoinMapper;
import com.coin.CoinRepository;
import com.coin.CoinService;
import com.coin.UpdateCoinsService;
import com.security.AuthService;
import com.security.dto.SignupRequest;
import com.user.RoleRepository;
import com.user.UserRepository;
import com.user.model.ERole;
import com.user.model.Role;
import com.ws.WebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.Timer;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final CoinRepository coinRepository;

    private final CoinMapper coinMapper;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            coinRepository.deleteAll();
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
                    .password("alex2000")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("marius.macean@gmail.com")
                    .username("marius")
                    .password("mane2000")
                    .roles(Set.of("ADMIN"))
                    .build());
            Timer t = new Timer();
            UpdateCoinsService updateCoinsService = new UpdateCoinsService(coinRepository, coinMapper);

            t.scheduleAtFixedRate(updateCoinsService, 0, 3000);
        }
    }
}

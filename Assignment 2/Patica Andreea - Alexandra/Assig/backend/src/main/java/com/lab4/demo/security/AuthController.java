package com.lab4.demo.security;

import com.lab4.demo.security.dto.JwtResponse;
import com.lab4.demo.security.dto.LoginRequest;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDetailsImpl;
import com.lab4.demo.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    @Autowired
    private final UserValidator userValidator;

    @PostMapping(SIGN_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("aaaaaaaaaaaaa");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok( //ok = status 200
                JwtResponse.builder()
                        .token(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build()
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        String validationMessage = userValidator.validate(signUpRequest.getUsername(),signUpRequest.getEmail(),signUpRequest.getPassword());
        if (validationMessage.equals("OK")) {

            authService.register(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

        }else{
            System.out.println(validationMessage);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(validationMessage));
        }
    }

}

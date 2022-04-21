package com.lab4.demo.user.validator;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserValidator {
    //private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(employee|admin)\\.com$";
    private static final String ADMIN_EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(admin)\\.com$";
    private static final String EMPLOYEE_EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(employee)\\.com$";
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors = new ArrayList<>();
    private final AuthService authService;


    public String validate(String username, String email, String password) {
        errors.clear();
        if (validateUsernameUniqueness(username)){
            return "Error: Username is already taken!";
        }
        if (validateEmailUniqueness(email)){
            return "Error: Email is already in use!";
        }
        if (validateEmail(email)){
            return "Error: Email is not valid!";
        }
        if (validatePasswordLength(password)){
            return "Error: Password must be at least 8 characters long";
        }
        if (validatePasswordSpecial(password)){
            return "Error: Password must contain at least one special character";
        }
        if (validatePasswordDigit(password)){
            return "Error: Password must contain at least one digit";
        }

        return "OK";
    }

    private boolean validateUsernameUniqueness(String username){
        return authService.existsByUsername(username);
    }

    private boolean validateEmailUniqueness(String email) {
        return authService.existsByEmail(email);
    }

    public boolean validateEmail(@NotNull String email) {
        return !email.matches(EMAIL_VALIDATION_REGEX);
    }

    private boolean validatePasswordLength(@NotNull String password) {
        return !(password.length() >= MIN_PASSWORD_LENGTH);
    }

    private boolean validatePasswordSpecial(@NotNull String password) {
        return !password.matches(".*[!@#$%^&*()_+].*");
    }

    private boolean validatePasswordDigit(@NotNull String password) {
        return !password.matches(".*[0-9].*");
    }

}

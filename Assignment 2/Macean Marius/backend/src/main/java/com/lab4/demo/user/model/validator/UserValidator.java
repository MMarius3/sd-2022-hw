package com.lab4.demo.user.model.validator;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public void validateCreateUpdate(UserListDTO user) {
        String email = user.getEmail();
        String username = user.getName();
        validateEmail(email);
        validateName(username);
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is not valid");
        }
    }

    private void validateName(String username) {
        if (!username.matches("[A-Za-z ]+")) {
            errors.add("Username must only contain letters");
        }
    }

    public void validateUserId(Long id) {
        validateIDExistence(id);
    }

    public void validateIDExistence(Long id) {
        final Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            errors.add("There is no user having this ID");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}

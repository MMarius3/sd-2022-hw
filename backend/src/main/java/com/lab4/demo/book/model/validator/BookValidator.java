package com.lab4.demo.book.model.validator;

import ch.qos.logback.core.net.server.Client;
import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookValidator {

    private final List<String> errors = new ArrayList<>();
    private final BookRepository bookRepository;

    public void validateCreateUpdate(BookDTO book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String genre = book.getGenre();
        Integer price = book.getPrice();
        Integer quantity = book.getQuantity();
        validateString(title);
        validateString(author);
        validateString(genre);
        validateInt(price);
        validateInt(quantity);
    }

    public void validateBookId(Long id) {
        validateIDExistence(id);
    }

    public void validateSell(Book book) {
        if (book.getQuantity() <= 0) {
            errors.add("Book out of order");
        }
    }

    public void validateIDExistence(Long id) {
        final Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            errors.add("There is no book having this ID");
        }
    }

    private void validateString(String inputString) {
        validateStringOnlyLetters(inputString);
    }

    private void validateInt(Integer inputInt) {
        validateInputValue(inputInt);
    }

    private void validateInputValue(Integer inputInt) {
        if (inputInt < 0 || inputInt > 200) {
            errors.add("Price and quantity must be non-negative and less than 200.");
        }
    }

    private void validateStringOnlyLetters(String inputString) {
        if (!inputString.matches("[A-Za-z ]+")) {
            errors.add("Title, author and genre must only contain letters");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}

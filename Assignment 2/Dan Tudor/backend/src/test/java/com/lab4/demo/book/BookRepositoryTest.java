package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.item.model.Item;
import org.hibernate.type.TrueFalseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    @AfterEach
    public void beforeAll() {
        bookRepository.deleteAll();
    }

    @Test
    public void findByTitle(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByTitle(savedBook.getTitle());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getTitle().equals("Title"));
    }

    @Test
    public void findByAuthor(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByAuthor(savedBook.getAuthor());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getAuthor().equals("Author"));
    }

    @Test
    public void findByGenre(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByGenre(savedBook.getGenre());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getGenre().equals("Adventure"));
    }

    @Test
    public void search(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        books.add(Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build());
        bookRepository.saveAll(books);
        String searchString = "%thor";
        List<Book> foundBooks = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(searchString, searchString, searchString);
        //assertEquals(books.size(), all.size());
        assertEquals(false, foundBooks.isEmpty());
    }

//    @Test
//    void testPaginationQuery() {
//        List<Book> books = new ArrayList<>();
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    String author = String.valueOf((char) a3) +
//                            (char) a2 +
//                            (char) a1;
//                    String genre = String.valueOf((char) a2) +
//                            (char) a3 +
//                            (char) a1;
//
//                    bookRepository.save(Book.builder()
//                            //.id((long)a1*100+ a2*10 + a3)
//                            .title(title)
//                            .author(author)
//                            .genre(genre)
//                            .price((int)a3)
//                            .quantity((int)a2)
//                            .build());
//                    books.add(Book.builder()
//                            //.id((long)a1*100+ a2*10 + a3)
//                            .title(title)
//                            .author(author)
//                            .genre(genre)
//                            .price((int)a3)
//                            .quantity((int)a2)
//                            .build());
//                }
//            }
//        }
//
//        final int page = 1;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//        final Page<Book> pagedResult = bookRepository.findAllByTitleOrAuthorOrGenreLike("%b%","%b%","%b%", pageable);
//
//        assertTrue(pagedResult.hasContent());
//        assertEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
////    assertEquals(1951, pagedResult.getTotalElements());
//
//        // what if now we'd also want to add sorting?
//
//        final int sortedPage = 4;
//        final int sortedPageSize = 100;
//        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "title"));
//        final Page<Book> pagedResultSortAsc = bookRepository.findAllByTitleOrAuthorOrGenreLike("%b%", "%b%", "%b%", first100AscByName);
//        assertTrue(pagedResultSortAsc.hasContent());
//        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
//        assertEquals(sortedPage, pagedResultSortAsc.getNumber());
//
//        final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
//        assertEquals(sortedPageSize, pagedResultSortedContent.size());
//
//        final Book firstItemAsc = pagedResultSortedContent.get(0);
//        pagedResultSortedContent.remove(0);
//
//        assertTrue(
//                pagedResultSortedContent.stream().anyMatch(item ->
//                        firstItemAsc.getTitle().compareTo(item.getTitle()) < 0
//                )
//        );
//    }
}

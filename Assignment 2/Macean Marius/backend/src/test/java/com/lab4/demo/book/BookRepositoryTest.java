package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.review.ReviewRepository;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        reviewRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder().title("whatever").author("also whatever").build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void testSimpleLikeQuery() {
        final Book book1 = Book.builder()
                .title("Ion")
                .author("Liviu Rebreanu")
                .genre("roman")
                .price(40)
                .quantity(100)
                .build();

        repository.save(book1);

        final List<Book> res1 = repository.findAllByTitleLikeOrAuthorLike("Ion",
                "Liviu Rebreanu");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(book1.getId(), res1.get(0).getId());

        final List<Book> res2 = repository.findAllByTitleLikeOrAuthorLike("%tew%",
                "Liviu Rebreanu");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(book1.getId(), res2.get(0).getId());
    }

    @Test
    void testSortingLikeQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Book.builder()
                            .title(title)
                            .author("RandomAuthor")
                            .build());
                }
            }
        }

        final List<Book> bBooksSortedDesc = repository.findAllByTitleLikeOrderByTitleDesc("%b%");
        final Book firstBook = bBooksSortedDesc.get(0);
        bBooksSortedDesc.remove(0);

        assertTrue(
                bBooksSortedDesc.stream().anyMatch(book ->
                        firstBook.getTitle().compareTo(book.getTitle()) > 0
                )
        );
    }

    @Test
    void testPaginationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Book.builder()
                            .title(title)
                            .author("RandomAuthor")
                            .build());
                }
            }
        }

        final int page = 1;
        final int pageSize = 10;
        final PageRequest pageable = PageRequest.of(page, pageSize);
        final Page<Book> pagedResult = repository.findAllByTitleLike("%b%", pageable);

        assertTrue(pagedResult.hasContent());
        assertEquals(pageSize, pagedResult.getNumberOfElements());
        assertEquals(page, pagedResult.getNumber());
    }

}

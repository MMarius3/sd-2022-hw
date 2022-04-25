package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
public class BookRepositoryTest {

    @Mock
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder()
                .name("whatever")
                .author("author")
                .genre("aaa")
                .price(10f)
                .quantity(15L)
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> Books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(Books);
        List<Book> all = repository.findAll();
        assertEquals(Books.size(), all.size());
    }

    @Test
    public void testSimpleLikeQuery() {
        final Book book = Book.builder()
                .id(1L)
                .name("Stewie")
                .description("Something, something, something ... dark side.")
                .author("author")
                .genre("aaa")
                .price(10f)
                .quantity(15L)
                .build();

        final List<Book> res1 = repository.findAllByNameLikeOrDescriptionLike("Stewie",
                "noooope");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(book.getId(), res1.get(0).getId());

        final List<Book> res2 = repository.findAllByNameLikeOrDescriptionLike("%tew%",
                "noooope");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(book.getId(), res2.get(0).getId());
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
                            .name(title)
                            .build());
                }
            }
        }

        final int page = 1;
        final int pageSize = 10;
        final PageRequest pageable = PageRequest.of(page, pageSize);
        final Page<Book> pagedResult = repository.findAllByNameLike("%b%", pageable);

        assertTrue(pagedResult.hasContent());
        assertEquals(pageSize, pagedResult.getNumberOfElements());
        assertEquals(page, pagedResult.getNumber());

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "name"));
        final Page<Book> pagedResultSortAsc = repository.findAllByNameLike("%b%", first100AscByName);
        assertTrue(pagedResultSortAsc.hasContent());
        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
        assertEquals(sortedPage, pagedResultSortAsc.getNumber());

        final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
        assertEquals(sortedPageSize, pagedResultSortedContent.size());

        final Book firstBookAsc = pagedResultSortedContent.get(0);
        pagedResultSortedContent.remove(0);

        assertTrue(
                pagedResultSortedContent.stream().anyMatch(Book ->
                        firstBookAsc.getName().compareTo(Book.getName()) < 0
                )
        );
    }
}

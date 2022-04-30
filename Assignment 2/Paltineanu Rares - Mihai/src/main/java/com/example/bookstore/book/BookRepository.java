package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Page<Book> findAllByAuthorLikeOrNameLikeOrGenreLike(String author, String name, String genre, Pageable pageable);

    List<Book> findAllByNameLikeOrDescriptionLike(String author, String genre);

    Page<Book> findAllByNameLike(String name, Pageable pageable);
}

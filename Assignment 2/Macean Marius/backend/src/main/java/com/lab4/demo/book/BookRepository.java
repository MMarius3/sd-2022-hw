package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findAllByTitleLikeOrAuthorLike(String title, String author);

    List<Book> findAllByTitleLikeOrderByTitleDesc(String title);

    // or, more dynamically...
    //List<Book> findAllByTitleLike(String title, Sort sorting);

    Page<Book> findAllByTitleLike(String title, Pageable pageable);

    Page<Book> findAllByAuthorLike(String author, Pageable pageable);

    // what if we had 5+ fields to search on...?
    // problem with the fixed set of criterias
    // wouldn't it be cool to have a set of atomic predicates to combine at will?
}

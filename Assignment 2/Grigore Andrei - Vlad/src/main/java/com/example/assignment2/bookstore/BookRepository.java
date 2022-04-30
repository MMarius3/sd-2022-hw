package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByTitle(String title);

    @Query (nativeQuery = true, value = "Select i from Book i where i.quantity > ?2 and  i.title = ?1")
    Optional<Book> findByTitleAndQuantity(String title, int quantity);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE Book i SET i.title = ?2 WHERE i.id = ?1")
    void updateTitle(@Param("id")Long id, @Param("title") String title);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE Book i SET i.genre = ?2 WHERE i.id = ?1")
    void updateGenre(@Param("id")Long id, @Param("genre")String genre);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE Book i SET i.author = ?2 WHERE i.id = ?1")
    void updateAuthor(@Param("id")Long id, @Param("id")String author);

    @Modifying
    @Transactional
    @Query (nativeQuery = true, value = "UPDATE Book SET i.quantity = ?2 WHERE i.id = ?1")
    void updateQuantity(@Param("id")Long id, @Param("quantity")int quantity);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Book SET i.quantity = (i.quantity - ?2) WHERE i.id = ?1")
    void sellBook(@Param("id")Long id, @Param("quantity")int quantity);

}

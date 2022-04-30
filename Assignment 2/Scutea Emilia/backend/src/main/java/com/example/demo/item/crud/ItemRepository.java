package com.example.demo.item.crud;


import com.example.demo.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemsByQuantityEquals(Integer quantity);

    @Transactional
    @Modifying
    @Query (value =  "UPDATE bookstore.item SET bookstore.item.quantity = ?2 where bookstore.item .id = ?1", nativeQuery = true)
    void sellBook(Long id, Integer quantity);

    List<Item> findAllByTitleLikeOrAuthorLikeOrGenreLike(String str1, String str2, String str3);

}

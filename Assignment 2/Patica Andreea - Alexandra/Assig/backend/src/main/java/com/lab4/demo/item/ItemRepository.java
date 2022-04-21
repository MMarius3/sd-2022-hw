package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;


public interface ItemRepository extends JpaRepository<Item, Long> {

//    Optional<Item> findByName(String name);
//
//    Optional<Item> findByNameLike(String name);
//
//    Stream<Item> findAllByNameAndAndDescription(String name, String description);
//
//    @Query(nativeQuery = true, value = "select * from item where name = ?1 and description = ?2")
//    Optional<Item> findByWhatever(String whatever, String x);

    //Stream<Item> findAllByEnabledIsTrue();
}

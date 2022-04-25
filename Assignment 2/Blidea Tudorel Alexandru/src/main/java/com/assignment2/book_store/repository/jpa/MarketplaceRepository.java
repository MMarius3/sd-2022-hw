package com.assignment2.book_store.repository.jpa;

import com.assignment2.book_store.data.entity.jpa.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
}

package com.coin;

import com.coin.model.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long>, JpaSpecificationExecutor<Coin> {
    //List<Coin> findAllByNameLikeOrDescriptionLike(String name, String description);

    //List<Coin> findAllByNameLikeOrderByNameDesc(String name);

    // or, more dynamically...
    //List<Coin> findAllByNameLike(String name, Sort sorting);

    //Page<Coin> findAllByNameLike(String name, Pageable pageable);

    //Page<Coin> findAllByDescriptionLike(String description, Pageable pageable);

    // what if we had 5+ fields to search on...?
    // problem with the fixed set of criterias
    // wouldn't it be cool to have a set of atomic predicates to combine at will?
}

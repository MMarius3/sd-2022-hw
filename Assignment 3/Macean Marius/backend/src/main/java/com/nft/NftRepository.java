package com.nft;

import com.nft.model.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NftRepository extends JpaRepository<Nft, Long> {
    Set<Nft> findAllByCoin_Id(Long coinId);
}

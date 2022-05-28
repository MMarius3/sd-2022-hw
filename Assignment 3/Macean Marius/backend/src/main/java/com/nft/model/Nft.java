package com.nft.model;

import com.coin.model.Coin;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nft {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String image;
    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;
}
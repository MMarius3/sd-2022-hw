package com.coin.model;

import com.trade.model.Trade;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String pair;

    @Column
    private float price;

    @Column
    private int top;

    @OneToMany(mappedBy = "coin")
    @Builder.Default
    private Set<Trade> trades = new HashSet<>();
}

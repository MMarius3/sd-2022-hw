package com.trade.model;

import com.coin.model.Coin;
import com.trade.PaymentMethod;
import com.user.model.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private PaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

}

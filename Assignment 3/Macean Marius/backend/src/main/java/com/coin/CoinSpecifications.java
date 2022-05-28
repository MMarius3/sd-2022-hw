package com.coin;

import com.coin.model.Coin;
import com.coin.model.dto.CoinFilterRequestDto;
import com.trade.PaymentMethod;
import com.trade.model.Trade;
import com.user.model.ERole;
import com.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import static java.util.Optional.ofNullable;

public class CoinSpecifications {

    public static Specification<Coin> similarNames(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), name);
    }

    public static Specification<Coin> onlyWalletExchangePaymentMethod() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.exists(paymentMethodTradeSubquery(root,
                query, criteriaBuilder));
    }

    public static Specification<Coin> withAdministratorTrades() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            final Root<Trade> trade = query.from(Trade.class);
            final Root<User> user = query.from(User.class);
            final Join<Object, Object> role = user.join("roles", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(trade.get("user"), user),
                    criteriaBuilder.equal(trade.get("coin"), root),
                    criteriaBuilder.equal(role.get("name"), ERole.ADMIN)
            );
        };
    }

    private static Subquery<Trade> paymentMethodTradeSubquery(Root<Coin> root, CriteriaQuery<?> query,
                                                        CriteriaBuilder criteriaBuilder) {
        final Subquery<Trade> tradeSubquery = query.subquery(Trade.class);
        final Root<Trade> fromTrade = tradeSubquery.from(Trade.class);
        return tradeSubquery.select(fromTrade).where(criteriaBuilder.and(
                criteriaBuilder.equal(fromTrade.get("coin"), root),
                criteriaBuilder.equal(fromTrade.get("paymentMethod"), PaymentMethod.EXCHANGE_WALLET)
                )
        );
    }

    public static Specification<Coin> specificationsFromFilter(CoinFilterRequestDto filter) {
        final Specification<Coin> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getName()).ifPresent(s -> spec.and(similarNames(s)));
        if (filter.getOnlyWalletExchange()) spec.and(onlyWalletExchangePaymentMethod());
        if (filter.getWithAdminTrade()) spec.and(withAdministratorTrades());
        return spec;
    }
}

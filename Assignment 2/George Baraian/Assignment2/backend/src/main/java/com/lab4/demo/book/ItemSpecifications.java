package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.review.model.Review;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

import static com.lab4.demo.review.Rating.EXCELENT;

public class ItemSpecifications {

    public static Specification<Book> similarNames(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), name);
    }

    public static Specification<Book> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThan(root.get("dateCreated"), date);
    }

    public static Specification<Book> onlyExcellentRated() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.exists(reviewRatingSubquery(root,
                query, criteriaBuilder));
    }

    public static Specification<Book> withAdministratorReviews() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            final Root<Review> review = query.from(Review.class);
            final Root<User> user = query.from(User.class);
            final Join<Object, Object> role = user.join("roles", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(review.get("user"), user),
                    criteriaBuilder.equal(review.get("item"), root),
                    criteriaBuilder.equal(role.get("name"), ERole.ADMIN)
            );
        };
    }

    private static Subquery<Review> reviewRatingSubquery(Root<Book> root, CriteriaQuery<?> query,
                                                         CriteriaBuilder criteriaBuilder) {
        final Subquery<Review> reviewSubquery = query.subquery(Review.class);
        final Root<Review> fromReview = reviewSubquery.from(Review.class);
        return reviewSubquery.select(fromReview).where(criteriaBuilder.and(
                criteriaBuilder.equal(fromReview.get("item"), root),
                criteriaBuilder.equal(fromReview.get("rating"), EXCELENT)
                )
        );
    }
}

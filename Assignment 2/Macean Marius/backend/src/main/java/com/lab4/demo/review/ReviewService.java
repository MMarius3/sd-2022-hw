package com.lab4.demo.review;

import com.lab4.demo.review.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public Set<ReviewDTO> getReviewsForBook(Long bookId) {
        return reviewRepository.findAllByBook_Id(bookId).stream()
                .map(reviewMapper::toDto)
                .collect(toSet());
    }
}

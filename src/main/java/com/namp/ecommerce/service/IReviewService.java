package com.namp.ecommerce.service;

import com.namp.ecommerce.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    List<ReviewDTO> getReviews();
    ReviewDTO getReviewById(long id);
    List<ReviewDTO> findByUserId(long id);
    void delete(ReviewDTO review);
    ReviewDTO save(ReviewDTO review);
}

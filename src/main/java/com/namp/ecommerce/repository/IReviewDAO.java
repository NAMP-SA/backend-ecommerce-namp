package com.namp.ecommerce.repository;

import com.namp.ecommerce.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReviewDAO extends CrudRepository<Review,Long> {
    List<Review> findAll();
    Review findByIdReview(long id);
    List<Review> findByUserId (long UserId);
}

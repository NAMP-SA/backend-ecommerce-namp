package com.namp.ecommerce.repository;

import com.namp.ecommerce.model.Review;
import com.namp.ecommerce.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReviewDAO extends CrudRepository<Review,Long> {
    List<Review> findAll();
    Review findByIdReview(long id);

    @Query("SELECT r FROM Review r WHERE r.idUser = :userId")
    List<Review> findReviewsByUserId(@Param("userId") Long userId);
}

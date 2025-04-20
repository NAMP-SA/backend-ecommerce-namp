package com.namp.ecommerce.service.implementation;

import com.namp.ecommerce.dto.ReviewDTO;
import com.namp.ecommerce.mapper.MapperReview;
import com.namp.ecommerce.model.Review;
import com.namp.ecommerce.model.User;
import com.namp.ecommerce.repository.IReviewDAO;
import com.namp.ecommerce.service.IReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewImplementation implements IReviewService {

    @Autowired
    private IReviewDAO reviewDAO;

    @Autowired
    private MapperReview mapperReview;

    @Override
    public List<ReviewDTO> getReviews(){
        return reviewDAO.findAll()
                .stream()
                .map(mapperReview::convertReviewToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(long id){
        return mapperReview.convertReviewToDto(reviewDAO.findByIdReview(id));
    }

    @Override
    public List<ReviewDTO> findByUserId(long id){
        return reviewDAO.findReviewsByUserId(id)
                .stream()
                .map(mapperReview::convertReviewToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete (ReviewDTO reviewDTO) {
        Review review = reviewDAO.findByIdReview(reviewDTO.getIdReview());
        if (review == null) {
            throw new EntityNotFoundException("Review not found with ID: " + reviewDTO.getIdReview());
        }
        reviewDAO.delete(review);
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        Review review = mapperReview.convertDtoToReview(reviewDTO);
        Review savedReview = reviewDAO.save(review);

        return mapperReview.convertReviewToDto(savedReview);
    }




}

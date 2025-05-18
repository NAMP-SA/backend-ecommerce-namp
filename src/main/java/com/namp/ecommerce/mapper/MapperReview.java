package com.namp.ecommerce.mapper;


import com.namp.ecommerce.dto.ReviewDTO;
import com.namp.ecommerce.model.Review;
import com.namp.ecommerce.repository.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperReview {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private MapperUtil mapperUtil;

    //Metodo para mappear de ReviewDTO a Review

    public Review convertDtoToReview(ReviewDTO reviewDTO) {
        Review review = new Review();

        review.setMessage(reviewDTO.getMessage());
        review.setSubject(reviewDTO.getSubject());
        review.setIdUser(userDAO.findByIdUser(reviewDTO.getIdUser().getIdUser()));

        return review;
    }
    /*
    ----------------------------------------------------------------------------------------------------------
                                           MAPPER UTIL CALLS
   -----------------------------------------------------------------------------------------------------------
     */

    public ReviewDTO convertReviewToDto(Review review){
        ReviewDTO reviewDTO = mapperUtil.convertReviewToDto(review);
        return reviewDTO;
    }
}

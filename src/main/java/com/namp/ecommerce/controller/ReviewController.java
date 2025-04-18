package com.namp.ecommerce.controller;


import com.namp.ecommerce.dto.ReviewDTO;
import com.namp.ecommerce.service.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-namp")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @GetMapping("review")
    public ResponseEntity<?> getReviews(){
        try{
            return ResponseEntity.ok(reviewService.getReviews());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error showing the reviews:"+e.getMessage());
        }
    }

    @PostMapping("review")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        try{
            ReviewDTO createdReviewDTO = reviewService.save(reviewDTO);

            if (createdReviewDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error creating review");
            }

            return ResponseEntity.ok(createdReviewDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the review:"+e.getMessage());
        }
    }

    @DeleteMapping("/admin/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable long id) {
        try{
            ReviewDTO reviewDTO = reviewService.getReviewById(id);

            if (reviewDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The review does not exist");
            }

            reviewService.delete(reviewDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the review:" + e.getMessage());
        }
    }


}

package com.cognixia.jump.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.service.ReviewService;
import com.cognixia.jump.util.JwtUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	JwtUtil jwtUtil;
	
	// get all reviews on the application
	@GetMapping("/reviews")
	public List<Review> getAllReviews() {
		return reviewRepo.findAll();
	}
	
	// get reviews for a specific user
	@GetMapping("/review/user")
	public ResponseEntity<?> getReviewsByUser(HttpServletRequest req) {
		List<Review> reviews = reviewService.findByUserId(req);
		return ResponseEntity.ok(reviews);
	}
	
	// add a new review for a restaurant
	@PostMapping("/{id}/add/review")
	public ResponseEntity<?> addReview(@PathVariable long restId, @RequestBody Review review, HttpServletRequest req) {
		Review added = reviewService.createNewReview(review, req);
		Restaurant rest = restaurantRepo.getById(restId);
		added.setRestaurant(rest);
		reviewRepo.save(added);
		return ResponseEntity.status(201).body(added);
	}
	
	// delete a specific review
	@DeleteMapping("/delete/review/{id}")
	public ResponseEntity<?> removeReview(@PathVariable long id) throws ResourceNotFoundException {
		Review deleted = reviewService.deleteReview(id);
		return new ResponseEntity<>(deleted, HttpStatus.OK);
	}
	

}

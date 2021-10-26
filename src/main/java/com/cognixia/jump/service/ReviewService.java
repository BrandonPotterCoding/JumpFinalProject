package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.RatingOutOfBoundsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired 
	UserRepository userRepo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public Review createNewReview(Review review, HttpServletRequest req, long restId) throws RatingOutOfBoundsException{
		// unsure about where to find restaurant information, could it be a part of the header?
		String jwt = req.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(jwt);
		
		Restaurant rest = restaurantRepo.getById(restId);
		review.setRestaurant(rest);
		
		User user = userRepo.findByUsername(username).get();
		review.setId(-1L);
		review.setUser(user);
		
		if( (review.getRating() >= 1) && (review.getRating() <= 5) )
			return reviewRepo.save(review);
		
		else 
			throw new RatingOutOfBoundsException();
		
	}
	
	public List<Review> findByUserId(HttpServletRequest req) {
		String jwt = req.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(jwt);
		
		User user = userRepo.findByUsername(username).get();
		
		return user.getReviews();
	}
	
	public Review deleteReview(long id) throws ResourceNotFoundException {
		Optional<Review> toDelete = reviewRepo.findById(id);
		if (toDelete.isEmpty()) {
			throw new ResourceNotFoundException("Review", id);
		}
		reviewRepo.deleteReview(id);
		return toDelete.get();
		
	}

}

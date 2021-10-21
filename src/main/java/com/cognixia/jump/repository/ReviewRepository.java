package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	Optional<Review> findById(long id);
	
	@Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurant_id")
	List<Review> reviewsForRestuarant(@Param(value="restaurant_id") Long id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Review r WHERE r.id = :id")
	public void deleteReview(@Param(value="id") Long id);

	@Query("SELECT r FROM Review r WHERE r.restaurant.id= ?1 ORDER BY r.rating DESC")
	List<Review> findReviewsDescending(Long restId);
}

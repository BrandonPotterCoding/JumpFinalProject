package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

	@Query("SELECT r FROM Restaurant r WHERE r.name = ?1")
	List<Restaurant> findRestaurantsByName(String name);
	
//	Restaurant findRestaurantByName(String name);

	@Query("SELECT r FROM Restaurant r WHERE r.name LIKE ?1%")
	List<Restaurant> findRestaurantsByKeyword(String keyword);
	
}

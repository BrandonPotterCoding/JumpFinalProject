package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	
	// ***** Get Restaurant By Id *****
	public Restaurant getRestaurantById(long id) throws ResourceNotFoundException{
		
		Optional<Restaurant> found = repo.findById(id); 
		
		if(found == null) {
			throw new ResourceNotFoundException("Restaurant", id);
		}
				
		return found.get();
	}
	
	// ***** Get All Restaurant *****
	public List<Restaurant> getAllRestaurants(){
		return repo.findAll();
	}
	
	// ***** Get Reviews of Restaurant *****
	public List<Review> getReviewsOfRestaurant(long id) throws ResourceNotFoundException{
		
		Restaurant restaurant = getRestaurantById(id);
		
		return restaurant.getReviews();
		
	}
	
	// ***** Add Restaurant *****
	public Restaurant addRestaurant(Restaurant restaurant) {
		
		restaurant.setId(-1L);
		
		Restaurant toAdd = repo.save(restaurant);
		
		return toAdd;	
	}
	
	// ***** Delete Restaurant *****
	public Restaurant deleteRestaurant(long id) throws ResourceNotFoundException {
		
		Restaurant toDelete = getRestaurantById(id);
		
		repo.deleteById(id);
		
		return toDelete;
	}
	
	// ***** Update Restaurant (Unspecified) *****
//	public Restaurant updateRestaurant(Restaurant restaurant) throws ResourceNotFoundException {
//		
//		if(repo.existsById(restaurant.getId())) {
//			Restaurant toUpdate = repo.save(restaurant);
//			
//			return toUpdate;
//		}
//		
//		throw new ResourceNotFoundException("Restaurant", restaurant.getId());
//	}
	
	// ***** Update Restaurant (Specified) *****
	public Restaurant updateRestaurant(long id, String detail, String update) throws ResourceNotFoundException {
		
		if(repo.existsById(id)) {
			
			Restaurant toUpdate = getRestaurantById(id);
			
			switch(detail.toLowerCase()) {
				case "name": 
					toUpdate.setName(update);
					break;
				
				case "address": 
					toUpdate.setAddress(update);
					break;
					
				case "description":
					toUpdate.setDescription(update);
					break;
					
				default: // Restaurant detail does not exist
					throw new ResourceNotFoundException("Detail of Restaurant");
			}
			
			return toUpdate;
		}
	
		throw new ResourceNotFoundException("Restaurant", id);
		
	}
	
}

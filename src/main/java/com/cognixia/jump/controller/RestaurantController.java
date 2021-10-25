package com.cognixia.jump.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.service.RestaurantService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RestaurantController {
	
	@Autowired
	RestaurantService service;
	
	// ***** Get Restaurant By Id *****
	@GetMapping("/restaurant/{id}")
	public Restaurant getRestaurantById(@PathVariable long id) throws ResourceNotFoundException{
		return service.getRestaurantById(id);
	}
	
	// ***** Get All Restaurant *****
	@GetMapping("/restaurants")
	public List<Restaurant> getAllRestaurants(){
		return service.getAllRestaurants();
	}
	
	// ***** Get Reviews of Restaurant *****
	@GetMapping("/{id}/reviews")
	public List<Review> getReviewsOfRestaurant(@PathVariable long id) throws ResourceNotFoundException{
		return service.getReviewsOfRestaurant(id); 
	}
	
	// ***** Add Restaurant *****
	@PostMapping("/restaurant/add")
//	public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant){
//		Restaurant added = service.addRestaurant(restaurant); 
//		
//		return new ResponseEntity<>(added, HttpStatus.CREATED);	
//	}
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant){
        return ResponseEntity.status(201).body(service.addRestaurant(restaurant));
    }
	
	// ***** Delete Restaurant *****
	@DeleteMapping("/restaurant/delete/{id}")
	public Restaurant deleteRestaurant(@PathVariable long id) throws ResourceNotFoundException{
		Restaurant deleted = service.deleteRestaurant(id); 
		
		return deleted;
	}
	
	// ***** Update Restaurant (Unspecified) *****
//	@PutMapping("/restaurant/update")
//	public ResponseEntity<?> updateRestaurant(@RequestBody Restaurant restaurant) throws ResourceNotFoundException{
//		Restaurant updated = service.updateRestaurant(restaurant);
//		
//		return new ResponseEntity<>(updated, HttpStatus.OK);
//	}
 	
	// ***** Update Restaurant (Specified) *****
	@PatchMapping("/restaurant/update/{id}/{detail}")
	public Restaurant updateRestaurant(@PathVariable long id, @PathVariable String detail, @PathParam(value="update") String update) throws ResourceNotFoundException{
		Restaurant updated = service.updateRestaurant(id, detail, update);
		
		return updated;
	}
	
	// ***** Get Restaurant By Name (Assuming no duplicates) *****
	@GetMapping("/restaurant/name/{name}")
	public ResponseEntity<?> getRestaurantByName(@PathVariable String name) throws ResourceNotFoundException{
		Restaurant searched = service.getRestaurantByName(name); 
		
		return new ResponseEntity<>(searched, HttpStatus.OK);
	}
	
	// ***** Get Restaurants By Name *****
	@GetMapping("/restaurants/name/{name}")
	public List<Restaurant> getRestaurantsByName(@PathVariable String name) throws ResourceNotFoundException{
		return service.getRestaurantsByName(name); 
	}
	
	// ***** Get Restaurants By keyword *****
//	@GetMapping("/restaurants/keyword/{keyword}")
//	public List<Restaurant> getRestaurantsByKeyword(@PathVariable String keyword) throws ResourceNotFoundException{
//		return service.getRestaurantsByKeyword(keyword); 
//	}
	
	// ***** Get Top 3 Reviews of Restaurant *****
	@GetMapping("/{id}/top3reviews")
	public List<Review> getTopThreeReviews(@PathVariable long id){
		return service.getTopThreeReviews(id); 
	}
	
}

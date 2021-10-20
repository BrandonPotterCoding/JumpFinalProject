package com.cognixia.jump.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.service.RestaurantService;

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
	public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant){
		Restaurant added = service.addRestaurant(restaurant); 
		
		return new ResponseEntity<>(added, HttpStatus.CREATED);	
	}
	
	// ***** Delete Restaurant *****
	@DeleteMapping("/restaurant/delete/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable long id) throws ResourceNotFoundException{
		Restaurant deleted = service.deleteRestaurant(id); 
		
		return new ResponseEntity<>(deleted, HttpStatus.OK);
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
	public ResponseEntity<?> updateRestaurant(@PathVariable long id, @PathVariable String detail, @PathParam(value="update") String update) throws ResourceNotFoundException{
		Restaurant updated = service.updateRestaurant(id, detail, update);
		
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
}

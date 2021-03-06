package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.RestaurantService;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
	
	private final String STARTING_URI = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RestaurantService service;

	@MockBean
	private RestaurantController controller;
	
	@MockBean
	MyUserDetailsService myUserDetailsService;

	@MockBean
	JwtUtil jwtUtil;
	
	@MockBean
	RestaurantRepository restaurantRepository;

	@Test
	void testGetRestaurantById() throws Exception{
		long id = 1;
		String uri = STARTING_URI + "restaurant/{id}";
		Restaurant restaurant = new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList());

		when(controller.getRestaurantById(id)).thenReturn(restaurant);

		mvc.perform(get(uri, id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(restaurant.getId()))
				.andExpect(jsonPath("$.name").value(restaurant.getName()))
				.andExpect(jsonPath("$.address").value(restaurant.getAddress()))
				.andExpect(jsonPath("$.description").value(restaurant.getDescription()))
				.andExpect(jsonPath("$.reviews").value(restaurant.getReviews()));

		verify(controller, times(1)).getRestaurantById(id);
		verifyNoMoreInteractions(controller);
	}

	@Test
	void testGetAllRestaurants() throws Exception {
		String uri = STARTING_URI + "restaurants";
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
		when(controller.getAllRestaurants()).thenReturn(restaurants);

		mvc.perform(get(uri)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.length()").value(restaurants.size()))
				.andExpect(jsonPath("$[0].id").value(restaurants.get(0).getId()))
				.andExpect(jsonPath("$[0].name").value(restaurants.get(0).getName()))
				.andExpect(jsonPath("$[0].address").value(restaurants.get(0).getAddress()))
				.andExpect(jsonPath("$[0].description").value(restaurants.get(0).getDescription()))
				.andExpect(jsonPath("$[0].reviews").value(restaurants.get(0).getReviews()))
				.andExpect(jsonPath("$[1].id").value(restaurants.get(1).getId()))
				.andExpect(jsonPath("$[1].name").value(restaurants.get(1).getName()))
				.andExpect(jsonPath("$[1].address").value(restaurants.get(1).getAddress()))
				.andExpect(jsonPath("$[1].description").value(restaurants.get(1).getDescription()))
				.andExpect(jsonPath("$[1].reviews").value(restaurants.get(1).getReviews()))
				.andExpect(jsonPath("$[2].id").value(restaurants.get(2).getId()))
				.andExpect(jsonPath("$[2].name").value(restaurants.get(2).getName()))
				.andExpect(jsonPath("$[2].address").value(restaurants.get(2).getAddress()))
				.andExpect(jsonPath("$[2].description").value(restaurants.get(2).getDescription()))
				.andExpect(jsonPath("$[2].reviews").value(restaurants.get(2).getReviews()));

		verify(controller, times(1)).getAllRestaurants();
		verifyNoMoreInteractions(controller);
	}

	@Test
	void testGetReviewsOfRestaurant() throws Exception {
		
		long id = 1;
		String uri = STARTING_URI + "{id}/reviews";
		Restaurant restaurant = new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList());
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));
		List<Review> reviews = Arrays.asList(
				new Review(1L, users.get(0), restaurant, 5L,"Very good"),
				new Review(2L,  users.get(1), restaurant, 5L,"Very good"),
				new Review(3L,  users.get(1), restaurant, 5L,"Very good"));
		restaurant.setReviews(reviews);
	
		when(controller.getReviewsOfRestaurant(id)).thenReturn(restaurant.getReviews());
		
		mvc.perform(get(uri,id)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.length()").value(restaurant.getReviews().size()))
		.andExpect(jsonPath("$[0].id").value(restaurant.getReviews().get(0).getId()))
		.andExpect(jsonPath("$[0].description").value(restaurant.getReviews().get(0).getDescription()))
		.andExpect(jsonPath("$[0].rating").value(restaurant.getReviews().get(0).getRating()))
		.andExpect(jsonPath("$[1].id").value(restaurant.getReviews().get(1).getId()))
		.andExpect(jsonPath("$[1].description").value(restaurant.getReviews().get(1).getDescription()))
		.andExpect(jsonPath("$[1].rating").value(restaurant.getReviews().get(1).getRating()))
		.andExpect(jsonPath("$[2].id").value(restaurant.getReviews().get(2).getId()))
		.andExpect(jsonPath("$[2].description").value(restaurant.getReviews().get(2).getDescription()))
		.andExpect(jsonPath("$[2].rating").value(restaurant.getReviews().get(2).getRating()));
		
		verify(controller, times(1)).getReviewsOfRestaurant(id);
		verifyNoMoreInteractions(controller);
	}

//	@Test
//	void testAddRestaurant() throws Exception {
//		String uri = STARTING_URI + "restaurant/add";
//		Restaurant restaurant = new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList());
//		String restaurantJson = restaurant.toJson();
//		
//		when(service.addRestaurant(Mockito.any(Restaurant.class))).thenReturn(restaurant);
//
//		mvc.perform(post(uri).content(restaurantJson).contentType(MediaType.APPLICATION_JSON)).andDo(print())
//				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
//	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	void testDeleteRestaurant() throws Exception {
		String uri = STARTING_URI + "restaurant/delete/{id}";
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
		Long id = 3L;
		
		when(controller.deleteRestaurant(id)).thenReturn(restaurants.get(2));
		
		mvc.perform(delete(uri, id)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id").value(restaurants.get(2).getId()))
		.andExpect(jsonPath("$.name").value(restaurants.get(2).getName()))
		.andExpect(jsonPath("$.address").value(restaurants.get(2).getAddress()))
		.andExpect(jsonPath("$.description").value(restaurants.get(2).getDescription()))
		.andExpect(jsonPath("$.reviews").value(restaurants.get(2).getReviews()));
		
	}

//	@Test
//	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//	void testUpdateRestaurant() throws Exception {
//		String uri = STARTING_URI +"restaurant/update/{id}/{detail}";
//		List<Restaurant> restaurants = Arrays.asList(
//				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
//				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
//				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
//		Long id = 3L;
//		String detail = "address";
//		String update = "78654 Grape Street";
//		
//		when(controller.updateRestaurant(id, detail, update)).thenReturn(restaurants.get(2));
//		
//		mvc.perform(patch(uri, id,detail,update)).andDo(print()).andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//		.andExpect(jsonPath("$.id").value(restaurants.get(2).getId()))
//		.andExpect(jsonPath("$.name").value(restaurants.get(2).getName()))
//		.andExpect(jsonPath("$.address").value(restaurants.get(2).getAddress()))
//		.andExpect(jsonPath("$.description").value(restaurants.get(2).getDescription()))
//		.andExpect(jsonPath("$.reviews").value(restaurants.get(2).getReviews()));
//
//	}
	
	@Test
	void testGetTopThreeReviews() throws Exception{
		long id = 1;
		String uri = STARTING_URI + "{id}/top3reviews";
		Restaurant restaurant = new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList());
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));
		List<Review> reviews = Arrays.asList(
				new Review(1L, users.get(0), restaurant, 4L,"Pretty good"),
				new Review(2L,  users.get(1), restaurant, 5L,"Very good"),
				new Review(3L,  users.get(1), restaurant, 2L,"Not so good"));
		restaurant.setReviews(reviews);
		
		when(controller.getTopThreeReviews(id)).thenReturn(restaurant.getReviews());
		
		mvc.perform(get(uri,id)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.length()").value(restaurant.getReviews().size()))
		.andExpect(jsonPath("$[0].id").value(restaurant.getReviews().get(0).getId()))
		.andExpect(jsonPath("$[0].description").value(restaurant.getReviews().get(0).getDescription()))
		.andExpect(jsonPath("$[0].rating").value(restaurant.getReviews().get(0).getRating()))
		.andExpect(jsonPath("$[1].id").value(restaurant.getReviews().get(1).getId()))
		.andExpect(jsonPath("$[1].description").value(restaurant.getReviews().get(1).getDescription()))
		.andExpect(jsonPath("$[1].rating").value(restaurant.getReviews().get(1).getRating()))
		.andExpect(jsonPath("$[2].id").value(restaurant.getReviews().get(2).getId()))
		.andExpect(jsonPath("$[2].description").value(restaurant.getReviews().get(2).getDescription()))
		.andExpect(jsonPath("$[2].rating").value(restaurant.getReviews().get(2).getRating()));
		
		verify(controller, times(1)).getTopThreeReviews(id);
		verifyNoMoreInteractions(controller);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

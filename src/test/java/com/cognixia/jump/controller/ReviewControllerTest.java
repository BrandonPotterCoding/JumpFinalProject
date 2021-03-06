package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.ReviewService;
import com.cognixia.jump.util.JwtUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
	
	private final String STARTING_URI = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReviewService service;

	@MockBean
	private ReviewController controller;
	
	@MockBean
	ReviewRepository reviewRepository;
	
	@MockBean
	MyUserDetailsService myUserDetailsService;
	
	@MockBean
	JwtUtil jwtUtil;
	
	@MockBean
	RestaurantRepository restaurantRepository;

	@Test
	void testGetAllReviews() throws Exception {
		String uri = STARTING_URI + "reviews";
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
		
		List<Review> reviews = Arrays.asList(
				new Review(1L, users.get(0), restaurants.get(0), 5L,"Very good"),
				new Review(2L,  users.get(1), restaurants.get(2), 5L,"Very good"),
				new Review(3L,  users.get(1), restaurants.get(0), 5L,"Very good"));
		when(controller.getAllReviews()).thenReturn(reviews);

		mvc.perform(get(uri)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.length()").value(reviews.size()))
				.andExpect(jsonPath("$[0].id").value(reviews.get(0).getId()))
				.andExpect(jsonPath("$[0].description").value(reviews.get(0).getDescription()))
				.andExpect(jsonPath("$[0].rating").value(reviews.get(0).getRating()))
				.andExpect(jsonPath("$[1].id").value(reviews.get(1).getId()))
				.andExpect(jsonPath("$[1].description").value(reviews.get(1).getDescription()))
				.andExpect(jsonPath("$[1].rating").value(reviews.get(1).getRating()))
				.andExpect(jsonPath("$[2].id").value(reviews.get(2).getId()))
				.andExpect(jsonPath("$[2].description").value(reviews.get(2).getDescription()))
				.andExpect(jsonPath("$[2].rating").value(reviews.get(2).getRating()));

		verify(controller, times(1)).getAllReviews();
		verifyNoMoreInteractions(controller);
	}

//	@Test
//	void testGetReviewsByUser() {
//		String uri = STARTING_URI + "review/user";
//		
//		List<User> users = Arrays.asList(
//				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
//						new ArrayList()),
//				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
//				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
//						new ArrayList()));
//		List<Restaurant> restaurants = Arrays.asList(
//				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
//				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
//				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
//		
//		List<Review> reviews = Arrays.asList(
//				new Review(1L, users.get(0), restaurants.get(0), 5L,"Very good"),
//				new Review(2L,  users.get(1), restaurants.get(2), 5L,"Very good"),
//				new Review(3L,  users.get(1), restaurants.get(0), 5L,"Very good"));
//
//		when(controller.getReviewsByUser(new MockHttpServletRequest())).thenReturn(reviews);
//		
//	}
//
//	@Test
//	void testAddReview() {
//		String uri = STARTING_URI + "add/review/{restId}";
//		User brandon = new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
//				new ArrayList());
//		Restaurant wendys = new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList());
//		Review newReview = new Review(1L, brandon, wendys, 5L,"Very good");
//		
//
//		when(controller.addReview(0, newReview, null)).thenReturn();
//		
//	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	void testRemoveReview() throws Exception {
		String uri = STARTING_URI + "delete/review/{id}";
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1L, "Wendys", "12345 Apple Street","Fast food", new ArrayList()),
				new Restaurant(2L, "Chik-fila", "54321 Orange Street","Fast food", new ArrayList()),
				new Restaurant(3L, "McDonalds", "91823 Banana Street", "Fast food", new ArrayList()));
		
		List<Review> reviews = Arrays.asList(
				new Review(1L, users.get(0), restaurants.get(0), 5L,"Very good"),
				new Review(2L,  users.get(1), restaurants.get(2), 5L,"Very good"),
				new Review(3L,  users.get(1), restaurants.get(0), 5L,"Very good"));
		Long id = 3L;
		when(controller.removeReview(id)).thenReturn(reviews.get(2));
		
		mvc.perform(delete(uri,id)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id").value(reviews.get(2).getId()))
		.andExpect(jsonPath("$.description").value(reviews.get(2).getDescription()))
		.andExpect(jsonPath("$.rating").value(reviews.get(2).getRating()));
	}

}

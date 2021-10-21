package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.ReviewService;
import com.cognixia.jump.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
	
	private final String STARTING_URI = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReviewService service;

	@InjectMocks
	private ReviewController controller;
	
	@MockBean
	ReviewRepository reviewRepository;
	
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
				.andExpect(jsonPath("$[0].user").value(reviews.get(0).getUser()))
				.andExpect(jsonPath("$[0].restaurant").value(reviews.get(0).getRestaurant()))
				.andExpect(jsonPath("$[0].description").value(reviews.get(0).getDescription()))
				.andExpect(jsonPath("$[0].rating").value(reviews.get(0).getRating()))
				.andExpect(jsonPath("$[1].id").value(reviews.get(1).getId()))
				.andExpect(jsonPath("$[1].user").value(reviews.get(1).getUser()))
				.andExpect(jsonPath("$[1].restaurant").value(reviews.get(1).getRestaurant()))
				.andExpect(jsonPath("$[1].description").value(reviews.get(1).getDescription()))
				.andExpect(jsonPath("$[1].rating").value(reviews.get(1).getRating()))
				.andExpect(jsonPath("$[2].id").value(reviews.get(2).getId()))
				.andExpect(jsonPath("$[2].user").value(reviews.get(2).getUser()))
				.andExpect(jsonPath("$[2].restaurant").value(reviews.get(2).getRestaurant()))
				.andExpect(jsonPath("$[2].description").value(reviews.get(2).getDescription()))
				.andExpect(jsonPath("$[2].rating").value(reviews.get(2).getRating()));

		verify(controller, times(1)).getAllReviews();
		verifyNoMoreInteractions(controller);
	}

	@Test
	void testGetReviewsByUser() {
		fail("Not yet implemented");
	}

	@Test
	void testAddReview() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveReview() {
		fail("Not yet implemented");
	}

}

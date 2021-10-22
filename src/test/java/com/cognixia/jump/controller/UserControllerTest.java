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

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MyUserDetailsService myUserDetails;

	@MockBean
	JwtUtil jwtUtil;

	@MockBean
	private UserService service;

	@MockBean
	private UserController controller;

	@MockBean
	UserRepository userRepository;

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	void testGetAllUsers() throws Exception {
		String uri = STARTING_URI + "user";
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));

		when(controller.getAllUsers()).thenReturn(users);

		mvc.perform(get(uri)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.length()").value(users.size()))
				.andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
				.andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
				.andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
				.andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
				.andExpect(jsonPath("$[0].reviews").value(users.get(0).getReviews()))
				.andExpect(jsonPath("$[0].displayname").value(users.get(0).getDisplayname()))
				.andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
				.andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
				.andExpect(jsonPath("$[1].username").value(users.get(1).getUsername()))
				.andExpect(jsonPath("$[1].password").value(users.get(1).getPassword()))
				.andExpect(jsonPath("$[1].enabled").value(users.get(1).isEnabled()))
				.andExpect(jsonPath("$[1].reviews").value(users.get(1).getReviews()))
				.andExpect(jsonPath("$[1].displayname").value(users.get(1).getDisplayname()))
				.andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
				.andExpect(jsonPath("$[2].id").value(users.get(2).getId()))
				.andExpect(jsonPath("$[2].username").value(users.get(2).getUsername()))
				.andExpect(jsonPath("$[2].password").value(users.get(2).getPassword()))
				.andExpect(jsonPath("$[2].enabled").value(users.get(2).isEnabled()))
				.andExpect(jsonPath("$[2].reviews").value(users.get(2).getReviews()))
				.andExpect(jsonPath("$[2].displayname").value(users.get(2).getDisplayname()))
				.andExpect(jsonPath("$[2].email").value(users.get(2).getEmail()));

		verify(controller, times(1)).getAllUsers();
		verifyNoMoreInteractions(controller);
	}

	@Test
	void testGetUserById() throws Exception {
		long id = 1;
		String uri = STARTING_URI + "user/{id}";
		User user = new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
				new ArrayList());

		when(controller.getUserById(id)).thenReturn(user);

		mvc.perform(get(uri, id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.username").value(user.getUsername()))
				.andExpect(jsonPath("$.password").value(user.getPassword()))
				.andExpect(jsonPath("$.enabled").value(user.isEnabled()))
				.andExpect(jsonPath("$.reviews").value(user.getReviews()))
				.andExpect(jsonPath("$.displayname").value(user.getDisplayname()))
				.andExpect(jsonPath("$.email").value(user.getEmail()));

		verify(controller, times(1)).getUserById(id);
		verifyNoMoreInteractions(controller);
	}

//	@Test
//	void testAddUser() throws Exception {
//		String uri = STARTING_URI + "add/user";
//
//		User user = new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
//				new ArrayList());
//
//		String userJson = user.toJson();
//
//		when(service.createNewUser(Mockito.any(User.class))).thenReturn(user);
//
//		mvc.perform(post(uri).content(userJson).contentType(MediaType.APPLICATION_JSON)).andDo(print())
//				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//	}

//	@Test
//	void testUpdateUsernamePassword() throws Exception {
//		String uri = STARTING_URI + "user";
//
//		User user = new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
//				new ArrayList());
//
//		AuthenticationRequest ar = new AuthenticationRequest("NewBrandon", "New123456");
//
//		when(service.updateUsernamePassword(ar, user)).thenReturn(user);
//
//		mvc.perform(patch(uri, ar)).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.id").value(user.getId()))
//				.andExpect(jsonPath("$.username").value(user.getUsername()))
//				.andExpect(jsonPath("$.password").value(user.getPassword()))
//				.andExpect(jsonPath("$.enabled").value(user.isEnabled()))
//				.andExpect(jsonPath("$.reviews").value(user.getReviews()))
//				.andExpect(jsonPath("$.displayname").value(user.getDisplayname()))
//				.andExpect(jsonPath("$.email").value(user.getEmail()));
//	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	void testRemoveUser() throws Exception {
		String uri = STARTING_URI + "remove/user/{id}";
		List<User> users = Arrays.asList(
				new User(1L, "Brandon", "123456", true, Role.ROLE_USER, "BrandonDN", "Brandon@Email.com",
						new ArrayList()),
				new User(2L, "Chris", "654321", true, Role.ROLE_USER, "ChrisDN", "Chris@Email.com", new ArrayList()),
				new User(3L, "Debbie", "1qaz2wsx", true, Role.ROLE_USER, "DebbieDN", "Debbie@Email.com",
						new ArrayList()));
		Long id = 3L;
		when(controller.removeUser(id)).thenReturn(users.get(2));

		mvc.perform(delete(uri,id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(users.get(2).getId()))
				.andExpect(jsonPath("$.username").value(users.get(2).getUsername()))
				.andExpect(jsonPath("$.password").value(users.get(2).getPassword()))
				.andExpect(jsonPath("$.enabled").value(users.get(2).isEnabled()))
				.andExpect(jsonPath("$.reviews").value(users.get(2).getReviews()))
				.andExpect(jsonPath("$.displayname").value(users.get(2).getDisplayname()))
				.andExpect(jsonPath("$.email").value(users.get(2).getEmail()));

	}

}

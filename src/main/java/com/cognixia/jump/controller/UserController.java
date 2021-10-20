package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@GetMapping("/user")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		return userService.getUserById(id);
	}
	@PostMapping("/add/user")
	public ResponseEntity<?> addUser(@RequestBody User user) throws Exception{
		User created = userService.createNewUser(user);
		return ResponseEntity.status(201).body(created);
	}
	@PatchMapping("/user")
	public ResponseEntity<?> updateUsernamePassword(@RequestBody AuthenticationRequest updatingUser, Authentication req) throws Exception {
		
		String username = req.getName();
		Optional<User> found = userRepository.findByUsername(username);
		if (found.isPresent()) {
			String oldUsername = found.get().getUsername();
			String oldPassword = found.get().getPassword();
			User updatedUser = userService.updateUsernamePassword(updatingUser, found.get());

			return ResponseEntity.status(200)
					.body("Old Username: " + oldUsername + ", Old Encoded Password: " + oldPassword + ". New Username: "
							+ updatedUser.getUsername() + ", New Encoded Password: " + updatedUser.getPassword() + ".");
		}
		throw new ResourceNotFoundException("User");
	}
	@DeleteMapping("/remove/user/{id}")
	public ResponseEntity<?> removeUser(@PathVariable Long id) throws ResourceNotFoundException{
		User deleted = userService.deleteUser(id);
		return ResponseEntity.status(200).body(deleted);
	}

}

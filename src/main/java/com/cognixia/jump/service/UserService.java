package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserAlreadyExistsException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User getUserById(Long id) throws ResourceNotFoundException {
		Optional<User> found = userRepository.findById(id);
		
		if(found.isPresent())
			return found.get();
		throw new ResourceNotFoundException("User",id);
	}
	
	public User createNewUser(User registeringNewUser) throws Exception{
		Optional<User> isAlreadyRegistered = userRepository.findByUsername(registeringNewUser.getUsername());
		
		if(isAlreadyRegistered.isPresent()) {
			throw new UserAlreadyExistsException(registeringNewUser.getUsername());
		}
		
		registeringNewUser.setPassword(passwordEncoder.encode(registeringNewUser.getPassword()));
		return userRepository.save(registeringNewUser);
		
	}

	public User updateUsernamePassword(AuthenticationRequest updatingUser, User currentUser) {
		currentUser.setUsername(updatingUser.getUsername());
		currentUser.setPassword(passwordEncoder.encode(updatingUser.getPassword()));
		userRepository.save(currentUser);	
		return currentUser;
		
	}

	public User deleteUser(Long id) throws ResourceNotFoundException {
		Optional<User> found = userRepository.findById(id);
		if(found.isPresent()) {
			User deleted = found.get();
			userRepository.delete(deleted);
			return deleted;
		}
		throw new ResourceNotFoundException("User", id);
	}
}

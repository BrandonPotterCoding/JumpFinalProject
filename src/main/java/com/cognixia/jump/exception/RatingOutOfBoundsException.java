package com.cognixia.jump.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RatingOutOfBoundsException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public RatingOutOfBoundsException() {
		super("Ratings could only be from 1 to 5");
	}
	
}

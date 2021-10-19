package com.cognixia.jump.exception;

import java.util.Date;

public class ErrorDetails {

	private Date timeStamp;
	private String message;
	private String path;

	public ErrorDetails(Date timeStamp, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.path = path;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

}

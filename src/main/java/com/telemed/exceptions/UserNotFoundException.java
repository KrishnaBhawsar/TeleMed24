package com.telemed.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String email) {
		super(email);
	}
}

package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310134323828801169L;

	private String message;

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}
}

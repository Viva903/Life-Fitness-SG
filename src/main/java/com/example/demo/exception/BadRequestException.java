package com.example.demo.exception;

public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310134323828801169L;

	private String message;

	public BadRequestException() {
	}

	public BadRequestException(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}
}

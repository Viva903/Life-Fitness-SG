package com.example.demo.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		List<String> errorsList = new ArrayList<String>();
		errorsList.add(ex.getMessage());

		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), errorsList,
				request.getDescription(false));
		System.out.println("Global Exception");
		return message;
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundExceptionHandler(
			ResourceNotFoundException ex, WebRequest request) {
		List<String> errorsList = new ArrayList<String>();
		errorsList.add(ex.getMessage());

		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
				new Date(), errorsList, request.getDescription(false));
		System.out.println("ResourceNotFoundException");
		return message;
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage badRequestExceptionHandler(ConstraintViolationException ex,
			WebRequest request) {
		List<String> errorsList = new ArrayList<String>();
		errorsList.add(ex.getMessage());

		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
				new Date(), errorsList, request.getDescription(false));
		System.out.println("BadRequestException");
		return message;
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex, WebRequest request) {

		List<String> errorsList = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());

		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
				new Date(), errorsList, request.getDescription(false));
		System.out.println("MethodArgumentNotValidException");
		return message;
	}
	
	
}

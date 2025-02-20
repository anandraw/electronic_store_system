package com.anand.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex){
		return new ResponseEntity<String>("Resource Not Found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoUsersFoundException.class)
	public ResponseEntity<ErrorResponce> noUSerFoundException(NoUsersFoundException ex){	
		
	    ErrorResponce errorResponse = new ErrorResponce();
	    errorResponse.setMessage(ex.getMessage());
	    errorResponse.setTimestamp(LocalDateTime.now());
	    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
	    errorResponse.setError("User Not Found");
	    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

}

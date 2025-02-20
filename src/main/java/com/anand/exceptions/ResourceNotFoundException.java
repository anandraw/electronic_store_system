package com.anand.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException() {
		super("Resource Not found!");
	}

}

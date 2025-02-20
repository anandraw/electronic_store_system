package com.anand.exceptions;



public class NoUsersFoundException extends RuntimeException {
	
    public NoUsersFoundException(String message) {
        super(message);
    }

   
    public NoUsersFoundException() {
        super("User does not exist");
    }

}

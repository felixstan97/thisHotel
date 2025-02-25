package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String message, int errorCode){
        super(HttpStatus.NOT_FOUND, message, errorCode);
    }
}

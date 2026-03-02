package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomException{
    public EmailAlreadyExistsException(String email, int errorCode) {
        super(HttpStatus.BAD_REQUEST, "Email already exists: " + email, errorCode);
    }
}

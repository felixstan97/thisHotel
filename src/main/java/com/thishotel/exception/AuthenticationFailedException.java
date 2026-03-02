package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends CustomException{
    public AuthenticationFailedException(String message, int errorCode) {
        super(HttpStatus.UNAUTHORIZED, message, errorCode); // 401 Unauthorized
    }
}

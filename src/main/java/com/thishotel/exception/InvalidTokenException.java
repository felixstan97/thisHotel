package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException{
    public InvalidTokenException(String message, int errorCode){
        super(HttpStatus.BAD_REQUEST, message, errorCode);
    }
}

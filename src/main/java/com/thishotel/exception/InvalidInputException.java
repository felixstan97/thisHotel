package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends CustomException{
    public InvalidInputException(String message, int errorCode) {
        super(HttpStatus.BAD_REQUEST, message, errorCode); // 400 Bad Request
    }
}

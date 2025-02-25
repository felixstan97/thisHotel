package com.thishotel.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handlerCustomException(CustomException ex){
        String responseMessage = String.format("Error %d: %s", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(responseMessage, ex.getStatus());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlerUserNotFound(UserNotFoundException ex) {
        String responseMessage = String.format("User error %d: %s", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(responseMessage, ex.getStatus());
    }
}

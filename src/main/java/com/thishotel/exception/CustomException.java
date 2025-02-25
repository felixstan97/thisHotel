package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final int errorCode;
    private final String message;

    public CustomException(HttpStatus status, String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public int getErrorCode(){
        return errorCode;
    }
}

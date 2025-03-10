package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final int errorCode;
    private final String message;

    public CustomException(HttpStatus status, String message, int errorCode){
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public String getMessage(){
        return message;
    }

}

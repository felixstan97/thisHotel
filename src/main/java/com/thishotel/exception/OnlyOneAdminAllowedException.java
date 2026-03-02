package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class OnlyOneAdminAllowedException extends CustomException{
    public OnlyOneAdminAllowedException(String message, int errorCode) {
        super(HttpStatus.FORBIDDEN, message, errorCode);
    }
}

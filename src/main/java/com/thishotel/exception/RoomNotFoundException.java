package com.thishotel.exception;

import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends CustomException{
    public RoomNotFoundException(Long id, int errorCode){
        super(HttpStatus.NOT_FOUND,"Room with ID: '" + id.toString()+ "' not found", errorCode);
    }
}

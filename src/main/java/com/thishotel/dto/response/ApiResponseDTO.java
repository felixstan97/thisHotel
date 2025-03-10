package com.thishotel.dto.response;

import lombok.Data;

@Data
public class ApiResponseDTO <T>{
    private String status;
    private T data;               //Payload
    private String message;       //error detail
    private Integer errorCode;

    //Success
    public ApiResponseDTO(T data) {
        this.status = "success";
        this.data = data;
        this.message = null;
        this.errorCode = null;
    }

    //Error
    public ApiResponseDTO(String message, Integer errorCode) {
        this.status = "error";
        this.data = null;
        this.message = message;
        this.errorCode = errorCode;
    }

}

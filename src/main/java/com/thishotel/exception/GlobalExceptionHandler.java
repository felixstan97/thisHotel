package com.thishotel.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thishotel.dto.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponseDTO<Object> response = new ApiResponseDTO<>("Access denied: You don’t have permission to perform this action.", 4003);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDTO<String>> handlerCustomException(CustomException ex){
        ApiResponseDTO<String> response = new ApiResponseDTO<>(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMismatchedInputException(InvalidFormatException ex) {
        String fieldName = ex.getPath().get(0).getFieldName();
        String invalidValue = ex.getValue().toString();
        String message = "Invalid value: '" + invalidValue + "' for field: '" + fieldName + "'. Allowed values: " + getAllowedEnumValues(ex);
        ApiResponseDTO<String> response = new ApiResponseDTO<>(message, 1009);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            return handleMismatchedInputException(invalidFormatException);
        }

        ApiResponseDTO<String> response = new ApiResponseDTO<>("Invalid request payload: " + ex.getMessage(), 1010);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserNotFound(UserNotFoundException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(ex.getMessage(), 1000);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ApiResponseDTO<String> response = new ApiResponseDTO<>(errorMessage, 1000);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleJsonParseException(JsonParseException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>("Invalid JSON format", 1008);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGenericException(Exception ex) {
        System.out.println("Unhandled exception: " + ex.getMessage());
        ApiResponseDTO<String> response = new ApiResponseDTO<>("Internal server error", 5000);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getAllowedEnumValues(InvalidFormatException ex) {
        Class<?> enumType = ex.getTargetType();
        if (enumType.isEnum()) {
            Object[] enumConstants = enumType.getEnumConstants();
            return String.join(", ",
                    java.util.Arrays.stream(enumConstants)
                            .map(Object::toString)
                            .toArray(String[]::new)
            );
        }
        return "Unknown";
    }

}

package com.inditex.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ApiError> handlePriceNotFoundException(PriceNotFoundException ex, WebRequest request) {
        return buildResponseEntity(ex.getMessage(), "Price not found", HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponseEntity(ex.getMessage(), "Invalid input", HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ApiError> buildResponseEntity(String message, String error, HttpStatus status, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .message(message)
                .error(error)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(status).body(apiError);
    }
}
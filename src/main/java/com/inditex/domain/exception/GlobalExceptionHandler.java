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

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .error("Price not found")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(PriceListEmptyException.class)
    public ResponseEntity<ApiError> handlePriceListEmptyException(PriceListEmptyException ex, WebRequest request) {

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .error("No prices available")
                .status(HttpStatus.NO_CONTENT.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiError);
    }
}

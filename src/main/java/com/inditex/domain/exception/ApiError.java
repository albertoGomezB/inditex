package com.inditex.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;
    private String path;

}

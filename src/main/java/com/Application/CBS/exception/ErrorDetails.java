package com.bankcore.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String customMessage;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String customMessage, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.customMessage = customMessage;
        this.details = details;
    }
}

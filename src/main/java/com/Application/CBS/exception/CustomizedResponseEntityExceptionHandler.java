package com.bankcore.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        String customMessage = "An error occurred.";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), customMessage, request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public final ResponseEntity<ErrorDetails> handleUserExceptions(Exception ex, WebRequest request) throws Exception {
        String customMessage = "An error occurred, client not found.";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), customMessage, request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExecutionException.class)
    public final ResponseEntity<ErrorDetails> handleExecutionExceptions(Exception ex, WebRequest request) throws Exception {
        String customMessage = "An error occurred, exception during execution.";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), customMessage, request.getDescription(false));        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String customMessage = "An error occurred, invalid argument.";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), customMessage, request.getDescription(false));        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

package com.exceptionj.todos.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleExceptions(ResponseStatusException ex) {
        return buildResponseEntity(ex, HttpStatus.valueOf(ex.getStatusCode().value()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleAllExceptions(Exception ex) {
        return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<ExceptionResponses> buildResponseEntity(Exception exc, HttpStatus status) {

        ExceptionResponses error = new ExceptionResponses();
        error.setStatus(status.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, status);
    }
}

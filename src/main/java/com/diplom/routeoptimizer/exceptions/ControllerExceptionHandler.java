package com.diplom.routeoptimizer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SolutionNotFoundException.class)
    public ResponseEntity<?> solutionNotFound(SolutionNotFoundException e) {
        e.printStackTrace(System.out);
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExistsException(UserAlreadyExistsException ex) {
        List<String> errors = List.of(ex.getMessage());
        Map<String, List<String>> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidSignInDataException.class)
    public ResponseEntity<?> invalidSignInDataExceptionHandler(InvalidSignInDataException ex) {
        List<String> errors = List.of(ex.getMessage());
        Map<String, List<String>> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}

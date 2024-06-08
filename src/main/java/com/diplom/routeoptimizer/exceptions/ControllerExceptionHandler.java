package com.diplom.routeoptimizer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
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

    @ExceptionHandler(InvalidAuthDataException.class)
    public ResponseEntity<?> invalidAuthDataExceptionHandler(InvalidAuthDataException ex) {

        List<String> errors = List.of(ex.getMessage());
        Map<String, List<String>> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> authValidationConstraintHandler(MethodArgumentNotValidException ex) {

        Object[] errMessageArr = ex.getDetailMessageArguments();
        if (errMessageArr == null) {
            throw new InvalidAuthDataException("Неизвестная ошибка аутентификации. Попробуйте ввести другие данные");
        }

        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();

        String errMessage = (String) errMessageArr[1];
        for (String unformattedError : errMessage.split(", and ")) {
            String formattedError = unformattedError.split(": ")[1].strip();
            errors.add(formattedError);
        }

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}

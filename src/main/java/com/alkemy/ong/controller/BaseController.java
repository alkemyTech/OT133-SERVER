package com.alkemy.ong.controller;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintValidationException(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String pathSplits[] = error.getPropertyPath().toString().split("\\.");
            String fieldName = pathSplits[pathSplits.length - 1];
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }

}

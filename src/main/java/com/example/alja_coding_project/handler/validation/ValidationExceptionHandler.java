package com.example.alja_coding_project.handler.validation;

import com.example.alja_coding_project.handler.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> exceptionHandler(MethodArgumentNotValidException exception){
        List<Map<String, String>> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            Map<String, String> error = new HashMap<>();
            error.put("field: ", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }
        Model model = new Model(errors, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
    }
}

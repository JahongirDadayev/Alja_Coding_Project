package com.example.alja_coding_project.handler.global;

import com.example.alja_coding_project.handler.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exceptionHandler(RuntimeException exception){
        Model model = new Model();
        model.setErrors(exception.getMessage());
        model.setStatus(HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(model);
    }
}

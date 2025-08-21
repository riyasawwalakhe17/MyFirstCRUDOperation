package com.company;

import com.company.exception.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<String> handleDeveloperNotFoundException(DeveloperNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage() , HttpStatus.NOT_FOUND);
    }
}

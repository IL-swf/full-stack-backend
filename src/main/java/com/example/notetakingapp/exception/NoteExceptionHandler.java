package com.example.notetakingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<String> elementNotFound(NoSuchElementException e) {
        return new ResponseEntity("Note not Found", HttpStatus.NOT_FOUND);
    }

}

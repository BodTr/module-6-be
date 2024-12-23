package com.codegym.music_project.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.BindException;
import java.net.MalformedURLException;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<Object> handleMalformedURLException(MalformedURLException e) {
        System.out.println("MalformedURLException error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> IOExceptionHandler(IOException e) {
        System.out.println("IOException error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> bindExceptionHandler(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointerExceptionHandler(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Object> httpMessageNotWritableExceptionHandler(HttpMessageNotWritableException e) {
        System.out.println("HttpMessageNotWritableException error: " + e.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStackTrace());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeException(RuntimeException e) {
        System.out.println("runtime error: " + e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}

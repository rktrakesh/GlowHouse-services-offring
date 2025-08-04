package com.glowhouse.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler (Exception exception, WebRequest request) {
        Map <String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        response.put("error",request.getDescription(false));
        response.put("time",LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

}

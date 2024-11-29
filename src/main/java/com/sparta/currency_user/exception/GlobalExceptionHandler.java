package com.sparta.currency_user.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ResponseStatusException 처리
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("status", String.valueOf(ex.getStatusCode().value()));
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    // MethodArgumentNotValidException 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("유효성 검사 오류\n");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField()).append(" 에서의 오류\n").append(error.getDefaultMessage()).append("\n");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }

}

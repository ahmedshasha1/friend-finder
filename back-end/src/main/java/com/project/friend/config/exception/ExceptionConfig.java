package com.project.friend.config.exception;

import com.project.friend.dto.exception.BundleMessage;
import com.project.friend.dto.exception.ExceptionDto;
import com.project.friend.service.bundlemessage.BundleMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionDto> exceptionResponse(RuntimeException runtimeException){
        return ResponseEntity.ok(new ExceptionDto(HttpStatus.NOT_ACCEPTABLE, BundleMessageService.getMessage(runtimeException.getMessage())));
    }
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionDto> exceptionResponse(BadCredentialsException runtimeException){
        return ResponseEntity.ok(new ExceptionDto(HttpStatus.NOT_ACCEPTABLE, BundleMessageService.getMessage(runtimeException.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        String key = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        BundleMessage bundleMessage = BundleMessageService.getMessage(key);

        return ResponseEntity.ok(new ExceptionDto(HttpStatus.NOT_ACCEPTABLE, bundleMessage));
    }


}

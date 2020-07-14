package com.alr.product.infrastructure.controllers;

import com.alr.product.infrastructure.responses.ErrorMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestControllerExceptionHandler {

  private final Logger log;

  public RestControllerExceptionHandler() {
    this.log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
      Map<String, String> body = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        body.put(fieldName, errorMessage);
      });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<ErrorMessageDTO> handle(WebExchangeBindException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    this.log.error("Exception {}", ex);
    ErrorMessageDTO body = new ErrorMessageDTO(status, ex, ZonedDateTime.now());
    return ResponseEntity.status(status).body(body);
  }
}

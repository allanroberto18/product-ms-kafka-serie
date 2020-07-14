package com.alr.product.infrastructure.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO implements Serializable {
  private int status;
  @JsonProperty("exceptionClass")
  private String exceptionClazz;
  private String error;
  private String message;
  private String timestamp;
  private StackTraceElement[] trace;

  public ErrorMessageDTO(HttpStatus status, Exception exception, ZonedDateTime timestamp) {
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.exceptionClazz = exception.getClass().getName();
    this.message = exception.getMessage();
    this.timestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    //this.trace = exception.getStackTrace();
  }
}
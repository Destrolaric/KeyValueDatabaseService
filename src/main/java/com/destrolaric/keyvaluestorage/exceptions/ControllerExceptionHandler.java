package com.destrolaric.keyvaluestorage.exceptions;


import java.io.IOException;
import java.util.Date;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {IOException.class})
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ErrorMessage resourceNotFoundException(IOException ex, WebRequest
      request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(true));

    return message;
  }
}

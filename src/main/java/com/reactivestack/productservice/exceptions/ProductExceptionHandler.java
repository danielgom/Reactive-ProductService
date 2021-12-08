package com.reactivestack.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException notFoundException) {

        ErrorMessage notFoundError = ErrorMessage.builder()
                .details(notFoundException.getLocalizedMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundError);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException notFoundException) {

        ErrorMessage badRequestError = ErrorMessage.builder()
                .details(notFoundException.getLocalizedMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestError);
    }
}

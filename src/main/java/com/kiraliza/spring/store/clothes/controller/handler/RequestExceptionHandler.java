package com.kiraliza.spring.store.clothes.controller.handler;

import com.kiraliza.spring.store.clothes.exception.AccessDeniedException;
import com.kiraliza.spring.store.clothes.exception.CartNotFoundException;
import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.exception.UserNotFoundException;
import com.kiraliza.spring.store.clothes.to.CartErrorMessage;
import com.kiraliza.spring.store.clothes.to.ErrorMessage;
import com.kiraliza.spring.store.clothes.to.ProductErrorMessage;
import com.kiraliza.spring.store.clothes.to.UserErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RequestExceptionHandler
{
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e)
    {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorMessage(e));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<CartErrorMessage> cartNotFoundException(CartNotFoundException e)
    {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new CartErrorMessage(e));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductErrorMessage> productNotFoundException(ProductNotFoundException e)
    {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ProductErrorMessage(e));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserErrorMessage> userNotFoundException(UserNotFoundException e)
    {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new UserErrorMessage(e));
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<ErrorMessage> uriSyntaxException(URISyntaxException e)
    {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorMessage(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

package com.example.demo.exception.advice;

import com.example.demo.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IdentityDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage identityNotFound(IdentityDoesNotExistException exception, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage(),
                webRequest.getDescription(true)
        );
    }

    @ExceptionHandler(RoleDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage roleNotFound(RoleDoesNotExistException exception, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage(),
                webRequest.getDescription(true)
        );
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFound(UserDoesNotExistException exception, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage(),
                webRequest.getDescription(true)
        );
    }

    @ExceptionHandler(MissingRefreshTokenException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage missingRefreshToken(MissingRefreshTokenException exception, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage(),
                webRequest.getDescription(true)
        );
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage incorrectPassword(PasswordMismatchException exception, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                Instant.now(),
                exception.getMessage(),
                webRequest.getDescription(true)
        );
    }

}

@Getter
@AllArgsConstructor
class ErrorMessage{

    private int statusCode;
    private Instant timestamp;
    private String message;
    private String description;

}
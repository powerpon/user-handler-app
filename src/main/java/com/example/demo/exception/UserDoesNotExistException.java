package com.example.demo.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(){
        super("User Does Not Exist");
    }
}

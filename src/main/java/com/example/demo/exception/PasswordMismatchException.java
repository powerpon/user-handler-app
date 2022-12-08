package com.example.demo.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(){
        super("Incorrect Password Entered");
    }
}

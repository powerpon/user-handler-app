package com.example.demo.exception;

public class MissingRefreshTokenException extends RuntimeException{
    public MissingRefreshTokenException(){
        super("Missing Refresh Token");
    }
}

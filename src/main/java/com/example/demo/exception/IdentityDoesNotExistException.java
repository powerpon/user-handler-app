package com.example.demo.exception;

public class IdentityDoesNotExistException extends RuntimeException{

    public IdentityDoesNotExistException(){
        super("That User Identity Does Not Exist");
    }

}

package com.example.demo.exception;

public class RoleDoesNotExistException extends RuntimeException{

    public RoleDoesNotExistException(){
        super("Role Does Not Exist");
    }

}

package com.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("invalid password");
    }

}

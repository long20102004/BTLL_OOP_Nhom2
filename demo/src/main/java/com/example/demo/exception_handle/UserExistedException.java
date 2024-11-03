package com.example.demo.exception_handle;

public class UserExistedException extends RuntimeException{
    private String message;
    public UserExistedException(String message) {
        super(message);
    }
}

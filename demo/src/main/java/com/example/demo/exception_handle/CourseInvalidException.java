package com.example.demo.exception_handle;

public class CourseInvalidException extends RuntimeException{
    private String message;
    public CourseInvalidException(String message) {
        super(message);
    }
}

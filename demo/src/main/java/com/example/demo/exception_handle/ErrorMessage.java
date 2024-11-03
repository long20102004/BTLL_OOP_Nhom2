package com.example.demo.exception_handle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
@Getter
@Setter
public class ErrorMessage extends ResponseEntity<ErrorMessage> {
    private String message;
    public ErrorMessage(HttpStatusCode status, String message) {
        super(status);
        this.message = message;
    }
}

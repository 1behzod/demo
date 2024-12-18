package com.example.demo.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends GlobalException {

    public ConflictException(String title) {
        this(title, title);
    }

    public ConflictException(String title, String message) {
        super(title, message, HttpStatus.CONFLICT);
    }
}

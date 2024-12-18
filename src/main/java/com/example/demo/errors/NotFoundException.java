package com.example.demo.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends GlobalException {

    public NotFoundException(String title) {
        this(title, null);
    }

    public NotFoundException(String title, String message) {
        super(title, message, HttpStatus.NOT_FOUND);
    }

}

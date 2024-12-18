package com.example.demo.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends GlobalException {

    public BadRequestException(String title) {
        this(title, null);
    }


    public BadRequestException(String title, String message) {
        super(title, message, HttpStatus.BAD_REQUEST);
    }
}

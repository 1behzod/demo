package com.example.demo.service;

import com.example.demo.errors.BadRequestException;
import com.example.demo.errors.ConflictException;
import com.example.demo.errors.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@Slf4j
public class BaseService {

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String message) {
        return () -> new BadRequestException(message);
    }

    protected static Supplier<NotFoundException> notFoundExceptionThrow(String message) {
        return () -> new NotFoundException(message);
    }

    protected static Supplier<ConflictException> conflictExceptionThrow(String message) {
        return () -> new ConflictException(message);
    }


}

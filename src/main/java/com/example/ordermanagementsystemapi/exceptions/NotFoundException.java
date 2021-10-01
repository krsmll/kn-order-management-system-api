package com.example.ordermanagementsystemapi.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, long id) {
        super(String.format("Could not find %s with an ID of %s", entity, id));
    }
}

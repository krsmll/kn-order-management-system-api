package com.example.ordermanagementsystemapi.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String className, long id) {
        super(String.format("Could not find %s with an ID of %s", className, id));
    }
}

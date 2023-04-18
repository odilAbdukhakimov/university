package com.example.university.exception;

public class RecordNotFound extends RuntimeException{
    public RecordNotFound(String message) {
        super(message);
    }
}

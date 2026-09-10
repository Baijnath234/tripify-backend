package com.tripify.backend.service;

public class DestinationNotFoundException extends RuntimeException {

    public DestinationNotFoundException(String message) {
        super(message);
    }
}
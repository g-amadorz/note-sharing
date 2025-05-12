package com.example.backend.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User not found with id ", id));
    }
}

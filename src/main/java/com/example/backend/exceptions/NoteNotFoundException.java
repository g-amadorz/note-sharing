package com.example.backend.exceptions;

public class NoteNotFoundException extends EntityNotFoundException {
    public NoteNotFoundException(Long id) {
        super(String.format("Note with id %s not found", id));
    }
}

package com.example.backend.controllers;

import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.NoteService;
import com.example.backend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.util.Set;

@AllArgsConstructor
@Getter
@RestController
@RequestMapping("/users/{userId}/notes")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Set<NoteDto>> getAllNotes(@PathVariable Long userId) {
        try {
            var notes = noteService.getUserNotesById(userId);
            return ResponseEntity.ok(notes);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long userId, @PathVariable Long noteId) {
        try {
            var note = userService.getNoteFromUser(userId, noteId);
            return ResponseEntity.ok(noteService.getNoteDto(note));
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(
            UriBuilder uriBuilder,
            @PathVariable Long userId, @RequestBody CreateNoteRequest request) {
        try {
            var user = userService.getUserById(userId);
            var uri = uriBuilder.path("/users/{userId}/notes").build(userId);
            return ResponseEntity.created(uri).body(noteService.createNote(request, user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }





}

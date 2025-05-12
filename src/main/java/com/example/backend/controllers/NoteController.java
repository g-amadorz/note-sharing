package com.example.backend.controllers;

import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.entities.Note;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.exceptions.NoteNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.NoteRepository;
import com.example.backend.services.NoteService;
import com.example.backend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@Getter
@RestController
@RequestMapping("/users/{userId}/notes")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<Set<Note>> getAllNotes(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        if (user == null) {
           return ResponseEntity.notFound().build();
        }
        Set<Note> notes = user.getNotes();

        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNote(@PathVariable Long userId, @PathVariable Long noteId) {
        try {
            var note = userService.getNoteFromUser(userId, noteId);
            return ResponseEntity.ok(note);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@PathVariable Long userId, @RequestBody CreateNoteRequest request) {
        try {
            var user = userService.getUserById(userId);
            return ResponseEntity.ok(noteService.createNote(request, user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }





}

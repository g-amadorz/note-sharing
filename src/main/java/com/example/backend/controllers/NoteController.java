package com.example.backend.controllers;

import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.dtos.UpdateNoteRequest;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.exceptions.NoteNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.service.NoteService;
import com.example.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
            @PathVariable(name="userId") Long userId, @RequestBody CreateNoteRequest request) {
        try {
            var user = userService.getUserById(userId);
            var uri = uriBuilder.path("/users/{userId}/notes").build(userId);
            return ResponseEntity.created(uri).body(noteService.createNote(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable(name="userId") Long userId, @PathVariable(name = "noteId") Long noteId) {
        try {
            noteService.deleteNoteById(noteId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{noteId}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "noteId") Long noteId,
                                              @RequestBody UpdateNoteRequest request) {
        try {
            var noteDto = noteService.updateNote(noteId, request);
            return ResponseEntity.ok(noteDto);
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
    }
        }




}

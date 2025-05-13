package com.example.backend.services;

import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.entities.Note;
import com.example.backend.entities.User;
import com.example.backend.exceptions.NoteNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.mappers.NoteMapper;
import com.example.backend.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public Note getNoteById(Long id) throws NoteNotFoundException {
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    public NoteDto createNote(CreateNoteRequest request, User user) throws NoteNotFoundException {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setAuthor(user);
        noteRepository.save(note);
        return noteMapper.toNoteDto(note);
    }

    public NoteDto getNoteDto(Note note) {
        return noteMapper.toNoteDto(note);
    }

    public Set<NoteDto> getUserNotesById(Long userId) {
        return noteRepository.getNotesByAuthor_Id(userId).stream().map(noteMapper::toNoteDto).collect(Collectors.toSet());
    }

    public void deleteNoteById(Long noteId) throws NoteNotFoundException {
        getNoteById(noteId);
        noteRepository.deleteById(noteId);
    }

}

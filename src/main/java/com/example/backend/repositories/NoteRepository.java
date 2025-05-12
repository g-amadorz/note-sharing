package com.example.backend.repositories;

import com.example.backend.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> getNoteById(Long id);
}

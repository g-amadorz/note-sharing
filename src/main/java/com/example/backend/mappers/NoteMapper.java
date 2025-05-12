package com.example.backend.mappers;

import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.entities.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    public NoteDto toNoteDto(Note note);
}

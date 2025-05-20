package com.example.backend.mappers;

import com.example.backend.dtos.NoteDto;
import com.example.backend.dtos.UpdateNoteRequest;
import com.example.backend.dtos.CreateNoteRequest;
import com.example.backend.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteDto toNoteDto(Note note);
    Note createNote(CreateNoteRequest createNoteRequest);
    Note update(UpdateNoteRequest updateNoteRequest, @MappingTarget Note note);
}

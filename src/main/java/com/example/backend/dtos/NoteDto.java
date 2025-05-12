package com.example.backend.dtos;
import lombok.Data;

@Data
public class NoteDto {
    private String title;
    private String content;
    private UserDto author;
}

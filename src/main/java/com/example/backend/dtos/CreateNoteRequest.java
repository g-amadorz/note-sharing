package com.example.backend.dtos;

import com.example.backend.entities.User;
import lombok.Data;

@Data
public class CreateNoteRequest {
    private String title;
    private String content;
}

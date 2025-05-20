package com.example.backend.dtos;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private String title;
    private String content;
}

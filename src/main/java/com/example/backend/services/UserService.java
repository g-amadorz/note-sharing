package com.example.backend.services;

import com.example.backend.dtos.UserDto;
import com.example.backend.entities.Note;
import com.example.backend.entities.User;
import com.example.backend.exceptions.NoteNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NoteService noteService;

    public UserDto createUser(User user) {
        return userMapper.toUserDto(userRepository.save(user));
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Note getNoteFromUser(Long userId, Long noteId) throws UserNotFoundException, NoteNotFoundException {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return user.getNotes().stream()
                        .filter(note -> note
                        .getId().equals(noteId))
                        .findFirst()
                        .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

}

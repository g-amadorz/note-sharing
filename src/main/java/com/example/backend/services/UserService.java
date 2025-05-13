package com.example.backend.services;

import com.example.backend.dtos.CreateUserRequest;
import com.example.backend.dtos.NoteDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.Note;
import com.example.backend.entities.User;
import com.example.backend.exceptions.NoteNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    public void deleteUserById(Long userId) throws UserNotFoundException {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    public boolean userExists(CreateUserRequest request) {
        var username = request.getUsername();
        var email = request.getEmail();

        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username);
    }

    public UserDto createUser(CreateUserRequest request) {
        var user = userMapper.createUser(request);
        userRepository.save(user);
        var userDto = userMapper.toUserDto(user);
        return userDto;
    }
}

package com.example.backend.controllers;

import com.example.backend.dtos.CreateUserRequest;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false, name = "id") String sort) {
        if ("desc".equalsIgnoreCase(sort)) {
            userRepository.findAll(Sort.by(Sort.Direction.DESC, "username"));
        }
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"))
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(required = true, name = "user_id") Long user_id) {
        var user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(
            UriComponentsBuilder uriBuilder,
            @RequestBody CreateUserRequest request) {
        var email = request.getEmail();
        var username = request.getUsername();

        if (userRepository.existsByEmail(email) || userRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        var user = userMapper.createUser(request);
        userRepository.save(user);
        var userDto = userMapper.toUserDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(required = true, name = "user_id") Long user_id) {
        var user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(user_id);
        return ResponseEntity.noContent().build();
    }
}

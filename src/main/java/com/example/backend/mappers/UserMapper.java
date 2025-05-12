package com.example.backend.mappers;

import com.example.backend.dtos.CreateUserRequest;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserDto toUserDto(User user);
    public User toUser(UserDto userDto);
    public User createUser(CreateUserRequest createUserRequest);
}

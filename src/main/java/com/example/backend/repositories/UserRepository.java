package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.id FROM User u")
    List<Long> findAllIds();

    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
}

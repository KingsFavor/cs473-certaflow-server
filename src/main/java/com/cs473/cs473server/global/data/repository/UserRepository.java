package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String userId);

}

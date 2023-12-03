package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {

    Optional<Chat> findById(String chatId);

    List<Chat> findByChatLocationId(String locationId);

}

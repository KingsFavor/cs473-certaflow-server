package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    Optional<ChatMessage> findById(String messageId);
    List<ChatMessage> findByChatMessageChatIdOrderByGeneratedAtAsc(String chatId);

}

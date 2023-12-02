package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.ChatMessageDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @Getter
    @Column(name = "message_id")
    String messageId;

    @Getter
    @Column(name = "message_content", columnDefinition = "TEXT")
    String messageContent;

    @Getter
    @Column(name = "generated_at")
    LocalDateTime generatedAt;

    @Getter
    @Column(name = "expired_at")
    LocalDateTime expiredAt;

    @Getter
    @Column(name = "likes_count")
    Integer likesCount;

    @Getter
    @Column(name = "is_official")
    boolean isOfficial;

    @Getter
    @Column(name = "fk__chat_messages__chats")
    String chatMessageChatId;

    @Getter
    @Column(name = "fk__chat_messages__users")
    String chatMessageUserId;

    public ChatMessageDto toDto() {
        return ChatMessageDto.builder()
                .messageId(messageId)
                .messageContent(messageContent)
                .generatedAt(generatedAt)
                .expiredAt(expiredAt)
                .likesCount(likesCount)
                .isOfficial(isOfficial)
                .chatMessageChatId(chatMessageChatId)
                .chatMessageUserId(chatMessageUserId)
                .build();
    }

}

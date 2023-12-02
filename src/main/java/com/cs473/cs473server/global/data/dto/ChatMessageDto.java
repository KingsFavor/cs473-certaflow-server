package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.ChatMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageDto {

    String messageId;
    String messageContent;
    LocalDateTime generatedAt;
    LocalDateTime expiredAt;
    Integer likesCount;
    boolean isOfficial;
    String chatMessageChatId;
    String chatMessageUserId;

    public ChatMessage toEntity() {
        return ChatMessage.builder()
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

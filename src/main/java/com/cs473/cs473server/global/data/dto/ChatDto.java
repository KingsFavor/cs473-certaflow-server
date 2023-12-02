package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.Chat;
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
public class ChatDto {

    String chatId;
    LocalDateTime localDateTime;
    Integer membersCount;
    String chatLocationId;

    public Chat toEntity() {
        return Chat.builder()
                .chatId(chatId)
                .localDateTime(localDateTime)
                .membersCount(membersCount)
                .chatLocationId(chatLocationId)
                .build();
    }
}

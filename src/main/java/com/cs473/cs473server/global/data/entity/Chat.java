package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.ChatDto;
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
@Table(name = "chats")
public class Chat {

    @Id
    @Getter
    @Column(name = "chat_id")
    String chatId;

    @Getter
    @Column(name = "generated_at")
    LocalDateTime localDateTime;

    @Getter
    @Column(name = "members_count")
    Integer membersCount;

    @Getter
    @Column(name = "fk__chats__locations")
    String chatLocationId;

    public ChatDto toDto() {
        return ChatDto.builder()
                .chatId(chatId)
                .localDateTime(localDateTime)
                .membersCount(membersCount)
                .chatLocationId(chatLocationId)
                .build();
    }

}

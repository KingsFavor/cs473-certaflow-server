package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.UserMessageLikeDto;
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
@Table(name = "user_message_likes")
public class UserMessageLike {

    @Id
    @Getter
    @Column(name = "mapping_id")
    String mappingId;

    @Getter
    @Column(name = "mapped_at")
    LocalDateTime mappedAt;

    @Getter
    @Column(name = "fk__user_message_likes__users")
    String userMessageLikeUserId;

    @Getter
    @Column(name = "fk__user_message_likes__chat_messages")
    String userMessageLikeChatMessageId;

    public UserMessageLikeDto toDto() {
        return UserMessageLikeDto.builder()
                .mappingId(mappingId)
                .mappedAt(mappedAt)
                .userMessageLikeUserId(userMessageLikeUserId)
                .userMessageLikeChatMessageId(userMessageLikeChatMessageId)
                .build();
    }

}

package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.UserMessageLike;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserMessageLikeDto {

    String mappingId;
    LocalDateTime mappedAt;
    String userMessageLikeUserId;
    String userMessageLikeChatMessageId;

    public UserMessageLike toEntity() {
        return UserMessageLike.builder()
                .mappingId(mappingId)
                .mappedAt(mappedAt)
                .userMessageLikeUserId(userMessageLikeUserId)
                .userMessageLikeChatMessageId(userMessageLikeChatMessageId)
                .build();
    }

}

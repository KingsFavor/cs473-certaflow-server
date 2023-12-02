package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.UserTipLike;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserTipLikeDto {

    private String mappingId;
    private LocalDateTime mappedAt;
    private String userTipLikeUserId;
    private String userTipLikeTipId;

    public UserTipLike toEntity() {
        return UserTipLike.builder()
                .mappingId(mappingId)
                .mappedAt(mappedAt)
                .userTipLikeUserId(userTipLikeUserId)
                .userTipLikeTipId(userTipLikeTipId)
                .build();
    }

}

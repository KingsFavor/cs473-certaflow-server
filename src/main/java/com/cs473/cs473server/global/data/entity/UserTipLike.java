package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.UserTipLikeDto;
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
@Table(name = "user_tip_likes")
public class UserTipLike {

    @Id
    @Getter
    @Column(name = "mapping_id")
    String mappingId;

    @Getter
    @Column(name = "mapped_at")
    LocalDateTime mappedAt;

    @Getter
    @Column(name = "fk__user_tip_likes__users")
    String userTipLikeUserId;

    @Getter
    @Column(name = "fk__user_tip_likes__tips")
    String userTipLikeTipId;

    public UserTipLikeDto toDto() {
        return UserTipLikeDto.builder()
                .mappingId(mappingId)
                .mappedAt(mappedAt)
                .userTipLikeUserId(userTipLikeUserId)
                .userTipLikeTipId(userTipLikeTipId)
                .build();
    }

}

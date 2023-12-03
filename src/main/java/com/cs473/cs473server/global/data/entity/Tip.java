package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.TipDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "tips")
public class Tip {

    @Id
    @Getter
    @Column(name = "tip_id")
    String tipId;

    @Getter
    @Column(name = "tip_content", columnDefinition = "TEXT")
    String tipContent;

    @Getter
    @Column(name = "likes_count")
    Integer likesCount;

    @Getter
    @Column(name = "is_official")
    boolean isOfficial;

    @Getter
    @Column(name = "fk__tips__users")
    String tipUserId;

    @Getter
    @Column(name = "fk__tips__locations")
    String tipLocationId;

    public TipDto toDto() {
        return TipDto.builder()
                .tipId(tipId)
                .tipContent(tipContent)
                .likesCount(likesCount)
                .isOfficial(isOfficial)
                .tipUserId(tipUserId)
                .tipLocationId(tipLocationId)
                .build();
    }

}

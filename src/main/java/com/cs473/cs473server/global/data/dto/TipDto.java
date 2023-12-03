package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.Tip;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TipDto {

    String tipId;
    String tipContent;
    Integer likesCount;
    boolean isOfficial;
    String tipUserId;
    String tipLocationId;

    public Tip toEntity() {
        return Tip.builder()
                .tipId(tipId)
                .tipContent(tipContent)
                .likesCount(likesCount)
                .isOfficial(isOfficial)
                .tipUserId(tipUserId)
                .tipLocationId(tipLocationId)
                .build();
    }
}

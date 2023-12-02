package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.CongestionOfficial;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CongestionOfficialDto {

    String informationId;
    Integer congestionLevel;
    LocalDateTime expiredAt;
    String congestionOfficialUserId;
    String congestionOfficialLocationId;

    public CongestionOfficial toEntity() {
        return CongestionOfficial.builder()
                .informationId(informationId)
                .congestionLevel(congestionLevel)
                .expiredAt(expiredAt)
                .congestionOfficialUserId(congestionOfficialUserId)
                .congestionOfficialLocationId(congestionOfficialLocationId)
                .build();
    }
}

package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.CongestionOfficialDto;
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
@Table(name = "congestion_officials")
public class CongestionOfficial {

    @Id
    @Getter
    @Column(name = "information_id")
    String informationId;

    @Getter
    @Column(name = "congestion_level")
    Integer congestionLevel;

    @Getter
    @Column(name = "expired_at")
    LocalDateTime expiredAt;

    @Getter
    @Column(name = "fk__congestion_officials__users")
    String congestionOfficialUserId;

    @Getter
    @Column(name = "fk__congestion_officials__locations")
    String congestionOfficialLocationId;

    public CongestionOfficialDto toDto() {
        return CongestionOfficialDto.builder()
                .informationId(informationId)
                .congestionLevel(congestionLevel)
                .expiredAt(expiredAt)
                .congestionOfficialUserId(congestionOfficialUserId)
                .congestionOfficialLocationId(congestionOfficialLocationId)
                .build();
    }

}

package com.cs473.cs473server.global.data.entity;

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
@Table(name = "congestion_feedbacks")
public class CongestionFeedback {

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
    @Column(name = "fk__congestion_feedbacks__users")
    String congestionFeedbackUserId;

    @Getter
    @Column(name = "fk__congestion_feedbacks__locations")
    String congestionFeedbackLocationId;

}

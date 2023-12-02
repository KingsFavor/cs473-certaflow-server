package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.CongestionFeedback;
import lombok.Getter;

import java.time.LocalDateTime;

public class CongestionFeedbackDto {

    String informationId;
    Integer congestionLevel;
    LocalDateTime expiredAt;
    String congestionFeedbackUserId;
    String congestionFeedbackLocationId;

    public CongestionFeedback toEntity() {
        return CongestionFeedback.builder()
                .informationId(informationId)
                .congestionLevel(congestionLevel)
                .expiredAt(expiredAt)
                .congestionFeedbackUserId(congestionFeedbackUserId)
                .congestionFeedbackLocationId(congestionFeedbackLocationId)
                .build();
    }

}

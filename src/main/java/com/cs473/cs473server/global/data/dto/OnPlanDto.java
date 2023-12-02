package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.OnPlan;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OnPlanDto {


    String onPlanId;
    LocalDateTime generatedAt;
    LocalDateTime expiredAt;
    Integer planIndex;
    String onPlanUserId;
    String onPlanLocationId;

    public OnPlan toEntity() {
        return OnPlan.builder()
                .onPlanId(onPlanId)
                .generatedAt(generatedAt)
                .expiredAt(expiredAt)
                .planIndex(planIndex)
                .onPlanUserId(onPlanUserId)
                .onPlanLocationId(onPlanLocationId)
                .build();
    }
}

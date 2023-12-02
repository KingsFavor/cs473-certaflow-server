package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.OnPlanDto;
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
@Table(name = "on_plans")
public class OnPlan {

    @Id
    @Getter
    @Column(name = "on_plan_id")
    String onPlanId;

    @Getter
    @Column(name = "generated_at")
    LocalDateTime generatedAt;

    @Getter
    @Column(name = "expired_at")
    LocalDateTime expiredAt;

    @Getter
    @Column(name = "plan_index")
    Integer planIndex;

    @Getter
    @Column(name = "fk__on_plans__users")
    String onPlanUserId;

    @Getter
    @Column(name = "fk__on_plans__locations")
    String onPlanLocationId;

    public OnPlanDto toDto() {
        return OnPlanDto.builder()
                .onPlanId(onPlanId)
                .generatedAt(generatedAt)
                .expiredAt(expiredAt)
                .planIndex(planIndex)
                .onPlanUserId(onPlanUserId)
                .onPlanLocationId(onPlanLocationId)
                .build();
    }

}

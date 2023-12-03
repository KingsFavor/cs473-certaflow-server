package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.OnPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OnPlanRepository extends JpaRepository<OnPlan, String> {

    Optional<OnPlan> findById(String onPlanId);

    List<OnPlan> findByOnPlanUserId(String userId);

}

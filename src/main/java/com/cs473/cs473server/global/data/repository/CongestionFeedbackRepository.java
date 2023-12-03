package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.CongestionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CongestionFeedbackRepository extends JpaRepository<CongestionFeedback, String> {

    Optional<CongestionFeedback> findById(String congestionId);

}

package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip, String> {

    Optional<Tip> findById(String tipId);
    List<Tip> findByTipLocationId(String locationId);
    List<Tip> findByTipUserId(String userId);

}

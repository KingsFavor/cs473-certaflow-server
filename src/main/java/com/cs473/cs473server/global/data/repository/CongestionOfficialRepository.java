package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.CongestionOfficial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CongestionOfficialRepository extends JpaRepository<CongestionOfficial, String> {

    List<CongestionOfficial> findAll();
    List<CongestionOfficial> findByCongestionOfficialLocationIdOrderByExpiredAtDesc(String locationId);
    Optional<CongestionOfficial> findById(String congestionId);

}

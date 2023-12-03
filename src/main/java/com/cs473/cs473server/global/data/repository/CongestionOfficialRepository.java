package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.CongestionOfficial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CongestionOfficialRepository extends JpaRepository<CongestionOfficial, String> {

    Optional<CongestionOfficial> findById(String congestionId);

}

package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findById(String locationId);
    List<Location> findAll();

}

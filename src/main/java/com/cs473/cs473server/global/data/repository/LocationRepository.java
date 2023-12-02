package com.cs473.cs473server.global.data.repository;

import com.cs473.cs473server.global.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, String> {

    List<Location> findAll();

}

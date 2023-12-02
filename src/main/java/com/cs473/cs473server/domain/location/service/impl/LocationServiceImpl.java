package com.cs473.cs473server.domain.location.service.impl;

import com.cs473.cs473server.domain.location.service.LocationService;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Map<String, Object> getAllLocation() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        item.put("locationList", locationRepository.findAll());

        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);

        return resultMap;
    }

}

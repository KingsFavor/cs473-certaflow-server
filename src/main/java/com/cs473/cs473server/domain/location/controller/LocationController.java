package com.cs473.cs473server.domain.location.controller;

import com.cs473.cs473server.domain.location.service.LocationService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final ResponseBodyFormatService responseBodyFormatService = new ResponseBodyFormatService();

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /* get all */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllLocation() {
        return responseBodyFormatService.formatResponseEntity(locationService.getAllLocation());
    }
}

package com.cs473.cs473server.domain.location.service.impl;

import com.cs473.cs473server.domain.location.service.LocationService;
import com.cs473.cs473server.global.data.dto.LocationDto;
import com.cs473.cs473server.global.data.dto.OnPlanDto;
import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.entity.OnPlan;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import com.cs473.cs473server.global.data.repository.OnPlanRepository;
import com.cs473.cs473server.global.data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final OnPlanRepository onPlanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               OnPlanRepository onPlanRepository,
                               UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.onPlanRepository = onPlanRepository;
        this.userRepository = userRepository;
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

    @Override
    public Map<String, Object> getDetail(String locationId, String userId) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        Location targetLocation = locationRepository.findById(locationId).get();
        boolean isIncludedInOnPlan = false;

        List<OnPlan> onPlanListOfUser = onPlanRepository.findByOnPlanUserId(userId);

        for (OnPlan onPlan : onPlanListOfUser) {
            if (targetLocation.getLocationId().equals(onPlan.getOnPlanLocationId())) {
                isIncludedInOnPlan = true;
            }
        }

        item.put("locationInfo", targetLocation);
        item.put("isIncludedInOnPlan", isIncludedInOnPlan);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);

        return resultMap;
    }

    @Override
    public Map<String, Object> addOnPlan(String locationId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        List<OnPlan> onPlanListOfUser = onPlanRepository.findByOnPlanUserId(userId);

        /* already exist */
        for (OnPlan onPlan : onPlanListOfUser) {
            if (onPlan.getOnPlanLocationId().equals(locationId)) {
                resultMap.put("reason", "Already in on-plan.");
                resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
        }

        /* craft Dto */
        OnPlanDto onPlanDto = OnPlanDto.builder()
                .onPlanId(UUID.randomUUID().toString())
                .generatedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(1))
                .planIndex(onPlanListOfUser.size() + 1)
                .onPlanUserId(userId)
                .onPlanLocationId(locationId)
                .build();

        /* save */
        onPlanRepository.save(onPlanDto.toEntity());

        item.put("onPlanInfo", onPlanDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);

        return resultMap;
    }

    @Override
    public Map<String, Object> deleteOnPlan(String locationId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        List<OnPlan> onPlanListOfUser = onPlanRepository.findByOnPlanUserId(userId);
        String deleteTargetId = "";

        /* not exist */
        boolean isExist = false;
        for (OnPlan onPlan : onPlanListOfUser) {
            if (onPlan.getOnPlanLocationId().equals(locationId)) {
                isExist = true;
                deleteTargetId = onPlan.getOnPlanId();
            }
        }
        if (!isExist) {
            resultMap.put("reason", "Not in on-plan.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* delete */
        onPlanRepository.deleteById(deleteTargetId);

        item.put("deleted result", true);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);

        return resultMap;
    }

    @Override
    public Map<String, Object> editLocationInfo(String locationId, String userId, Map<String, Object> requestBody) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get target location */
        LocationDto targetLocationDto = locationRepository.findById(locationId).get().toDto();

        if (requestBody.get("locationLatitude") != null) {
            Double newLatitude = 0.0;
            try {
                newLatitude = Double.parseDouble((String) requestBody.get("locationLatitude"));
            } catch (Exception e) {
                resultMap.put("reason", "Invalid latitude. Failed to parse.");
                resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            targetLocationDto.setLocationLatitude(newLatitude);
        }
        if (requestBody.get("locationLongitude") != null) {
            Double newLongitude = 0.0;
            try {
                newLongitude = Double.parseDouble((String) requestBody.get("locationLongitude"));
            } catch (Exception e) {
                resultMap.put("reason", "Invalid longitude. Failed to parse.");
                resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            targetLocationDto.setLocationLatitude(newLongitude);
        }
        if (requestBody.get("locationName") != null) {
            targetLocationDto.setLocationName((String) requestBody.get("locationName"));
        }
        if (requestBody.get("locationDescription") != null) {
            targetLocationDto.setLocationDescription((String) requestBody.get("locationDescription"));
        }
        if (requestBody.get("locationUserId") != null) {
            /* check existence */
            if (userRepository.findById((String) requestBody.get("locationUserId")).isEmpty()) {
                resultMap.put("reason", "Not existing user id : " + requestBody.get("locationUserId"));
                resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            targetLocationDto.setLocationUserId((String) requestBody.get("locationUserId"));
        }
        if (requestBody.get("openHour") != null) {
            targetLocationDto.setOpenHour((String) requestBody.get("openHour"));
        }
        if (requestBody.get("phoneNumber") != null) {
            targetLocationDto.setPhoneNumber((String) requestBody.get("phoneNumber"));
        }
        if (requestBody.get("homepage") != null) {
            targetLocationDto.setHomepage((String) requestBody.get("homepage"));
        }
        if (requestBody.get("locationAddress") != null) {
            targetLocationDto.setLocationAddress((String) requestBody.get("locationAddress"));
        }

        /* save */
        locationRepository.save(targetLocationDto.toEntity());

        item.put("editedLocationInfo", targetLocationDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> editCurrentView(String locationId, Integer amount) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get target location */
        LocationDto targetLocationDto = locationRepository.findById(locationId).get().toDto();

        targetLocationDto.setCurrentViewsCount(targetLocationDto.getCurrentViewsCount() + amount);

        /* save */
        locationRepository.save(targetLocationDto.toEntity());

        item.put("afterCount", targetLocationDto.getCurrentViewsCount());
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> editNearUserCount(String locationId, Integer amount) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get target location */
        LocationDto targetLocationDto = locationRepository.findById(locationId).get().toDto();

        targetLocationDto.setNearUserCount(targetLocationDto.getNearUserCount() + amount);

        /* save */
        locationRepository.save(targetLocationDto.toEntity());

        item.put("afterCount", targetLocationDto.getNearUserCount());
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

}

package com.cs473.cs473server.domain.location.service.impl;

import com.cs473.cs473server.domain.location.service.LocationService;
import com.cs473.cs473server.global.data.dto.LocationDto;
import com.cs473.cs473server.global.data.dto.OnPlanDto;
import com.cs473.cs473server.global.data.dto.TipDto;
import com.cs473.cs473server.global.data.dto.UserTipLikeDto;
import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.entity.OnPlan;
import com.cs473.cs473server.global.data.entity.Tip;
import com.cs473.cs473server.global.data.entity.UserTipLike;
import com.cs473.cs473server.global.data.repository.*;
import com.cs473.cs473server.global.service.CongestionLevelCoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final CongestionLevelCoreService congestionLevelCoreService;
    private final LocationRepository locationRepository;
    private final OnPlanRepository onPlanRepository;
    private final UserRepository userRepository;
    private final TipRepository tipRepository;
    private final UserTipLikeRepository userTipLikeRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               OnPlanRepository onPlanRepository,
                               UserRepository userRepository,
                               TipRepository tipRepository,
                               UserTipLikeRepository userTipLikeRepository,
                               ChatRepository chatRepository,
                               CongestionLevelCoreService congestionLevelCoreService) {
        this.locationRepository = locationRepository;
        this.onPlanRepository = onPlanRepository;
        this.userRepository = userRepository;
        this.tipRepository = tipRepository;
        this.userTipLikeRepository = userTipLikeRepository;
        this.chatRepository = chatRepository;
        this.congestionLevelCoreService = congestionLevelCoreService;
    }

    @Override
    public Map<String, Object> getAllLocation() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        List<Location> locationList = locationRepository.findAll();

        /* update congestion level */
        for (Location location : locationList) {
            congestionLevelCoreService.updateLocationCongestionLevel(location.getLocationId());
        }

        item.put("locationList", locationRepository.findAll());

        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);

        return resultMap;
    }

    @Override
    public Map<String, Object> getDetail(String locationId, String userId) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* update congestion level */
        congestionLevelCoreService.updateLocationCongestionLevel(locationId);

        Location targetLocation = locationRepository.findById(locationId).get();
        boolean isIncludedInOnPlan = false;

        List<OnPlan> onPlanListOfUser = onPlanRepository.findByOnPlanUserId(userId);

        for (OnPlan onPlan : onPlanListOfUser) {
            if (targetLocation.getLocationId().equals(onPlan.getOnPlanLocationId())) {
                isIncludedInOnPlan = true;
            }
        }

        /* get chat id */
        String chatId = chatRepository.findByChatLocationId(locationId).get(0).getChatId();

        item.put("locationInfo", targetLocation);
        item.put("locationChatId", chatId);
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

    @Override
    public Map<String, Object> getAllTips(String locationId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* result list */
        List<Map<String, Object>> resultList = new ArrayList<>();

        /* get tip list */
        List<Tip> tipListOfLocation = tipRepository.findByTipLocationId(locationId);

        /* check like exist & craft list */
        for (Tip locationTip : tipListOfLocation) {
            Map<String, Object> listElement = new HashMap<>();
            listElement.put("tipInfo", locationTip);

            boolean isLike = false;
            List<UserTipLike> userTipLikeList = userTipLikeRepository.findByUserTipLikeUserId(userId);
            for (UserTipLike tipLike : userTipLikeList) {
                if (tipLike.getUserTipLikeTipId().equals(locationTip.getTipId())) {
                    isLike = true;
                }
            }
            listElement.put("isLikedByUser", isLike);
            resultList.add(listElement);
        }

        item.put("tipList", resultList);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> addTip(String locationId, String userId, Map<String, String> requestBody, boolean isOfficial) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* craft tip dto */
        TipDto tipDto = TipDto.builder()
                .tipId(UUID.randomUUID().toString())
                .tipContent(requestBody.get("tipContent"))
                .likesCount(0)
                .isOfficial(isOfficial)
                .tipUserId(userId)
                .tipLocationId(locationId)
                .build();

        /* save */
        tipRepository.save(tipDto.toEntity());

        item.put("addedTip", tipDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteTip(String tipId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* delete tip likes */
        List<UserTipLike> tipLikes = userTipLikeRepository.findByUserTipLikeTipId(tipId);
        Integer tipLikeCount = tipLikes.size();
        for (UserTipLike tipLike : tipLikes) {
            userTipLikeRepository.deleteById(tipLike.getMappingId());
        }

        /* delete tip */
        tipRepository.deleteById(tipId);

        item.put("deletedTipId", tipId);
        item.put("deletedTipLikes", tipLikeCount);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> addTipLike(String tipId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* already exist */
        boolean isLiked = false;
        List<UserTipLike> userTipLikeList = userTipLikeRepository.findByUserTipLikeUserId(userId);
        for (UserTipLike tipLike : userTipLikeList) {
            if (tipLike.getUserTipLikeTipId().equals(tipId)) {
                isLiked = true;
            }
        }
        if (isLiked) {
            resultMap.put("reason", "Already liked by the user.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* craft UserTipLikeDto */
        UserTipLikeDto userTipLikeDto = UserTipLikeDto.builder()
                .mappingId(UUID.randomUUID().toString())
                .mappedAt(LocalDateTime.now())
                .userTipLikeUserId(userId)
                .userTipLikeTipId(tipId)
                .build();

        /* save */
        userTipLikeRepository.save(userTipLikeDto.toEntity());

        /* edit tip itself */
        Tip targetTip = tipRepository.findById(tipId).get();
        targetTip.setLikesCount(targetTip.getLikesCount() + 1);
        tipRepository.save(targetTip);

        item.put("addedTipLike", userTipLikeDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteTipLike(String tipId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* check existence */
        boolean isLiked = false;
        List<UserTipLike> userTipLikeList = userTipLikeRepository.findByUserTipLikeUserId(userId);
        for (UserTipLike tipLike : userTipLikeList) {
            if (tipLike.getUserTipLikeTipId().equals(tipId)) {
                isLiked = true;
            }
        }
        if (!isLiked) {
            resultMap.put("reason", "The user have not liked the given tip.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* delete */
        userTipLikeRepository.deleteById(tipId);

        /* edit tip itself */
        Tip targetTip = tipRepository.findById(tipId).get();
        targetTip.setLikesCount(targetTip.getLikesCount() - 1);
        tipRepository.save(targetTip);

        item.put("deleteResult", true);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

}

package com.cs473.cs473server.domain.user.service.impl;

import com.cs473.cs473server.domain.user.service.UserService;
import com.cs473.cs473server.global.data.dto.UserDto;
import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.entity.OnPlan;
import com.cs473.cs473server.global.data.entity.User;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import com.cs473.cs473server.global.data.repository.OnPlanRepository;
import com.cs473.cs473server.global.data.repository.UserRepository;
import com.cs473.cs473server.global.service.ContributionLevelCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final ContributionLevelCoreService contributionLevelCoreService;
    private final UserRepository userRepository;
    private final OnPlanRepository onPlanRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OnPlanRepository onPlanRepository,
                           LocationRepository locationRepository,
                           ContributionLevelCoreService contributionLevelCoreService) {
        this.userRepository = userRepository;
        this.onPlanRepository = onPlanRepository;
        this.locationRepository = locationRepository;
        this.contributionLevelCoreService = contributionLevelCoreService;
    }

    @Override
    public Map<String, Object> getDetail(String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* update contribution level */
        contributionLevelCoreService.updateContribution(userId);

        User targetUser = userRepository.findById(userId).get();

        item.put("userInfo", targetUser);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> registerUser(String userId, String userPassword, String userName) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* craft UserDto */
        UserDto userDto = UserDto.builder()
                .userId(userId)
                .userName(userName)
                .userPassword(userPassword)
                .isActive(true)
                .generatedAt(LocalDateTime.now())
                .contributionLevel(0)
                .feedbackNumber(0)
                .isOfficialUser(false)
                .build();

        /* save */
        userRepository.save(userDto.toEntity());

        item.put("userInfo", userDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> getOnPlans(String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        List<Map<String, Object>> resultList = new ArrayList<>();

        /* get on-plan list */
        List<OnPlan> onPlanList = onPlanRepository.findByOnPlanUserId(userId);

        for (OnPlan onPlan : onPlanList) {
            Map<String, Object> resultListElement = new HashMap<>();
            resultListElement.put("onPlanInfo", onPlan);

            /* get location info */
            Location targetLocation = locationRepository.findById(onPlan.getOnPlanLocationId()).get();
            resultListElement.put("locationInfo", targetLocation);

            resultList.add(resultListElement);
        }

        item.put("item", resultList);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }
}

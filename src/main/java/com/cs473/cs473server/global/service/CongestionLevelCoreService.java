package com.cs473.cs473server.global.service;

import com.cs473.cs473server.global.data.entity.CongestionFeedback;
import com.cs473.cs473server.global.data.entity.CongestionOfficial;
import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.repository.CongestionFeedbackRepository;
import com.cs473.cs473server.global.data.repository.CongestionOfficialRepository;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CongestionLevelCoreService {

    @Value("${CURRENT_VIEW_WEIGHT}")
    private Integer CURRENT_VIEW_WEIGHT;

    @Value("${OFFICIAL_INFO_WEIGHT}")
    private Integer OFFICIAL_INFO_WEIGHT;

    @Value("${USER_FEEDBACK_WEIGHT}")
    private Integer USER_FEEDBACK_WEIGHT;

    @Value("${NEAR_USER_WEIGHT}")
    private Integer NEAR_USER_WEIGHT;

    /* repositories */
    private final LocationRepository locationRepository;
    private final CongestionFeedbackRepository congestionFeedbackRepository;
    private final CongestionOfficialRepository congestionOfficialRepository;

    @Autowired
    public CongestionLevelCoreService(LocationRepository locationRepository,
                                      CongestionFeedbackRepository congestionFeedbackRepository,
                                      CongestionOfficialRepository congestionOfficialRepository) {
        this.locationRepository = locationRepository;
        this.congestionFeedbackRepository = congestionFeedbackRepository;
        this.congestionOfficialRepository = congestionOfficialRepository;
    }

    private Double sigmoid(Integer input) {
        return 1.0 / (1.0 + Math.exp(-input));
    }

    private Integer roundToInteger(Double input) {
        return (int) Math.round(input);
    }

    public void updateLocationCongestionLevel(String locationId) {

        /* target location */
        Location targetLocation = locationRepository.findById(locationId).get();

        /* calculate current view part */
        Double currentViewValue = CURRENT_VIEW_WEIGHT * sigmoid(targetLocation.getCurrentViewsCount());

        /* calculate official info part */
        /* get official infos */
        List<CongestionOfficial> congestionOfficialList = congestionOfficialRepository
                .findByCongestionOfficialLocationIdOrderByExpiredAtDesc(locationId);
        /* When duplicate, the one with the longest ttl is selected. */
        Double officialInfoValue = 0.0;
        if (!congestionOfficialList.isEmpty()) {
            officialInfoValue = OFFICIAL_INFO_WEIGHT * (congestionOfficialList.get(0).getCongestionLevel() / 3.0);
        }

        /* calculate user feedback info part */
        /* get user feedbacks */
        List<CongestionFeedback> congestionFeedbackList = congestionFeedbackRepository.findByCongestionFeedbackLocationId(locationId);
        Integer sum = 0;
        for (CongestionFeedback congestionFeedback : congestionFeedbackList) {
            sum = sum + congestionFeedback.getCongestionLevel();
        }
        Double feedbackInfoValue = USER_FEEDBACK_WEIGHT * sigmoid(sum);

        /* calculate near user info part */
        Double nearUserValue = NEAR_USER_WEIGHT * sigmoid(targetLocation.getNearUserCount());

        /* take sum */
        Double resultSum = currentViewValue + officialInfoValue + feedbackInfoValue + nearUserValue;

        targetLocation.setLocationCongestionLevel(roundToInteger(resultSum));
        locationRepository.save(targetLocation);

    }



}

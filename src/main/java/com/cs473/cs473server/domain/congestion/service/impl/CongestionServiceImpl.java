package com.cs473.cs473server.domain.congestion.service.impl;

import com.cs473.cs473server.domain.congestion.service.CongestionService;
import com.cs473.cs473server.global.data.dto.CongestionFeedbackDto;
import com.cs473.cs473server.global.data.dto.CongestionOfficialDto;
import com.cs473.cs473server.global.data.repository.CongestionFeedbackRepository;
import com.cs473.cs473server.global.data.repository.CongestionOfficialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CongestionServiceImpl implements CongestionService {

    private final CongestionFeedbackRepository congestionFeedbackRepository;
    private final CongestionOfficialRepository congestionOfficialRepository;

    @Autowired
    public CongestionServiceImpl(CongestionFeedbackRepository congestionFeedbackRepository,
                                 CongestionOfficialRepository congestionOfficialRepository) {
        this.congestionFeedbackRepository = congestionFeedbackRepository;
        this.congestionOfficialRepository = congestionOfficialRepository;
    }

    @Override
    public Map<String, Object> addCongestionUserFeedback(String locationId, String userId, Map<String, Object> requestBody) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* parse body */
        Integer congestionLevel = 0;
        Integer timeToLive = 0;

        try {
            congestionLevel = Integer.parseInt((String) requestBody.get("congestionLevel"));
        } catch (Exception e) {
            resultMap.put("reason", "Invalid congestion level. Failed to parse.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        try {
            timeToLive = Integer.parseInt((String) requestBody.get("timeToLive"));
        } catch (Exception e) {
            resultMap.put("reason", "Invalid time to live. Failed to parse.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* craft CongestionFeedbackDto */
        CongestionFeedbackDto congestionFeedbackDto = CongestionFeedbackDto.builder()
                .informationId(UUID.randomUUID().toString())
                .congestionLevel(congestionLevel)
                .expiredAt(LocalDateTime.now().plusSeconds(timeToLive))
                .congestionFeedbackUserId(userId)
                .congestionFeedbackLocationId(locationId)
                .build();

        /* save */
        congestionFeedbackRepository.save(congestionFeedbackDto.toEntity());

        item.put("addedFeedback", congestionFeedbackDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> addCongestionOfficial(String locationId, String userId, Map<String, Object> requestBody) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* parse body */
        Integer congestionLevel = 0;
        Integer timeToLive = 0;

        try {
            congestionLevel = Integer.parseInt((String) requestBody.get("congestionLevel"));
        } catch (Exception e) {
            resultMap.put("reason", "Invalid congestion level. Failed to parse.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        try {
            timeToLive = Integer.parseInt((String) requestBody.get("timeToLive"));
        } catch (Exception e) {
            resultMap.put("reason", "Invalid time to live. Failed to parse.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* craft CongestionOfficialDto */
        CongestionOfficialDto congestionOfficialDto = CongestionOfficialDto.builder()
                .informationId(UUID.randomUUID().toString())
                .congestionLevel(congestionLevel)
                .expiredAt(LocalDateTime.now().plusSeconds(timeToLive))
                .congestionOfficialUserId(userId)
                .congestionOfficialLocationId(locationId)
                .build();

        /* save */
        congestionOfficialRepository.save(congestionOfficialDto.toEntity());

        item.put("addedOfficial", congestionOfficialDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }
}

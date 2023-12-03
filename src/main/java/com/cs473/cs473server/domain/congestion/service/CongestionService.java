package com.cs473.cs473server.domain.congestion.service;

import java.util.Map;

public interface CongestionService {

    Map<String, Object> addCongestionUserFeedback(String locationId, String userId, Map<String, Object> requestBody);
    Map<String, Object> addCongestionOfficial(String locationId, String userId, Map<String, Object> requestBody);

}

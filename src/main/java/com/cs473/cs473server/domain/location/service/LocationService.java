package com.cs473.cs473server.domain.location.service;

import java.util.Map;

public interface LocationService {

    Map<String, Object> getAllLocation();
    Map<String, Object> getDetail(String locationId, String userId);
    Map<String, Object> addOnPlan(String locationId, String userId);
    Map<String, Object> deleteOnPlan(String locationId, String userId);
    Map<String, Object> editLocationInfo(String locationId, String userId, Map<String, Object> requestBody);
    Map<String, Object> editCurrentView(String locationId, Integer amount);
    Map<String, Object> editNearUserCount(String locationId, Integer amount);
    Map<String, Object> getAllTips(String locationId, String userId);
    Map<String, Object> addTip(String locationId, String userId, Map<String, String> requestBody, boolean isOfficial);
    Map<String, Object> deleteTip(String tipId);
    Map<String, Object> addTipLike(String tipId, String userId);
    Map<String, Object> deleteTipLike(String tipId, String userId);

}

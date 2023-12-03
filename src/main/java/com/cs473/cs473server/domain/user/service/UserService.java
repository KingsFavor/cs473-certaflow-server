package com.cs473.cs473server.domain.user.service;

import java.util.Map;

public interface UserService {

    Map<String, Object> getDetail(String userId);
    Map<String, Object> registerUser(String userId, String userPassword, String userName);
    Map<String, Object> getOnPlans(String userId);
}

package com.cs473.cs473server.domain.login.service;

import java.util.Map;

public interface LoginService {

    Map<String, Object> login(String userId, String userPassword);

}

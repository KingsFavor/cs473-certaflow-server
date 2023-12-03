package com.cs473.cs473server.domain.login.service.impl;

import com.cs473.cs473server.domain.location.service.impl.LocationServiceImpl;
import com.cs473.cs473server.domain.login.service.LoginService;
import com.cs473.cs473server.global.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Object> login(String userId, String userPassword) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* check id */
        if (userRepository.findById(userId).isEmpty()) {
            resultMap.put("reason", "Not existing user.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        /* check password */
        if (!userRepository.findById(userId).get().getUserPassword().equals(userPassword)) {
            resultMap.put("reason", "Invalid password.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        item.put("userName", userRepository.findById(userId).get().getUserName());
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }
}

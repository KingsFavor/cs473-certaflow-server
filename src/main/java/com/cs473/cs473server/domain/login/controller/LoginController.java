package com.cs473.cs473server.domain.login.controller;

import com.cs473.cs473server.domain.login.service.LoginService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final ResponseBodyFormatService responseBodyFormatService = new ResponseBodyFormatService();

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {

        /* requestBody validation */
        if (requestBody.get("userId") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user id must be given");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (requestBody.get("userPassword") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user password must be given");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(loginService.login(requestBody.get("userId"), requestBody.get("userPassword")));
    }

}

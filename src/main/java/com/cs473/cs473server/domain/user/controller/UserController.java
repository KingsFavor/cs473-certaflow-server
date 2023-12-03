package com.cs473.cs473server.domain.user.controller;

import com.cs473.cs473server.domain.user.service.UserService;
import com.cs473.cs473server.global.service.DataCheckService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ResponseBodyFormatService responseBodyFormatService = new ResponseBodyFormatService();

    private final DataCheckService dataCheckService;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService,
                          DataCheckService dataCheckService) {
        this.userService = userService;
        this.dataCheckService = dataCheckService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable String userId) {

        /* check existence */
        if (!dataCheckService.isUserIdExist(userId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing user id : " + userId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(userService.getDetail(userId));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> requestBody) {

        /* request body validation */
        if (requestBody.get("userId") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user id must be given");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (requestBody.get("userName") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user name must be given");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (requestBody.get("userPassword") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user password must be given");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService
                .formatResponseEntity(
                        userService.registerUser(
                                requestBody.get("userId"),
                                requestBody.get("userPassword"),
                                requestBody.get("userName")
                        )
                );
    }

    @GetMapping("/on-plan")
    public ResponseEntity<Map<String, Object>> getUserOnPlans(@RequestHeader Map<String, String> httpHeader) {

        /* header */
        String userId = httpHeader.get("cert_user_id");
        if (userId == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "user id must be given.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (!dataCheckService.isUserIdExist(userId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing user id : " + userId);
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(userService.getOnPlans(userId));
    }

}

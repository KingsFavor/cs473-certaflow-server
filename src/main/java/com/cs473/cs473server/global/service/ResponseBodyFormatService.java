package com.cs473.cs473server.global.service;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ResponseBodyFormatService {

    public ResponseEntity<Map<String, Object>> formatResponseEntity(Map<String, Object> resultMap) {
        Map<String, Object> responseBody = new HashMap<>();

        HttpStatus httpStatus = (HttpStatus) resultMap.get("httpStatus");

        if (httpStatus.equals(HttpStatus.OK)) {
            responseBody.put("item", resultMap.get("item"));
        }
        if (!httpStatus.equals(HttpStatus.OK)) {
            responseBody.put("reason", resultMap.get("reason"));
        }

        return new ResponseEntity<>(responseBody, httpStatus);
    }

}

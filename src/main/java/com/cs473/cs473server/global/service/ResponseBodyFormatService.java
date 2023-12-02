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

        return new ResponseEntity<>((Map<String, Object>) resultMap.get("item"), httpStatus);
    }

}

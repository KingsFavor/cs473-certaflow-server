package com.cs473.cs473server.domain.congestion.controller;

import com.cs473.cs473server.domain.congestion.service.CongestionService;
import com.cs473.cs473server.global.service.DataCheckService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/congestion")
public class CongestionController {

    private ResponseBodyFormatService responseBodyFormatService;

    private final DataCheckService dataCheckService;
    private final CongestionService congestionService;

    @Autowired
    public CongestionController(CongestionService congestionService,
                                DataCheckService dataCheckService) {
        this.congestionService = congestionService;
        this.dataCheckService = dataCheckService;
    }


    @PostMapping("/location/{locationId}/official/register")
    public ResponseEntity<Map<String, Object>> registerFeedBack(@RequestHeader Map<String, String> httpHeader,
                                                                @PathVariable String locationId,
                                                                @RequestBody Map<String, Object> requestBody) {
        /* check existence */
        /* location id */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        /* check response body */
        if (requestBody.get("congestionLevel") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "congestion level must be give.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (requestBody.get("timeToLive") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "time to live must be give.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

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

        return responseBodyFormatService.formatResponseEntity(congestionService.addCongestionUserFeedback(locationId, userId, requestBody));
    }


    @PostMapping("/location/{locationId}/official/register")
    public ResponseEntity<Map<String, Object>> registerOfficialInfo(@RequestHeader Map<String, String> httpHeader,
                                                                    @PathVariable String locationId,
                                                                    @RequestBody Map<String, Object> requestBody) {
        /* check existence */
        /* location id */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        /* check response body */
        if (requestBody.get("congestionLevel") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "congestion level must be give.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (requestBody.get("timeToLive") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "time to live must be give.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

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

        /* check isMaster */
        if (!dataCheckService.isMasterUserOfLocation(userId, locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "The given user is not a master of the location.");
            resultMap.put("httpStatus", HttpStatus.UNAUTHORIZED);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(congestionService.addCongestionOfficial(locationId, userId, requestBody));
    }
}

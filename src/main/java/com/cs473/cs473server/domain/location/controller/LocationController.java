package com.cs473.cs473server.domain.location.controller;

import com.cs473.cs473server.domain.location.service.LocationService;
import com.cs473.cs473server.global.service.DataCheckService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final ResponseBodyFormatService responseBodyFormatService = new ResponseBodyFormatService();

    private final DataCheckService dataCheckService;
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService,
                              DataCheckService dataCheckService) {
        this.locationService = locationService;
        this.dataCheckService = dataCheckService;
    }


    /* get all */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllLocation() {
        return responseBodyFormatService.formatResponseEntity(locationService.getAllLocation());
    }


    /* get detail */
    @GetMapping("/{locationId}")
    public ResponseEntity<Map<String, Object>> getDetail(@RequestHeader Map<String, String> httpHeader,
                                                         @PathVariable String locationId) {
        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        return responseBodyFormatService.formatResponseEntity(locationService.getDetail(locationId, userId));
    }


    /* add location into on-plan */
    @PostMapping("/{locationId}/add-on-plan")
    public ResponseEntity<Map<String, Object>> addOnPlan(@RequestHeader Map<String, String> httpHeader,
                                                         @PathVariable String locationId) {
        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        return responseBodyFormatService.formatResponseEntity(locationService.addOnPlan(locationId, userId));
    }


    /* delete location from on-plan */
    @DeleteMapping("/{locationId}/delete-on-plan")
    public ResponseEntity<Map<String, Object>> deleteOnPlan(@RequestHeader Map<String, String> httpHeader,
                                                            @PathVariable String locationId) {
        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        return responseBodyFormatService.formatResponseEntity(locationService.deleteOnPlan(locationId, userId));
    }


    /* edit location */
    @PutMapping("/{locationId}")
    public ResponseEntity<Map<String, Object>> editLocationInfo(@RequestHeader Map<String, String> httpHeader,
                                                                @PathVariable String locationId,
                                                                @RequestBody Map<String, Object> requestBody) {
        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        /* isMaster */
        if (dataCheckService.isMasterUserOfLocation(userId, locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "The given user is not master user of the given location.");
            resultMap.put("httpStatus", HttpStatus.UNAUTHORIZED);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.editLocationInfo(locationId, userId, requestBody));
    }


    /* increment current view */
    @PutMapping("/{locationId}/current-view/increment")
    public ResponseEntity<Map<String, Object>> incrementCurrentView(@PathVariable String locationId) {

        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.editCurrentView(locationId, 1));
    }


    /* decrement current view */
    @PutMapping("/{locationId}/current-view/decrement")
    public ResponseEntity<Map<String, Object>> decrementCurrentView(@PathVariable String locationId) {

        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.editCurrentView(locationId, -1));
    }


    /* register near user */
    @PutMapping("/{locationId}/near-user/register")
    public ResponseEntity<Map<String, Object>> registerNearUser(@PathVariable String locationId) {

        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.editNearUserCount(locationId, 1));
    }


    /* remove near user */
    @PutMapping("/{locationId}/near-user/remove")
    public ResponseEntity<Map<String, Object>> removeNearUser(@PathVariable String locationId) {

        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.editNearUserCount(locationId, -1));
    }


    /* get all tips */
    @GetMapping("/{locationId}/tip")
    public ResponseEntity<Map<String, Object>> getAllTips(@RequestHeader Map<String, String> httpHeader,
                                                          @PathVariable String locationId) {

        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        return responseBodyFormatService.formatResponseEntity(locationService.getAllTips(locationId, userId));
    }


    /* add tip */
    @PostMapping("/{locationId}/tip")
    public ResponseEntity<Map<String, Object>> addTip(@RequestHeader Map<String, String> httpHeader,
                                                      @PathVariable String locationId,
                                                      @RequestBody Map<String, String> requestBody) {
        /* check existence */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        /* request body validation */
        if (requestBody.get("tipContent") == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "tip content must be given.");
            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.addTip(locationId, userId, requestBody, dataCheckService.isMasterUserOfLocation(userId, locationId)));
    }


    /* delete tip */
    @DeleteMapping("/{locationId}/tip/{tipId}")
    public ResponseEntity<Map<String, Object>> deleteTip(@RequestHeader Map<String, String> httpHeader,
                                                         @PathVariable String locationId,
                                                         @PathVariable String tipId) {
        /* check existence */
        /* location id */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        /* tip id */
        if (!dataCheckService.isTipIdExist(tipId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing tip id : " + tipId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        /* combination */
        if (!dataCheckService.isTipIsOfLocation(tipId, locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "The given tip is not about the given location.");
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        if (!dataCheckService.isTipIsFromUser(tipId, userId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Cannot delete other user's tip.");
            resultMap.put("httpStatus", HttpStatus.UNAUTHORIZED);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.deleteTip(tipId));
    }


    /* add like to the tip */
    @PostMapping("/{locationId}/tip/{tipId}/like")
    public ResponseEntity<Map<String, Object>> addLikeToTip(@RequestHeader Map<String, String> httpHeader,
                                                            @PathVariable String locationId,
                                                            @PathVariable String tipId) {
        /* check existence */
        /* location id */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        /* tip id */
        if (!dataCheckService.isTipIdExist(tipId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing tip id : " + tipId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        /* combination */
        if (!dataCheckService.isTipIsOfLocation(tipId, locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "The given tip is not about the given location.");
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.addTipLike(tipId, userId));
    }


    @DeleteMapping("/{locationId}/tip/{tipId}/unlike")
    public ResponseEntity<Map<String, Object>> deleteLikeFromTip(@RequestHeader Map<String, String> httpHeader,
                                                            @PathVariable String locationId,
                                                            @PathVariable String tipId) {
        /* check existence */
        /* location id */
        if (!dataCheckService.isLocationIdExist(locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing location id : " + locationId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }
        /* tip id */
        if (!dataCheckService.isTipIdExist(tipId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing tip id : " + tipId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
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

        /* combination */
        if (!dataCheckService.isTipIsOfLocation(tipId, locationId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "The given tip is not about the given location.");
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(locationService.deleteTipLike(tipId, userId));
    }

}

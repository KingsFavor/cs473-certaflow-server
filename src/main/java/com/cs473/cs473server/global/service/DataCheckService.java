package com.cs473.cs473server.global.service;

import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import com.cs473.cs473server.global.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataCheckService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataCheckService(LocationRepository locationRepository,
                            UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    /* -------------------------------------------------------- */
    /* -------------------- Data existence -------------------- */

    public boolean isLocationIdExist(String locationId) {
        return locationRepository.findById(locationId).isPresent();
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    /* -------------------------------------------------------- */
    /* -------------------------------------------------------- */

    /* -------------------------------------------------------- */
    /* ------------------- Data Combination ------------------- */

    public boolean isMasterUserOfLocation(String userId, String locationId) {
        Location targetLocation = locationRepository.findById(locationId).get();

        if (targetLocation.getLocationUserId() == null) {
            return false;
        } else {
            return targetLocation.getLocationUserId().equals(userId);
        }
    }

    /* -------------------------------------------------------- */
    /* -------------------------------------------------------- */
}

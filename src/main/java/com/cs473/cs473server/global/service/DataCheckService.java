package com.cs473.cs473server.global.service;

import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.repository.ChatRepository;
import com.cs473.cs473server.global.data.repository.LocationRepository;
import com.cs473.cs473server.global.data.repository.TipRepository;
import com.cs473.cs473server.global.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataCheckService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TipRepository tipRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public DataCheckService(LocationRepository locationRepository,
                            UserRepository userRepository,
                            TipRepository tipRepository,
                            ChatRepository chatRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.tipRepository = tipRepository;
        this.chatRepository = chatRepository;
    }

    /* -------------------------------------------------------- */
    /* -------------------- Data existence -------------------- */

    public boolean isLocationIdExist(String locationId) {
        return locationRepository.findById(locationId).isPresent();
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    public boolean isTipIdExist(String tipId) {
        return tipRepository.findById(tipId).isPresent();
    }

    public boolean isChatIdExist(String chatId) {
        return chatRepository.findById(chatId).isPresent();
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

    public boolean isTipIsOfLocation(String tipId, String locationId) {
        return tipRepository.findById(tipId).get().getTipLocationId().equals(locationId);
    }

    public boolean isTipIsFromUser(String tipId, String userId) {
        return tipRepository.findById(tipId).get().getTipUserId().equals(userId);
    }

    /* -------------------------------------------------------- */
    /* -------------------------------------------------------- */

    /* -------------------------------------------------------- */
    /* Extract data */

    public String getLocationIdFromChatId(String chatId) {
        return chatRepository.findById(chatId).get().getChatLocationId();
    }

    /* -------------------------------------------------------- */
    /* -------------------------------------------------------- */
}

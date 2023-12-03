package com.cs473.cs473server.global.service;

import com.cs473.cs473server.global.data.entity.Location;
import com.cs473.cs473server.global.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataCheckService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TipRepository tipRepository;
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public DataCheckService(LocationRepository locationRepository,
                            UserRepository userRepository,
                            TipRepository tipRepository,
                            ChatRepository chatRepository,
                            ChatMessageRepository chatMessageRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.tipRepository = tipRepository;
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
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

    public boolean isChatMessageExist(String messageId) {
        return chatMessageRepository.findById(messageId).isPresent();
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

    public boolean isMessageIsInChat(String messageId, String chatId) {
        return chatMessageRepository.findById(messageId).get().getChatMessageChatId().equals(chatId);
    }

    public boolean isMessageFromUser(String messageId, String userId) {
        return chatMessageRepository.findById(messageId).get().getChatMessageUserId().equals(userId);
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

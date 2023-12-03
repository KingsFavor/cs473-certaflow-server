package com.cs473.cs473server.domain.chat.service;

import java.util.Map;

public interface ChatService {

    Map<String, Object> getChatInfo(String chatId);
    Map<String, Object> getChatMessages(String chatId, String userId);
    Map<String, Object> getInToChatRoom(String chatId);
    Map<String, Object> getOutFromChatRoom(String chatId);
    Map<String, Object> addMessageToChat(String chatId, String userId, String messageContent, boolean isOfficial);
}

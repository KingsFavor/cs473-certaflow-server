package com.cs473.cs473server.domain.chat.service.impl;

import com.cs473.cs473server.domain.chat.service.ChatService;
import com.cs473.cs473server.global.data.entity.Chat;
import com.cs473.cs473server.global.data.entity.ChatMessage;
import com.cs473.cs473server.global.data.entity.UserMessageLike;
import com.cs473.cs473server.global.data.repository.ChatMessageRepository;
import com.cs473.cs473server.global.data.repository.ChatRepository;
import com.cs473.cs473server.global.data.repository.UserMessageLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserMessageLikeRepository userMessageLikeRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository,
                           UserMessageLikeRepository userMessageLikeRepository,
                           ChatMessageRepository chatMessageRepository) {
        this.chatRepository = chatRepository;
        this.userMessageLikeRepository = userMessageLikeRepository;
        this.chatMessageRepository = chatMessageRepository;
    }


    @Override
    public Map<String, Object> getChatInfo(String chatId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get target chat */
        Chat targetChat = chatRepository.findById(chatId).get();

        item.put("chatInfo", targetChat);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> getChatMessages(String chatId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        List<Map<String, Object>> resultList = new ArrayList<>();

        /* get user message likes */
        List<UserMessageLike> userMessageLikeList = userMessageLikeRepository.findByUserMessageLikeUserId(userId);

        /* get messages */
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatMessageChatId(chatId);

        /* check like exists & craft element */
        for (ChatMessage chatMessage : chatMessageList) {
            Map<String, Object> resultListElement = new HashMap<>();
            resultListElement.put("messageInfo", chatMessage);
            boolean isLiked = false;
            for (UserMessageLike userMessageLike : userMessageLikeList) {
                if (userMessageLike.getUserMessageLikeChatMessageId().equals(chatMessage.getMessageId())) {
                    isLiked = true;
                }
            }
            resultListElement.put("isLikedByUser", isLiked);
            resultList.add(resultListElement);
        }

        item.put("chatMessageList", resultList);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> getInToChatRoom(String chatId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get chat */
        Chat targetChat = chatRepository.findById(chatId).get();

        targetChat.setMembersCount(targetChat.getMembersCount() + 1);
        chatRepository.save(targetChat);

        item.put("currentMembersCount", targetChat.getMembersCount());
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
    }

    @Override
    public Map<String, Object> getOutFromChatRoom(String chatId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get chat */
        Chat targetChat = chatRepository.findById(chatId).get();

        targetChat.setMembersCount(targetChat.getMembersCount() - 1);
        chatRepository.save(targetChat);

        item.put("currentMembersCount", targetChat.getMembersCount());
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
    }
}

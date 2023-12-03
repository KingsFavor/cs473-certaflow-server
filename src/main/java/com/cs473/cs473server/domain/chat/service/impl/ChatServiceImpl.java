package com.cs473.cs473server.domain.chat.service.impl;

import com.cs473.cs473server.domain.chat.service.ChatService;
import com.cs473.cs473server.global.data.dto.ChatMessageDto;
import com.cs473.cs473server.global.data.entity.Chat;
import com.cs473.cs473server.global.data.entity.ChatMessage;
import com.cs473.cs473server.global.data.entity.UserMessageLike;
import com.cs473.cs473server.global.data.repository.ChatMessageRepository;
import com.cs473.cs473server.global.data.repository.ChatRepository;
import com.cs473.cs473server.global.data.repository.UserMessageLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatMessageChatIdOrderByGeneratedAtAsc(chatId);

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
        return resultMap;
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
        return resultMap;
    }

    @Override
    public Map<String, Object> addMessageToChat(String chatId, String userId, String messageContent, boolean isOfficial) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* craft chatMessageDto */
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .messageId(UUID.randomUUID().toString())
                .messageContent(messageContent)
                .generatedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(1))
                .likesCount(0)
                .isOfficial(isOfficial)
                .chatMessageChatId(chatId)
                .chatMessageUserId(userId)
                .build();

        /* save */
        chatMessageRepository.save(chatMessageDto.toEntity());

        item.put("addedMessage", chatMessageDto);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteChatMessage(String messageId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> item = new HashMap<>();

        /* get message likes */
        List<UserMessageLike> userMessageLikeList = userMessageLikeRepository.findByUserMessageLikeChatMessageId(messageId);
        Integer likeCount = userMessageLikeList.size();

        /* delete likes */
        for (UserMessageLike messageLike : userMessageLikeList) {
            userMessageLikeRepository.deleteById(messageLike.getMappingId());
        }

        /* delete message */
        chatMessageRepository.deleteById(messageId);

        item.put("deletedMessageId", messageId);
        item.put("deletedMessageLikeCount", likeCount);
        resultMap.put("item", item);
        resultMap.put("httpStatus", HttpStatus.OK);
        return resultMap;
    }


}

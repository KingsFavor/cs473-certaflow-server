package com.cs473.cs473server.domain.chat.controller;

import com.cs473.cs473server.domain.chat.service.ChatService;
import com.cs473.cs473server.global.service.DataCheckService;
import com.cs473.cs473server.global.service.ResponseBodyFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {

    private final ResponseBodyFormatService responseBodyFormatService = new ResponseBodyFormatService();

    private final DataCheckService dataCheckService;
    private final ChatService chatService;

    @Autowired
    public ChatController(DataCheckService dataCheckService,
                          ChatService chatService) {
        this.dataCheckService = dataCheckService;
        this.chatService = chatService;
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<Map<String, Object>> getChatInfo(@PathVariable String chatId) {

        /* check existence */
        if (!dataCheckService.isChatIdExist(chatId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing chat id : " + chatId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(chatService.getChatInfo(chatId));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<Map<String, Object>> getChatMessages(@RequestHeader Map<String, String> httpHeader,
                                                               @PathVariable String chatId) {

        /* check existence */
        if (!dataCheckService.isChatIdExist(chatId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing chat id : " + chatId);
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

        return responseBodyFormatService.formatResponseEntity(chatService.getChatMessages(chatId, userId));
    }

    @PutMapping("/{chatId}/in")
    public ResponseEntity<Map<String, Object>> getInChat(@PathVariable String chatId) {
        /* check existence */
        if (!dataCheckService.isChatIdExist(chatId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing chat id : " + chatId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(chatService.getInToChatRoom(chatId));
    }

    @PutMapping("/{chatId}/out")
    public ResponseEntity<Map<String, Object>> getOutChat(@PathVariable String chatId) {
        /* check existence */
        if (!dataCheckService.isChatIdExist(chatId)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", "Not existing chat id : " + chatId);
            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
            return responseBodyFormatService.formatResponseEntity(resultMap);
        }

        return responseBodyFormatService.formatResponseEntity(chatService.getOutFromChatRoom(chatId));
    }

//    @PostMapping("{chatId}/message")
//    public ResponseEntity<Map<String, Object>> addMessage(@RequestHeader Map<String, String> httpHeader,
//                                                          @PathVariable String chatId) {
//        /* check existence */
//        if (!dataCheckService.isChatIdExist(chatId)) {
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("reason", "Not existing chat id : " + chatId);
//            resultMap.put("httpStatus", HttpStatus.NOT_FOUND);
//            return responseBodyFormatService.formatResponseEntity(resultMap);
//        }
//
//        /* header */
//        String userId = httpHeader.get("cert_user_id");
//        if (userId == null) {
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("reason", "user id must be given.");
//            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
//            return responseBodyFormatService.formatResponseEntity(resultMap);
//        }
//        if (!dataCheckService.isUserIdExist(userId)) {
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("reason", "Not existing user id : " + userId);
//            resultMap.put("httpStatus", HttpStatus.BAD_REQUEST);
//            return responseBodyFormatService.formatResponseEntity(resultMap);
//        }
//
//        /* request body validation */
//
//    }

}

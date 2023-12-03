package com.cs473.cs473server.global.service;

import com.cs473.cs473server.global.data.entity.ChatMessage;
import com.cs473.cs473server.global.data.entity.Tip;
import com.cs473.cs473server.global.data.entity.User;
import com.cs473.cs473server.global.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContributionLevelCoreService {

    @Value("${SURVEY_CONTRIBUTION_POINT}")
    private Integer SURVEY_CONTRIBUTION_POINT;

    @Value("${TIP_LIKE_POINT}")
    private Integer TIP_LIKE_POINT;

    @Value("${MESSAGE_LIKE_POINT}")
    private Integer MESSAGE_LIKE_POINT;

    /* repository */
    private final UserRepository userRepository;
    private final UserTipLikeRepository userTipLikeRepository;
    private final UserMessageLikeRepository userMessageLikeRepository;
    private final CongestionFeedbackRepository congestionFeedbackRepository;
    private final TipRepository tipRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ContributionLevelCoreService(UserRepository userRepository,
                                        UserTipLikeRepository userTipLikeRepository,
                                        UserMessageLikeRepository userMessageLikeRepository,
                                        CongestionFeedbackRepository congestionFeedbackRepository,
                                        TipRepository tipRepository,
                                        ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.userTipLikeRepository = userTipLikeRepository;
        this.userMessageLikeRepository = userMessageLikeRepository;
        this.congestionFeedbackRepository = congestionFeedbackRepository;
        this.tipRepository = tipRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public void updateContribution(String userId) {

        /* get target user */
        User targetUser = userRepository.findById(userId).get();

        /* calculate feedback point */
        Integer numberOfFeedbacks = congestionFeedbackRepository.findByCongestionFeedbackUserId(userId).size();
        Integer feedbackPoint = numberOfFeedbacks * SURVEY_CONTRIBUTION_POINT;

        /* calculate tip like point */
        List<Tip> tipListOfUser = tipRepository.findByTipUserId(userId);
        Integer likeCount = 0;
        for (Tip tip : tipListOfUser) {
            Integer tipLikes = userTipLikeRepository.findByUserTipLikeTipId(tip.getTipId()).size();
            likeCount = likeCount + tipLikes;
        }
        Integer tipLikePoint = likeCount * TIP_LIKE_POINT;

        /* calculate message like point */
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatMessageUserId(userId);
        Integer messageLikeCount = 0;
        for (ChatMessage chatMessage : chatMessageList) {
            Integer messageLikes = userMessageLikeRepository
                    .findByUserMessageLikeChatMessageId(chatMessage.getMessageId())
                    .size();
            messageLikeCount = messageLikeCount + messageLikes;
        }
        Integer messageLikePoint = messageLikeCount * MESSAGE_LIKE_POINT;

        Integer totalPoint = feedbackPoint + tipLikePoint + messageLikePoint;
        targetUser.setContributionLevel(totalPoint);
        userRepository.save(targetUser);
    }

}

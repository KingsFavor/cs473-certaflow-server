package com.cs473.cs473server.global.scheduler;

import com.cs473.cs473server.global.data.entity.*;
import com.cs473.cs473server.global.data.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Component
public class DataCleanScheduler {

    private final CongestionOfficialRepository congestionOfficialRepository;
    private final CongestionFeedbackRepository congestionFeedbackRepository;
    private final OnPlanRepository onPlanRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserMessageLikeRepository userMessageLikeRepository;

    @Autowired
    public DataCleanScheduler(CongestionFeedbackRepository congestionFeedbackRepository,
                              CongestionOfficialRepository congestionOfficialRepository,
                              OnPlanRepository onPlanRepository,
                              ChatMessageRepository chatMessageRepository,
                              UserMessageLikeRepository userMessageLikeRepository) {
        this.congestionOfficialRepository = congestionOfficialRepository;
        this.congestionFeedbackRepository = congestionFeedbackRepository;
        this.onPlanRepository = onPlanRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userMessageLikeRepository = userMessageLikeRepository;
    }


    /* scheduling period : 5 seconds */
    @Scheduled(fixedDelay = 5000)
    public void cleanUpMain() {
        cleanUpCongestionFeedbacks();
        cleanUpCongestionOfficials();
        cleanUpOnPlans();
        cleanUpChatMessages();
    }

    private void cleanUpCongestionFeedbacks() {
        /* get all list of congestion feedbacks */
        List<CongestionFeedback> congestionFeedbackList = congestionFeedbackRepository.findAll();

        Integer count = 0;

        /* if expired, delete it. */
        for (CongestionFeedback congestionFeedback : congestionFeedbackList) {
            if (isAfter(LocalDateTime.now(), congestionFeedback.getExpiredAt())) {
                /* expired */
                congestionFeedbackRepository.delete(congestionFeedback);
                count = count + 1;
            }
        }

        log.info(count + " Congestion Feedbacks are deleted.");
    }

    private void cleanUpCongestionOfficials() {
        /* get all list of congestion officials */
        List<CongestionOfficial> congestionOfficialList = congestionOfficialRepository.findAll();

        Integer count = 0;

        /* if expired, delete it. */
        for (CongestionOfficial congestionOfficial : congestionOfficialList) {
            if (isAfter(LocalDateTime.now(), congestionOfficial.getExpiredAt())) {
                /* expired */
                congestionOfficialRepository.delete(congestionOfficial);
                count = count + 1;
            }
        }

        log.info(count + " Congestion Officials are deleted.");
    }

    private void cleanUpOnPlans() {
        /* get all list of onPlans */
        List<OnPlan> onPlanList = onPlanRepository.findAll();

        Integer count = 0;

        /* if expired, delete it. */
        for (OnPlan onPlan : onPlanList) {
            if (isAfter(LocalDateTime.now(), onPlan.getExpiredAt())) {
                /* expired */
                onPlanRepository.delete(onPlan);
                count = count + 1;
            }
        }

        log.info(count + " On-Plans are deleted.");
    }

    private void cleanUpChatMessages() {
        /* get all list of chat messages */
        List<ChatMessage> chatMessageList = chatMessageRepository.findAll();

        Integer count = 0;

        /* if expired, delete it. */
        for (ChatMessage chatMessage : chatMessageList) {
            if (isAfter(LocalDateTime.now(), chatMessage.getExpiredAt())) {
                /* expired */
                /* get all chat message likes */
                List<UserMessageLike> userMessageLikeList = userMessageLikeRepository.findAll();
                /* delete message likes */
                for (UserMessageLike userMessageLike : userMessageLikeList) {
                    userMessageLikeRepository.delete(userMessageLike);
                }
                /* delete message */
                chatMessageRepository.delete(chatMessage);
                count = count + 1;
            }
        }

        log.info(count + " Chat-Messages are deleted.");
    }

    private static boolean isAfter(LocalDateTime currentDateTime, LocalDateTime expiredDateTime) {
        return currentDateTime.isAfter(expiredDateTime);
    }

}

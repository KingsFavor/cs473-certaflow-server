package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String userId;
    private String userName;
    private String userPassword;
    private boolean isActive;
    private LocalDateTime generatedAt;
    private Integer contributionLevel;
    private Integer feedbackNumber;
    private boolean isOfficialUser;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userName(userName)
                .userPassword(userPassword)
                .isActive(isActive)
                .generatedAt(generatedAt)
                .contributionLevel(contributionLevel)
                .feedbackNumber(feedbackNumber)
                .isOfficialUser(isOfficialUser)
                .build();
    }
}

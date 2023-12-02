package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "users")
public class User {

    @Id
    @Getter
    @Column(name = "user_id")
    String userId;

    @Getter
    @Column(name = "user_name")
    String userName;

    @Getter
    @Column(name = "user_password")
    String userPassword;

    @Getter
    @Column(name = "is_active")
    boolean isActive;

    @Getter
    @Column(name = "generated_at")
    LocalDateTime generatedAt;

    @Getter
    @Column(name = "contribution_level")
    Integer contributionLevel;

    @Getter
    @Column(name = "feedback_number")
    Integer feedbackNumber;

    @Getter
    @Column(name = "is_official_user")
    boolean isOfficialUser;

    public UserDto toDto() {
        return UserDto.builder()
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

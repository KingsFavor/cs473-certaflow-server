package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.Location;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LocationDto {


    String locationId;
    Double locationLatitude;
    Double locationLongitude;
    String locationName;
    String locationDescription;
    Integer locationCongestionLevel;
    LocalDateTime lastUpdatedAt;
    Integer currentViewsCount;
    Integer nearUserCount;
    boolean isOfficialUserExist;
    String locationUserId;
    String locationLocationCategoryId;
    String rating;
    String openHour;
    String phoneNumber;
    String homepage;
    String locationAddress;

    public Location toEntity() {
        return Location.builder()
                .locationId(locationId)
                .locationLatitude(locationLatitude)
                .locationLongitude(locationLongitude)
                .locationName(locationName)
                .locationDescription(locationDescription)
                .locationCongestionLevel(locationCongestionLevel)
                .lastUpdatedAt(lastUpdatedAt)
                .currentViewsCount(currentViewsCount)
                .nearUserCount(nearUserCount)
                .isOfficialUserExist(isOfficialUserExist)
                .locationUserId(locationUserId)
                .locationLocationCategoryId(locationLocationCategoryId)
                .rating(rating)
                .openHour(openHour)
                .phoneNumber(phoneNumber)
                .homepage(homepage)
                .locationAddress(locationAddress)
                .build();
    }

}

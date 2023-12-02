package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.LocationDto;
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
@Table(name = "locations")
public class Location {

    @Id
    @Getter
    @Column(name = "location_id")
    String locationId;

    @Getter
    @Column(name = "location_latitude")
    Double locationLatitude;

    @Getter
    @Column(name = "location_longitude")
    Double locationLongitude;

    @Getter
    @Column(name = "location_name")
    String locationName;

    @Getter
    @Column(name = "location_description", columnDefinition = "TEXT")
    String locationDescription;

    @Getter
    @Column(name = "location_congestion_level")
    Integer locationCongestionLevel;

    @Getter
    @Column(name = "last_updated_at")
    LocalDateTime lastUpdatedAt;

    @Getter
    @Column(name = "current_views_count")
    Integer currentViewsCount;

    @Getter
    @Column(name = "near_user_count")
    Integer nearUserCount;

    @Getter
    @Column(name = "is_official_user_exist")
    boolean isOfficialUserExist;

    @Getter
    @Column(name = "fk__locations__users")
    String locationUserId;

    @Getter
    @Column(name = "fk__locations__location_categories")
    String locationLocationCategoryId;

    @Getter
    @Column(name = "rating")
    String rating;

    @Getter
    @Column(name = "open_hour")
    String openHour;

    @Getter
    @Column(name = "phone_number")
    String phoneNumber;

    @Getter
    @Column(name = "homepage")
    String homepage;

    @Getter
    @Column(name = "location_address")
    String locationAddress;

    public LocationDto toDto() {
        return LocationDto.builder()
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

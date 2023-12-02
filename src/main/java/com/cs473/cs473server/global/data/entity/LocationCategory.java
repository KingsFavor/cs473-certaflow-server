package com.cs473.cs473server.global.data.entity;

import com.cs473.cs473server.global.data.dto.LocationCategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "location_categories")
public class LocationCategory {

    @Id
    @Getter
    @Column(name = "location_category_id")
    String locationCategoryId;

    @Getter
    @Column(name = "location_category_name")
    String locationCategoryName;

    public LocationCategoryDto toDto() {
        return LocationCategoryDto.builder()
                .locationCategoryId(locationCategoryId)
                .locationCategoryName(locationCategoryName)
                .build();
    }

}

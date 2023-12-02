package com.cs473.cs473server.global.data.dto;

import com.cs473.cs473server.global.data.entity.LocationCategory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LocationCategoryDto {

    String locationCategoryId;
    String locationCategoryName;

    public LocationCategory toEntity() {
        return LocationCategory.builder()
                .locationCategoryId(locationCategoryId)
                .locationCategoryName(locationCategoryName)
                .build();
    }

}

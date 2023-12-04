package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.ReviewEntity;
import com.sportyfind.webapi.entities.VenueEntity;

import java.sql.Time;
import java.util.List;

public class VenueCreateDto {
    public String address;
    public Time openTime;
    public Time closeTime;
    public String phone;
    public String thumbnail;
    List<ReviewDto> reviews;


    public static VenueCreateDto fromEntity(VenueEntity entity) {
        var result = new VenueCreateDto();
        result.address = entity.getAddress();
        result.openTime = entity.getOpeningHour();
        result.closeTime = entity.getClosingHour();
        result.phone = entity.getPhone();
        result.thumbnail = entity.getThumbnail();
        result.reviews = ReviewDto.fromEntities(entity.getReviews());
        return result;
    }
}

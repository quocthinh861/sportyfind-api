package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.ReviewEntity;
import com.sportyfind.webapi.entities.VenueEntity;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

public class VenueCreateDto {
    public Integer venueId;
    public String address;
    public String district;
    public String province;
    public String ward;
    public Time openTime;
    public Time closeTime;
    public String phone;
    public String thumbnail;
    public String additionalInfo;
    public String latitude;
    public String longitude;
    public List<ReviewDto> reviews;


    public static VenueCreateDto fromEntity(VenueEntity entity) {
        var result = new VenueCreateDto();
        result.address = entity.getAddress();
        result.openTime = entity.getOpeningHour();
        result.closeTime = entity.getClosingHour();
        result.phone = entity.getPhone();
        result.thumbnail = entity.getThumbnail();
        result.additionalInfo = entity.getAdditionalInfo();
        result.latitude = entity.getLati();
        result.longitude = entity.getLongti();
        // sort reviews by createdDate;
        entity.getReviews().sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));
        result.reviews = ReviewDto.fromEntities(entity.getReviews());
        result.district = entity.getDistrict();
        result.province = entity.getProvince();
        result.ward = entity.getWard();
        result.venueId = entity.getVenueId();
        return result;
    }
}

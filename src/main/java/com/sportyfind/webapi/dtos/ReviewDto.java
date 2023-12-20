package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.ReviewEntity;
import com.sportyfind.webapi.util.TimeUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDto {
    public String username;
    public String content;
    public Integer rating;
    public Timestamp createdDate;
    public Long userId;
    public Integer venueId;



    public static ReviewDto fromEntity(ReviewEntity entity) {
        var result = new ReviewDto();
        result.username = entity.getCustomer().getUsername();
        result.content = entity.getReview();
        result.rating = entity.getScore();

        // convert Date to Timestamp
        Date date = entity.getCreatedDate();
        result.createdDate = new Timestamp(date.getTime());

        return result;
    }

    public static List<ReviewDto> fromEntities(List<ReviewEntity> entities) {
        return entities.stream().map(ReviewDto::fromEntity).collect(Collectors.toList());
    }
}

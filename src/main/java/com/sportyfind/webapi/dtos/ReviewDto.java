package com.sportyfind.webapi.dtos;

import com.sportyfind.webapi.entities.ReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewDto {
    public String username;
    public String content;
    public Integer rating;

    public static ReviewDto fromEntity(ReviewEntity entity) {
        var result = new ReviewDto();
        result.username = entity.getCustomer().getUsername();
        result.content = entity.getReview();
        result.rating = entity.getScore();
        return result;
    }

    public static List<ReviewDto> fromEntities(List<ReviewEntity> entities) {
        return entities.stream().map(ReviewDto::fromEntity).collect(Collectors.toList());
    }
}

package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.FieldCreateDto;
import com.sportyfind.webapi.dtos.ReviewDto;
import com.sportyfind.webapi.dtos.VenueCreateDto;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.entities.ReviewEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.entities.VenueEntity;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.ReviewRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;

    @Autowired VenueRepository venueRepository;

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;


    public FieldCreateDto getFieldIdByURL(String url) {
        VenueEntity venueEntity = venueRepository.findByUrl(url);
        if(venueEntity == null) {
            return null;
        }
        List<FieldEntity> fieldEntities = venueEntity.getFields();
        if(fieldEntities == null || fieldEntities.size() == 0) {
            return null;
        }

        return getFieldDetailById(fieldEntities.get(0).getFieldId());
    }

    public FieldCreateDto getFieldDetailById(Integer fieldId) {
        FieldEntity field = fieldRepository.findById(fieldId).orElse(null);
        if (field == null) {
            return null;
        }
        VenueEntity venue = field.getVenue();
        if (venue == null) {
            return null;
        }

        FieldCreateDto result = FieldCreateDto.fromEntity(field);
        result.venue = VenueCreateDto.fromEntity(venue);

        return result;
    }

    public ReviewDto submitReview(ReviewDto reviewDto) {
        UserEntity userEntity = userRepository.findById(reviewDto.userId).orElse(null);
        if(userEntity == null) {
            throw new RuntimeException("User not found");
        }
        VenueEntity venueEntity = venueRepository.findById(reviewDto.venueId).orElse(null);
        if(venueEntity == null) {
            throw new RuntimeException("Venue not found");
        }

        // Check if user already reviewed this venue, if yes, update the review
        List<ReviewEntity> reviewEntities = reviewRepository.findByCustomerAndVenue(userEntity, venueEntity);
        if(reviewEntities != null && reviewEntities.size() > 0) {
            ReviewEntity reviewEntity = reviewEntities.get(0);
            reviewEntity.setReview(reviewDto.content);
            reviewEntity.setScore(reviewDto.rating);
            reviewEntity.setCreatedDate(new Date());

            ReviewEntity result = reviewRepository.save(reviewEntity);
            return ReviewDto.fromEntity(result);
        }

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview(reviewDto.content);
        reviewEntity.setScore(reviewDto.rating);
        reviewEntity.setCustomer(userEntity);
        reviewEntity.setVenue(venueEntity);
        reviewEntity.setCreatedDate(new Date());

        ReviewEntity result = reviewRepository.save(reviewEntity);
        return ReviewDto.fromEntity(result);
    }
}

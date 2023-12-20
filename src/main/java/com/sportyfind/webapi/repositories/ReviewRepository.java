package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.ReviewEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.entities.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    public List<ReviewEntity> findByCustomerAndVenue(UserEntity userEntity, VenueEntity venueEntity);
}

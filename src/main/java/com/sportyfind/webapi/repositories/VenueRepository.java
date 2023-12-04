package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<VenueEntity, Integer> {

        public VenueEntity findByUrl(String url);
}

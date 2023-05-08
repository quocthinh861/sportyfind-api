package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.models.FieldBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldBookingRepository extends JpaRepository<FieldBookingEntity, Integer> {
    List<FieldBookingEntity> findByCustomerId(Long customerId);
}
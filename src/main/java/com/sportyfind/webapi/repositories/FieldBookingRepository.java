package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FieldBookingRepository extends JpaRepository<FieldBookingEntity, Integer> {
    List<FieldBookingEntity> findByCustomerId(Long customerId);

    @Query("SELECT fb FROM FieldBookingEntity fb WHERE fb.customer.id = :customerId AND fb.field.fieldId = :fieldId AND fb.bookingDate BETWEEN :beginDate AND :endDate")
    List<FieldBookingEntity> searchFieldBookingWithQuery(@Param("customerId") Long customerId,
                                                         @Param("fieldId") Integer fieldId,
                                                         @Param("beginDate") Date beginDate,
                                                         @Param("endDate") Date endDate);
}
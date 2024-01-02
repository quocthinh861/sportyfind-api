package com.sportyfind.webapi.repositories;

import com.sportyfind.webapi.entities.FieldBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface FieldBookingRepository extends JpaRepository<FieldBookingEntity, Integer> {
    List<FieldBookingEntity> findByCustomerId(Long customerId);

    @Query("SELECT fb FROM FieldBookingEntity fb WHERE fb.customer.id = :customerId AND fb.bookingDate BETWEEN :beginDate AND :endDate AND (:status IS NULL OR fb.bookingStatus = :status)")
    List<FieldBookingEntity> searchFieldBookingWithQuery(@Param("customerId") Long customerId,
                                                         @Param("beginDate") Date beginDate,
                                                         @Param("endDate") Date endDate,
                                                         @Param("status") String status);


    // query to check if the field is available at the time
    @Query("SELECT fb FROM FieldBookingEntity fb WHERE fb.field.fieldId = :fieldId AND fb.bookingDate = :bookingDate AND (fb.startTime BETWEEN :startTime AND :endTime OR fb.endTime BETWEEN :startTime AND :endTime)")
    List<FieldBookingEntity> findByFieldIdAndBookingDateAndStartTimeOrEndTime(@Param("fieldId") Integer fieldId,
                                                                               @Param("bookingDate") Date bookingDate,
                                                                               @Param("startTime") Time startTime,
                                                                               @Param("endTime") Time endTime);

}
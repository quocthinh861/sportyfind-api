package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.models.SearchBookingQuery;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private FieldBookingRepository fieldBookingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private UserRepository userRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    public FieldBookingDto createBooking(FieldBookingDto bookingDTO) throws Exception {
        var result = new FieldBookingDto();

        FieldEntity field = fieldRepository.findById(bookingDTO.fieldId)
                .orElseThrow(() -> new Exception("Field not found"));
        UserEntity customer = userRepository.findById(bookingDTO.customerId)
                .orElseThrow(() -> new Exception("Customer not found"));

        FieldBookingEntity booking = new FieldBookingEntity();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date bookingDate = dateFormat.parse(bookingDTO.bookingDate);
        booking.setCustomer(customer);
        booking.setField(field);
        booking.setBookingDate(bookingDate);
        booking.setBookingStatus(bookingDTO.bookingStatus);
        // Parse the input strings to LocalTime objects
        LocalTime startTime = LocalTime.parse(bookingDTO.startTime, formatter);
        LocalTime endTime = LocalTime.parse(bookingDTO.endTime, formatter);
        // Convert LocalTime objects to Time objects
        Time startTimeSql = Time.valueOf(startTime);
        Time endTimeSql = Time.valueOf(endTime);
        booking.setStartTime(startTimeSql);
        booking.setEndTime(endTimeSql);
        fieldBookingRepository.save(booking);

        result.loadFromEntity(booking);
        return result;
    }

    public List<FieldBookingDto> getBookingByCustomerId(Long customerId) {
        var data = fieldBookingRepository.findByCustomerId(customerId);
        return FieldBookingDto.fromEntities(data);
    }

    public List<FieldBookingDto> searchBooking(SearchBookingQuery query) throws Exception {
        Date beginDate = TimeUtil.formatStringToDate(query.beginDate);
        Date endDate = TimeUtil.formatStringToDate(query.endDate);

        var data = fieldBookingRepository.searchFieldBookingWithQuery(query.customerId, query.fieldId, beginDate, endDate);
        return FieldBookingDto.fromEntities(data);
    }
}

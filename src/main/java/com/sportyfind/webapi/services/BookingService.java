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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private FieldBookingRepository fieldBookingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private UserRepository userRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    public boolean checkAvailableTime(FieldBookingDto fieldBookingDto) throws Exception {
        LocalTime startTime = LocalTime.parse(fieldBookingDto.startTime, formatter);
        LocalTime endTime = LocalTime.parse(fieldBookingDto.endTime, formatter);
        Time startTimeSql = Time.valueOf(startTime);
        Time endTimeSql = Time.valueOf(endTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date bookingDate = dateFormat.parse(fieldBookingDto.bookingDate);

        List<FieldBookingEntity> bookings = fieldBookingRepository.findByFieldIdAndBookingDateAndStartTimeOrEndTime(
                fieldBookingDto.fieldId, bookingDate, startTimeSql, endTimeSql);

        return bookings.size() == 0;
    }

    public FieldBookingDto createBooking(FieldBookingDto bookingDTO) throws Exception {
        var result = new FieldBookingDto();

        FieldEntity field = fieldRepository.findById(bookingDTO.fieldId)
                .orElseThrow(() -> new Exception("Field not found"));
        UserEntity customer = userRepository.findById(bookingDTO.customerId)
                .orElseThrow(() -> new Exception("Customer not found"));

        FieldBookingEntity booking = new FieldBookingEntity();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date bookingDate = dateFormat.parse(bookingDTO.bookingDate);

        if (checkAvailableTime(bookingDTO) == false) {
            throw new Exception("Time is not available");
        }


        booking.setCustomer(customer);
        booking.setField(field);
        booking.setBookingDate(bookingDate);
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
        // Sort the data by booking date using Stream API
        List<FieldBookingEntity> sortedData = data.stream()
                .sorted((o1, o2) -> o2.getBookingDate().compareTo(o1.getBookingDate()))
                .collect(Collectors.toList());
        return FieldBookingDto.fromEntities(sortedData);
    }

    public List<FieldBookingDto> searchBooking(SearchBookingQuery query) throws Exception {
        Date beginDate = TimeUtil.formatStringToDate(query.beginDate, "MM/dd/yyyy");
        Date endDate = TimeUtil.formatStringToDate(query.endDate, "MM/dd/yyyy");


        var data = fieldBookingRepository.searchFieldBookingWithQuery(query.customerId, beginDate, endDate, query.status);
        return FieldBookingDto.fromEntities(data);
    }

    public FieldBookingDto deleteBooking(FieldBookingDto bookingDTO) {
        var result = new FieldBookingDto();

        FieldBookingEntity booking = fieldBookingRepository.findById(bookingDTO.bookingId).orElse(null);
        if (booking != null) {
            fieldBookingRepository.delete(booking);
            result.loadFromEntity(booking);
        }
        return result;
    }
}

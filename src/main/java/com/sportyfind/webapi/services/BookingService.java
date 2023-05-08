package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.models.FieldBookingEntity;
import com.sportyfind.webapi.models.FieldEntity;
import com.sportyfind.webapi.models.UserEntity;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        booking.setStartTime(Time.valueOf(bookingDTO.startTime));
        booking.setEndTime(Time.valueOf(bookingDTO.endTime));
        fieldBookingRepository.save(booking);

        result.loadFromEntity(booking);
        return result;
    }

    public List<FieldBookingDto> getBookingByCustomerId(Long customerId) {
        var data = fieldBookingRepository.findByCustomerId(customerId);
        List<FieldBookingDto> result = new ArrayList<>();

        for (FieldBookingEntity booking : data) {
            FieldBookingDto bookingDto = new FieldBookingDto();
            bookingDto.loadFromEntity(booking);
            bookingDto.FieldName = booking.getField().getFieldName();
            result.add(bookingDto);
        }
        return result;
    }
}

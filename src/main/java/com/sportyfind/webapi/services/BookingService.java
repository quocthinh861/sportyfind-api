package com.sportyfind.webapi.services;

import com.sportyfind.webapi.Utils.CustomTimeUtil;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.entities.FieldBookingEntity;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.models.SearchBookingQuery;
import com.sportyfind.webapi.repositories.FieldBookingRepository;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
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
        return FieldBookingDto.fromEntities(data);
    }

    public List<FieldBookingDto> searchBooking(SearchBookingQuery query) throws Exception {
        Date beginDate = CustomTimeUtil.formatStringToDate(query.beginDate);
        Date endDate = CustomTimeUtil.formatStringToDate(query.endDate);

        var data = fieldBookingRepository.searchFieldBookingWithQuery(query.customerId, query.fieldId, beginDate, endDate);
        return FieldBookingDto.fromEntities(data);
    }
}

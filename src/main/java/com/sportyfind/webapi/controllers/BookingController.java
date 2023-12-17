package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.models.SearchBookingQuery;
import com.sportyfind.webapi.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@PreAuthorize("hasRole('USER')")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Object> createBooking(@RequestBody FieldBookingDto bookingDTO) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = bookingService.createBooking(bookingDTO);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/checkAvailability")
    public ResponseEntity<Object> checkAvailable(@RequestBody FieldBookingDto bookingDTO) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = bookingService.checkAvailableTime(bookingDTO);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }


    @GetMapping("/getBookingListByCustomerId")
    public ResponseEntity<Object> getBookingByCustomerId(@RequestParam Long customerId) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = bookingService.getBookingByCustomerId(customerId);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/searchBooking")
    public ResponseEntity<Object> searchBooking(@RequestBody SearchBookingQuery query) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = bookingService.searchBooking(query);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }
}

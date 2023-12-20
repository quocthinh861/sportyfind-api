package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.ReviewDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.entities.FieldEntity;
import com.sportyfind.webapi.repositories.FieldRepository;
import com.sportyfind.webapi.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldService fieldService;

    @PostMapping("/create")
    public ResponseEntity<Object> createField(@RequestBody FieldEntity fieldEntity) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            fieldRepository.save(fieldEntity);
            response.result = fieldEntity;
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getFieldDetailByURL")
    public ResponseEntity<Object> getFieldDetailByURL(@RequestParam String url) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            var field = fieldService.getFieldIdByURL(url);
            response.result = field;
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/getFieldDetail")
    public ResponseEntity<Object> getFieldDetail(@RequestParam int fieldId) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            var field = fieldService.getFieldDetailById(fieldId);
            response.result = field;
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/submitReview")
    public ResponseEntity<Object> submitReview(@RequestBody ReviewDto reviewDto) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            var field = fieldService.submitReview(reviewDto);
            response.result = field;
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }
}

package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.models.FieldEntity;
import com.sportyfind.webapi.repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;

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
}
